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

# Requirements

Before running the project, make sure the following are installed:

- Katalon Studio 8.6.9
- Java 8
- Node.js
- npm
- Docker Desktop
- Git



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

After cloning the repository, simply run:

```bash
git clone https://github.com/verginl/test-katalon-api-kafka.git 
cd test-katalon-api-kafka
./start.sh
```

The startup script automatically performs the following:

```text
1. Docker check
2. Start Kafka
3. Wait for Kafka
4. Create/check Kafka topic
5. Start REST API
6. Install Kafka Producer dependencies
7. Send USER_CREATED event
8. Environment READY
```
No need to manually execute the Kafka Docker command, create the topic, start the REST API, or run npm install.

# Running the Test

After executing:

```bash
./start.sh
```

Open the project using Katalon Studio.

Then navigate to:

```text
1. Test Suites
2. TS_API_KafkaIntegration
```


*Click:*
<br>
```text
Run
```

*Expected result:*

```text
TC_REST_Producer_CreateUser    PASS
TC_REST_Consumer_GetUser       PASS
TC_KafkaConsumer               PASS
```
