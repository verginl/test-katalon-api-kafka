# API & Kafka Automation

Project Katalon Studio untuk mendemonstrasikan pengujian RESTful API dan pengujian Kafka Consumer menggunakan pendekatan BDD (Cucumber).

Project ini mendemonstrasikan:

Pengujian REST API Producer menggunakan Katalon Studio
Pengujian REST API Consumer menggunakan Katalon Studio
Pengujian Kafka Consumer menggunakan Katalon Studio
Kafka Producer menggunakan Node.js
Eksekusi pengujian berbasis BDD menggunakan Cucumber
Eksekusi end-to-end melalui Katalon Test Suite
REST API lokal menggunakan Node.js dan Express
Kafka yang dijalankan menggunakan Docker

# Requirements

Before running the project, make sure the following are installed:

Katalon Studio 8.6.9
Java 8
Node.js
npm
Docker Desktop
Git

### Recommended Environment

The project was developed and tested using:

Katalon Studio : 8.6.9
Java           : 1.8.0_362
Node.js        : 22.x
Kafka          : 3.9.1
KafkaJS        : 2.x
Docker         : 29.x

# Quick Start

The project provides a *start.sh* script to simplify environment setup.

After cloning the repository, simply run:

git clone <REPOSITORY_URL>
cd test-katalon-api-kafka
./start.sh

The startup script automatically performs the following:

Docker check
     ↓
Start Kafka
     ↓
Wait for Kafka
     ↓
Create/check Kafka topic
     ↓
Start REST API
     ↓
Install Kafka Producer dependencies
     ↓
Send USER_CREATED event
     ↓
Environment READY

No need to manually execute the Kafka Docker command, create the topic, start the REST API, or run npm install.


# Running the Test

After executing:

./start.sh

open the project using Katalon Studio.

Then navigate to:

Test Suites
    ↓
TS_API_KafkaIntegration

Click:

Run

Expected result:

TC_REST_Producer_CreateUser    PASS
TC_REST_Consumer_GetUser       PASS
TC_KafkaConsumer               PASS