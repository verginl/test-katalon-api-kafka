import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

import groovy.json.JsonSlurper

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer

import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When

import java.time.Duration
import java.util.Collections
import java.util.Properties

public class KafkaConsumerSteps {

	KafkaConsumer<String, String> consumer
	String consumedMessage
	def jsonMessage

	static final String BOOTSTRAP_SERVERS = 'localhost:9092'
	static final String TOPIC = 'user-events'

	@Given("the Kafka service is available")
	def verifyKafkaServiceIsAvailable() {

		Properties properties = createConsumerProperties()

		consumer = new KafkaConsumer<String, String>(properties)

		consumer.subscribe(Collections.singletonList(TOPIC))

		println "Kafka service is available"
		println "Bootstrap Server: ${BOOTSTRAP_SERVERS}"
		println "Topic: ${TOPIC}"
	}

	@When("I consume a message from the user events topic")
	def consumeMessageFromUserEventsTopic() {

		long timeout = System.currentTimeMillis() + 10000

		while (System.currentTimeMillis() < timeout) {

			ConsumerRecords<String, String> records =
					consumer.poll(Duration.ofMillis(1000))

			if (!records.isEmpty()) {

				records.each { record ->

					consumedMessage = record.value()

					println "Kafka message received:"
					println consumedMessage

					return
				}
			}
		}

		assert consumedMessage != null :
		"No Kafka message was received within 10 seconds"

		jsonMessage = new JsonSlurper().parseText(consumedMessage)

		println "Consumed message parsed successfully"
	}

	@Then("the Kafka message should contain user information")
	def verifyKafkaMessageContainsUserInformation() {

		assert jsonMessage != null

		assert jsonMessage.event != null
		assert jsonMessage.userId != null
		assert jsonMessage.name != null
		assert jsonMessage.email != null

		assert jsonMessage.event == 'USER_CREATED'
		assert jsonMessage.name == 'Vergi'
		assert jsonMessage.email == 'vergi@example.com'

		println "Kafka message validation passed"
		println "Event : ${jsonMessage.event}"
		println "User ID : ${jsonMessage.userId}"
		println "Name : ${jsonMessage.name}"
		println "Email : ${jsonMessage.email}"

		consumer.close()
	}

	private Properties createConsumerProperties() {

		Properties properties = new Properties()

		properties.put(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				BOOTSTRAP_SERVERS
				)

		properties.put(
				ConsumerConfig.GROUP_ID_CONFIG,
				"katalon-user-events-consumer"
				)

		properties.put(
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName()
				)

		properties.put(
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName()
				)

		properties.put(
				ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
				"earliest"
				)

		properties.put(
				ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
				"true"
				)

		return properties
	}
}
