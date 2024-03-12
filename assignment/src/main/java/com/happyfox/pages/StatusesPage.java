package com.happyfox.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.happyfox.utils.Constants;
import com.happyfox.utils.WaitUtil;

public class StatusesPage extends BasePage {

	public StatusesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	private String addStatusButon = "//button[@data-test-id='new-status']";
	private String enterStatusBox = "//input[@data-test-id='form-field-name']";
	private String descrptionBox = "//textarea[@data-test-id='form-field-description']";
	private String addStatus = "//button[@data-test-id='add-status']";
	private String addStatusOverlay = "//div[contains(@class,'header_title')]";
	private String statusToBeDeleted = "//div[contains(@title,'Issue')][1]";
	private String confirmationDialog="//header[@class='hf-confirmation-dialog_body_header']";
	private String deleteButton = "//a[normalize-space()='Delete']";
	private String deleteWarning = "//div[@class='hf-confirmation-dialog_body_field-note hf-mod-delete']";
	private String deleteConfimation = "//button[@data-test-id='delete-dependants-primary-action']";
	private String chooseAllIssues = "//div[contains(@class,'ember-basic-dropdown-trigger')]//span[normalize-space()='Choose Status']";
	private String chooseDraft = "//li[normalize-space()='draft']";
	private String toastMessage = "//div[contains(@class,'hf-toast-message')]";
	private String makeDefault = "//a[@data-test-id='make-default-2411']";
	
	
	

	private WebElement addStatusButton() {

		return driver.findElement(By.xpath(addStatusButon));

	}

	private WebElement addStatusName() {

		return driver.findElement(By.xpath(enterStatusBox));

	}

	private WebElement addDescription() {

		return driver.findElement(By.xpath(descrptionBox));

	}

	private WebElement addStatus() {

		return driver.findElement(By.xpath(addStatus));

	}

	public void clickOnAddStatus() {

		WaitUtil.waitForElementToBeClickable(By.xpath(addStatusButon));
		addStatusButton().click();

	}

	public String getPagetitle() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
		wait.until(ExpectedConditions.titleIs(Constants.statuses_page_title));
		return driver.getTitle();
	}

	public void createStatus(String name, String description) {

		WaitUtil.waitForElementToBeClickable(By.xpath(enterStatusBox));

		addStatusName().click();
		addStatusName().sendKeys(name);
		addDescription().click();
		addDescription().sendKeys(description);

		addStatus().click();
		WaitUtil.waitForElementToDisappear(By.xpath(addStatusOverlay));

	}

	private WebElement deletebutton() {
		return driver.findElement(By.xpath("//a[normalize-space()='Delete']"));
	}

	private WebElement deleteConfirmation() {
		return driver.findElement(By.xpath(deleteConfimation));
	}
	
	public String getDeleteModalHeader() {
		WaitUtil.waitForElementPresent(By.xpath(confirmationDialog));
		String mainMessage = confirmationDialog().findElement(By.xpath(confirmationDialog)).getText();
		return mainMessage;
		
	}
	
	public String getWarningMessage() {
		WaitUtil.waitForElementPresent(By.xpath(deleteWarning));
		String warningMessage = driver.findElement(By.xpath(deleteWarning)).getText();
		return warningMessage;
	}

	

	public void clickOnDelete() {
		
		WaitUtil.waitForElementToBeClickable(By.xpath(chooseAllIssues));
		chooseAllIssues().click();
		WaitUtil.waitForElementToBeClickable(By.xpath(chooseDraft));
		chooseDraft().click();
		WaitUtil.waitForElementToBeClickable(By.xpath(deleteButton));
		

	}
	
	private WebElement confirmationDialog(){
		return driver.findElement(By.xpath("//a[normalize-space()='Delete']"));
	}

	private WebElement statusToBeDeleted() {

		return driver.findElement(By.xpath(statusToBeDeleted));

	}

	private WebElement statusToBeDeleted(String name) {
		return driver.findElement(By.xpath("//div[contains(@title,'" + name + "')]"));
	}

	private WebElement createdStatus(String name) {
		return driver.findElement(By.xpath("//div[contains(@title,'" + name + "')]"));
	}

	public String getObjectId(String name) {
		WebElement statusElement = driver.findElement(By.xpath("//div[contains(@title,'" + name + "')]"));
		
		System.out.println("this is the element"+ statusElement);

		WebElement parentElement = statusElement.findElement(By.xpath("./ancestor::tr"));
		// Get id from parent and return
		String attributeValue = parentElement.getAttribute("data-draggable-object-id");
		return attributeValue;
		
	}

	private WebElement newStatusMakeDefault(String name) {
		String id = getObjectId(name);
		return driver.findElement(By.xpath("//a[@data-test-id='make-default-" + id + "']"));
	}

	public void makeDefault(String name) {
		
		if(newStatusMakeDefault(name) != null) {
			newStatusMakeDefault(name).click();
		}
		else {
			System.out.println("newStatusMakeDefault is null");
		}
		
	}



	private WebElement chooseAllIssues() {

		return driver.findElement(By.xpath(chooseAllIssues));

	}

	private WebElement chooseDraft() {

		return driver.findElement(By.xpath(chooseDraft));

	}

	public Boolean statusDeleteSuccessToast() {
		WaitUtil.waitForElementPresent(By.xpath(toastMessage));
		return driver.findElement(By.xpath(toastMessage)).isDisplayed();
	}


	public void deleteStatusConfirmationModal() {
		// Should be done with Search and Delete of created Priority
		WaitUtil.waitForElementToBeClickable(By.xpath(deleteConfimation));
		deleteConfirmation().click();
			
	}
	
	public void scrollToStatusToDelete(String name) {
		WaitUtil.waitForElementPresent(By.xpath("//div[contains(@title,'" + name + "')]"));
		Actions action = new Actions(driver);
		action.scrollToElement(createdStatus(name));
		action.moveToElement(createdStatus(name)).perform();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", createdStatus(name));
	}

	public void waitForToastToDisappear() {
		WaitUtil.waitForElementToDisappear(By.xpath(toastMessage));
	}

	public void createdStatusToDelete(String name) {
		// Should be done with Search and Delete of created Priority
		WaitUtil.waitForElementToBeClickable(By.xpath("//div[contains(@title,'" + name + "')]"));
		statusToBeDeleted(name).click();
		WaitUtil.waitForElementPresent(By.xpath(addStatusOverlay));
		deletebutton().click();
		clickOnDelete();

	}

	public Boolean createdStatusCheck(String name) {
		WaitUtil.waitForElementPresent(By.xpath("//div[contains(@title,'" + name + "')]"));
		return createdStatus(name).isDisplayed();
	}

	public void scrollToStatus(String name) {
		Actions action = new Actions(driver);
		action.scrollToElement(createdStatus(name));
		action.moveToElement(createdStatus(name)).perform();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", createdStatus(name));
	}

	private WebElement makeDefaultButton() {
		return driver.findElement(By.xpath(makeDefault));
	}

	public void makeDefault(String name, WebDriver driver) {

		Actions actions = new Actions(driver);
		actions.moveToElement(createdStatus(name)).build().perform();
		WaitUtil.waitForElementToBeClickable(By.xpath(makeDefault));
		makeDefaultButton().click();
		actions.moveToElement(makeDefaultButton()).click().build().perform();

	}

	public void clickMakeDefaultOnTicketColumn(String ticketColumnName) throws InterruptedException {
		WebElement ticketElement = driver
				.findElement(By.cssSelector("td.lt-cell.align-left.ember-view div[title='" + ticketColumnName + "']"));

		Actions actions = new Actions(driver);

		actions.moveToElement(ticketElement).build().perform();

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)+100");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement makeDefaultOption = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@data-test-id,'make-default')]")));

		actions.moveToElement(makeDefaultOption).click().build().perform();

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", makeDefaultOption);

		WaitUtil.waitForElementToBeClickable(By.xpath(statusToBeDeleted));
		statusToBeDeleted().click();
	}


}