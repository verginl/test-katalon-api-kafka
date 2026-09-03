const { Kafka } = require('kafkajs');

const kafka = new Kafka({
    clientId: 'katalon-test-producer',
    brokers: ['localhost:9092']
});

const producer = kafka.producer();

const message = {
    event: 'USER_CREATED',
    userId: 1,
    name: 'Vergi',
    email: 'vergi@example.com'
};

async function run() {

    await producer.connect();

    console.log('Kafka Producer connected');

    await producer.send({
        topic: 'user-events',
        messages: [
            {
                value: JSON.stringify(message)
            }
        ]
    });

    console.log('Message sent successfully');
    console.log(JSON.stringify(message));

    await producer.disconnect();

    console.log('Kafka Producer disconnected');
}

run().catch(async error => {

    console.error('Kafka Producer error:', error);

    try {
        await producer.disconnect();
    } catch (disconnectError) {
        // Ignore disconnect error
    }

    process.exit(1);
});