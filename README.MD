# API & Kafka Automation

Project Katalon Studio untuk mendemonstrasikan pengujian RESTful API dan pengujian Kafka Consumer menggunakan pendekatan BDD (Cucumber).

<br>

Project ini mendemonstrasikan:

1. Pengujian REST API Producer menggunakan Katalon Studio
2. Pengujian REST API Consumer menggunakan Katalon Studio
3. Pengujian Kafka Consumer menggunakan Katalon Studio
4. Kafka Producer menggunakan Node.js
5. Eksekusi pengujian berbasis BDD menggunakan Cucumber
6. Eksekusi end-to-end melalui Katalon Test Suite
7. REST API lokal menggunakan Node.js dan Express
8. Kafka yang dijalankan menggunakan Docker

<br>

# Requirements

Before running the project, make sure the following are installed:

- Katalon Studio 8.6.9
- Java 8
- Node.js
- npm
- Docker Desktop
- Git

<br>

## Recommended Environment

The project was developed and tested using:

```text
Katalon Studio : 8.6.9
Java           : 1.8.0_362
Node.js        : 22.x
Kafka          : 3.9.1
KafkaJS        : 2.x
Docker         : 29.x
```


# Quick Start

The project provides a *start.sh* script to simplify environment setup.

<br>

After cloning the repository, simply run:

```bash
git clone <REPOSITORY_URL>
cd test-katalon-api-kafka
./start.sh
```

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

<br>
No need to manually execute the Kafka Docker command, create the topic, start the REST API, or run npm install.

<br>
Running the Test
After executing:

```bash
./start.sh
```

<br>
open the project using Katalon Studio.

<br>
Then navigate to:

Test Suites
    ↓
TS_API_KafkaIntegration

<br>
Click:

Run

<br>
Expected result:

TC_REST_Producer_CreateUser    PASS
TC_REST_Consumer_GetUser       PASS
TC_KafkaConsumer               PASS
