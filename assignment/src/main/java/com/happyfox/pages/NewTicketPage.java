package com.happyfox.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.happyfox.utils.WaitUtil;

public class NewTicketPage extends BasePage {

	public NewTicketPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	private String verifyAttachment = "//span[@class='hf-attach-file_name']";
	private String confirmationSection = "//div[@class='hf-custom-message-after-ticket-creation_content hf-editor-reset_list']";

	private WebElement enterSubjectField() {
		return driver.findElement(By.id("id_subject"));
	}

	private WebElement enterMessageField() {
		return driver.findElement(By.xpath("//div[contains(@id,'contents')]//div[@role='textbox']"));
	}

	private WebElement addAttachemntButton() {
		return driver.findElement(By.xpath("//input[@type='file']"));
	}

	private WebElement addNameField() {
		return driver.findElement(By.id("id_name"));
	}

	private WebElement addEmailField() {
		return driver.findElement(By.id("id_email"));
	}

	private WebElement createTicketButton() {
		return driver.findElement(By.xpath("//button[@id='submit']"));
	}

	private WebElement attachedFile() {
		return driver.findElement(By.xpath("//span[@class='hf-attach-file_name']"));
	}

	private WebElement confirmationMessage() {
		return driver.findElement(By.xpath(confirmationSection));
	}

	public void enterContactDetailsAndSubmit(String name, String email) {
		addNameField().sendKeys(name);
		addEmailField().sendKeys(email);
		createTicketButton().click();
	}

	public void enterSubjectAndMessage(String subject, String message) {
		enterSubjectField().sendKeys(subject);
		enterMessageField().click();
		enterMessageField().sendKeys(message);
		createTicketButton().click();
	}

	public void addAttachment() {
		addAttachemntButton().sendKeys("/Users/deepanmurugesan/HappyFox/assignment/screenshots/screenshot.png");
	}

	public Boolean verifyAttachment() {
		WaitUtil.waitForElementPresent(By.xpath(verifyAttachment));
		return attachedFile().isDisplayed();
	}

	public String getConfirmationMessage() {
		WaitUtil.waitForElementPresent(By.xpath(confirmationSection));
		return confirmationMessage().getText();
	}

}
