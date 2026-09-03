import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint

import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException

import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When

import com.kms.katalon.core.configuration.RunConfiguration
import groovy.json.JsonSlurper

class RestAPISteps {
	// Shared variables
	ResponseObject response

	def jsonResponse

	@Given("the REST API service is available")
	def verifyRestApiServiceIsAvailable() {

		ResponseObject healthResponse = WS.sendRequest(
				findTestObject('Object Repository/REQ_Health_Check')
				)

		WS.verifyResponseStatusCode(
				healthResponse,
				200
				)

		println "REST API service is available"
		println "Health Check Response: ${healthResponse.getResponseText()}"
	}


	// WHEN - REST PRODUCER\
	@When("I send a POST request to create a new user")
	def sendPostRequestToCreateNewUser() {

		response = WS.sendRequest(
				findTestObject('Object Repository/REQ_Create_User')
				)

		jsonResponse = new JsonSlurper().parseText(
				response.getResponseText()
				)

		println "POST /api/users executed"
		println "Response: ${response.getResponseText()}"
	}


	// THEN - REST PRODUCER
	@Then("the response status code should be 201")
	def verifyCreateUserResponseStatusCode() {

		WS.verifyResponseStatusCode(
				response,
				201
				)

		println "Response status code: 201"
	}


	@Then("the response should contain the created user information")
	def verifyCreatedUserInformation() {

		assert jsonResponse.id != null
		assert jsonResponse.name != null
		assert jsonResponse.email != null

		WS.verifyEqual(
				jsonResponse.name,
				'Vergi'
				)

		WS.verifyEqual(
				jsonResponse.email,
				'vergi@example.com'
				)

		GlobalVariable.createdUserId =
				jsonResponse.id.toString()

		println "Created User ID: ${GlobalVariable.createdUserId}"
		println "Created User Name: ${jsonResponse.name}"
		println "Created User Email: ${jsonResponse.email}"
	}


	// WHEN - REST CONSUMER
	@When("I request the created user")
	def requestCreatedUser() {

		assert GlobalVariable.createdUserId != null
		assert GlobalVariable.createdUserId.toString().trim() != ''

		response = WS.sendRequest(
				findTestObject(
				'Object Repository/REQ_Get_User',
				[
					('userId'): GlobalVariable.createdUserId
				]
				)
				)

		jsonResponse = new JsonSlurper().parseText(
				response.getResponseText()
				)

		println "GET /api/users/${GlobalVariable.createdUserId} executed"
		println "Response: ${response.getResponseText()}"
	}


	// THEN - REST CONSUMER
	@Then("the response status code should be 200")
	def verifyGetUserResponseStatusCode() {

		WS.verifyResponseStatusCode(
				response,
				200
				)

		println "Response status code: 200"
	}


	@Then("the response should contain the requested user information")
	def verifyRequestedUserInformation() {

		assert jsonResponse.id != null
		assert jsonResponse.name != null
		assert jsonResponse.email != null

		WS.verifyEqual(
				jsonResponse.id.toString(),
				GlobalVariable.createdUserId
				)

		WS.verifyEqual(
				jsonResponse.name,
				'Vergi'
				)

		WS.verifyEqual(
				jsonResponse.email,
				'vergi@example.com'
				)

		println "Consumed User ID: ${jsonResponse.id}"
		println "Consumed User Name: ${jsonResponse.name}"
		println "Consumed User Email: ${jsonResponse.email}"
	}
}