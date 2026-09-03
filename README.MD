API & Kafka Automation

Project Katalon Studio untuk mendemonstrasikan pengujian RESTful API dan pengujian Kafka Consumer menggunakan pendekatan BDD (Cucumber).

<br>
Project Overview

Project ini mendemonstrasikan:

Pengujian REST API Producer menggunakan Katalon Studio
Pengujian REST API Consumer menggunakan Katalon Studio
Pengujian Kafka Consumer menggunakan Katalon Studio
Kafka Producer menggunakan Node.js
Eksekusi pengujian berbasis BDD menggunakan Cucumber
Eksekusi end-to-end melalui Katalon Test Suite
REST API lokal menggunakan Node.js dan Express
Kafka yang dijalankan menggunakan Docker
<br>
Requirements

Before running the project, make sure the following tools are installed:

<br>
Software	Version
Katalon Studio	8.6.9
Java	8
Node.js	22.x
npm	Included with Node.js
Docker Desktop	29.x
Git	Latest
<br>
Recommended Environment

The project was developed and tested using:

Katalon Studio : 8.6.9
Java           : 1.8.0_362
Node.js        : 22.x
Kafka          : 3.9.1
KafkaJS        : 2.x
Docker         : 29.x

<br>
Quick Start

The project provides a start.sh script to simplify the environment setup.

<br>
1. Clone Repository
git clone <REPOSITORY_URL>
cd test-katalon-api-kafka

<br>
2. Start Environment

Run:

./start.sh

<br>

The startup script automatically performs the following steps:

Docker Check
     ↓
Start Kafka
     ↓
Wait for Kafka
     ↓
Create / Check Kafka Topic
     ↓
Start REST API
     ↓
Install Kafka Producer Dependencies
     ↓
Send USER_CREATED Event
     ↓
Environment READY

<br>

Note:
There is no need to manually execute the Kafka Docker command, create the Kafka topic, start the REST API, or run npm install.

<br>
Running the Test

After successfully executing:

./start.sh


open the project using Katalon Studio.

<br>

Navigate to:

Test Suites
    ↓
TS_API_KafkaIntegration

<br>

Then click:

Run

<br>
Expected Result

The following test cases are expected to pass:

TC_REST_Producer_CreateUser    PASS
TC_REST_Consumer_GetUser       PASS
TC_KafkaConsumer                PASS

<br>
Test Flow

The overall integration test flow can be illustrated as follows:

                    ┌─────────────────────┐
                    │      start.sh       │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │    Docker / Kafka   │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │     REST API        │
                    │ Node.js + Express   │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │   Kafka Producer    │
                    │      Node.js        │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │   Kafka Topic       │
                    │   USER_CREATED      │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │  Katalon Studio     │
                    │   Kafka Consumer    │
                    └─────────────────────┘

<br>
BDD Testing

Testing menggunakan pendekatan BDD (Behavior Driven Development) dengan Cucumber.

Struktur pengujian mencakup:

Feature
   ↓
Scenario
   ↓
Step Definition
   ↓
Katalon Test Case
   ↓
API / Kafka

<br>

BDD digunakan untuk membuat skenario pengujian lebih mudah dibaca dan dipahami, baik oleh developer maupun QA.

<br>
Test Coverage
Test Case	Description	Expected
TC_REST_Producer_CreateUser	Create user melalui REST API	PASS
TC_REST_Consumer_GetUser	Retrieve user melalui REST API	PASS
TC_KafkaConsumer	Consume USER_CREATED event dari Kafka	PASS
<br>
Architecture

Project terdiri dari beberapa komponen utama:

┌─────────────────────────────────────────────┐
│              Katalon Studio                │
│                                             │
│  REST API Test       Kafka Consumer Test    │
│       │                       │             │
└───────┼───────────────────────┼─────────────┘
        │                       │
        ▼                       ▼
┌───────────────┐       ┌─────────────────┐
│ Node.js REST  │       │      Kafka      │
│ API + Express │       │    Docker      │
└───────────────┘       └────────┬────────┘
                                 │
                                 ▲
                         ┌───────┴────────┐
                         │ Kafka Producer │
                         │    Node.js     │
                         └────────────────┘

<br>
Environment Startup
