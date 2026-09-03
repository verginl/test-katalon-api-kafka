Feature: Kafka User Events

  @TC_KafkaConsumer
  Scenario: Consume user created event from Kafka
    Given the Kafka service is available
    When I consume a message from the user events topic
    Then the Kafka message should contain user information