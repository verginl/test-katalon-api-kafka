#!/bin/bash

set -e

echo "=========================================="
echo " Katalon API & Kafka Test Environment"
echo "=========================================="

# ------------------------------------------
# Configuration
# ------------------------------------------

KAFKA_CONTAINER="kafka"
KAFKA_IMAGE="apache/kafka:3.9.1"
KAFKA_PORT="9092"
KAFKA_TOPIC="user-events"
API_PORT="8080"

# ------------------------------------------
# Check Docker
# ------------------------------------------

echo ""
echo "[1/7] Checking Docker..."

if ! command -v docker >/dev/null 2>&1; then
    echo "ERROR: Docker is not installed."
    exit 1
fi

if ! docker info >/dev/null 2>&1; then
    echo "ERROR: Docker is not running."
    echo "Please start Docker Desktop and run this script again."
    exit 1
fi

echo "Docker is ready."

# ------------------------------------------
# Start Kafka
# ------------------------------------------

echo ""
echo "[2/7] Starting Kafka..."

if docker ps --format '{{.Names}}' | grep -qx "$KAFKA_CONTAINER"; then

    echo "Kafka container is already running."

elif docker ps -a --format '{{.Names}}' | grep -qx "$KAFKA_CONTAINER"; then

    echo "Kafka container exists. Starting it..."

    docker start "$KAFKA_CONTAINER" >/dev/null

else

    echo "Kafka container does not exist. Creating it..."

    docker run -d \
        --name "$KAFKA_CONTAINER" \
        -p "$KAFKA_PORT:9092" \
        "$KAFKA_IMAGE" >/dev/null

fi

# ------------------------------------------
# Wait for Kafka
# ------------------------------------------

echo ""
echo "[3/7] Waiting for Kafka..."

MAX_RETRIES=30
RETRY=0

until docker exec "$KAFKA_CONTAINER" \
    /opt/kafka/bin/kafka-broker-api-versions.sh \
    --bootstrap-server localhost:9092 >/dev/null 2>&1
do

    RETRY=$((RETRY + 1))

    if [ "$RETRY" -ge "$MAX_RETRIES" ]; then
        echo "ERROR: Kafka did not become ready."
        exit 1
    fi

    sleep 2
done

echo "Kafka is ready."

# ------------------------------------------
# Create Kafka Topic
# ------------------------------------------

echo ""
echo "[4/7] Checking Kafka topic..."

if docker exec "$KAFKA_CONTAINER" \
    /opt/kafka/bin/kafka-topics.sh \
    --list \
    --bootstrap-server localhost:9092 |
    grep -qx "$KAFKA_TOPIC"
then

    echo "Topic '$KAFKA_TOPIC' already exists."

else

    echo "Creating topic '$KAFKA_TOPIC'..."

    docker exec "$KAFKA_CONTAINER" \
        /opt/kafka/bin/kafka-topics.sh \
        --create \
        --topic "$KAFKA_TOPIC" \
        --bootstrap-server localhost:9092 \
        --partitions 1 \
        --replication-factor 1

fi

# ------------------------------------------
# Prepare REST API Dependencies
# ------------------------------------------

echo ""
echo "[5/7] Preparing REST API dependencies..."

if [ ! -d "api-server/node_modules" ]; then

    echo "Installing REST API dependencies..."

    cd api-server
    npm install
    cd ..

else

    echo "REST API dependencies already installed."

fi

# ------------------------------------------
# Start REST API
# ------------------------------------------

echo ""
echo "[6/7] Starting REST API..."

if lsof -iTCP:"$API_PORT" -sTCP:LISTEN -n -P >/dev/null 2>&1; then

    echo "REST API is already running on port $API_PORT."

else

    echo "Starting REST API on port $API_PORT..."

    nohup node api-server/server.js \
        >/tmp/katalon-api-server.log 2>&1 &

    sleep 2

    if curl -s http://localhost:"$API_PORT"/health >/dev/null 2>&1; then

        echo "REST API is ready."

    else

        echo "ERROR: REST API failed to start."
        echo "Check log:"
        echo "/tmp/katalon-api-server.log"

        exit 1

    fi

fi

# ------------------------------------------
# Prepare Kafka Producer
# ------------------------------------------

echo ""
echo "[7/7] Preparing Kafka Producer..."

if [ ! -d "kafka/node_modules" ]; then

    echo "Installing Kafka Producer dependencies..."

    cd kafka
    npm install
    cd ..

else

    echo "Kafka Producer dependencies already installed."

fi

# ------------------------------------------
# Send Kafka Event
# ------------------------------------------

echo ""
echo "Sending Kafka USER_CREATED event..."

cd kafka
npm start
cd ..

# ------------------------------------------
# Done
# ------------------------------------------

echo ""
echo "=========================================="
echo " Environment is READY"
echo "=========================================="
echo ""
echo "REST API : http://localhost:8080"
echo "Kafka    : localhost:9092"
echo "Topic    : user-events"
echo ""
echo "Kafka Producer:"
echo "  USER_CREATED event sent successfully"
echo ""
echo "Next step:"
echo "Open the project in Katalon Studio"
echo ""
echo "Run:"
echo "Test Suites"
echo "  -> TS_API_Kafka"
echo "     -> TS_API_KafkaIntegration"
echo ""
echo "=========================================="