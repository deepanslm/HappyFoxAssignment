package com.happyfox.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.log4testng.Logger;

import com.happyfox.utils.Constants;
import com.happyfox.utils.WaitUtil;

public class PriorityPage  extends BasePage {

	public PriorityPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	private String addPriorityButton="//button[@data-test-id='new-priority']";
	private String enterPriorityBox="//input[@data-test-id='form-field-name']";
	private String descrptionBox = "//textarea[@data-test-id='form-field-description']";
	private String addPriority="//button[@data-test-id='add-priority']";
	private String addPriorityOverlay="//div[contains(@class,'header_title')]";
	private String deleteButton="//a[normalize-space()='Delete']";
	private String statuses = "//a[contains(text(),'Statuses')]";
	private String confirmationDialog="//header[@class='hf-confirmation-dialog_body_header']";
	private String deleteWarning="//div[@class='hf-confirmation-dialog_body_field-note hf-mod-delete']";
	private String deleteConfimation ="//button[@data-test-id='delete-dependants-primary-action']";
	private String toastMessage="//div[contains(@class,'hf-toast-message')]";
	private String manageSpanLocator = "//span[@title='Manage']";
	private String choosePriority="//span[normalize-space()='Choose Priority']";
	private String chooseLowPriority="//li[normalize-space()='Low']";
	
	private static final Logger logger = Logger.getLogger(PriorityPage.class.getClass());

	private  WebElement addPriorityButton() {
		
	  	return driver.findElement(By.xpath(addPriorityButton));
	  
	}
	
	private  WebElement addPriorityName() {
		
	  	return driver.findElement(By.xpath(enterPriorityBox));
	  
	}
	
	private  WebElement addDescription() {
		
	  	return driver.findElement(By.xpath(descrptionBox));
	  
	}

	private  WebElement addPriority() {
	
		return driver.findElement(By.xpath(addPriority));
  
	}
	
	private WebElement createdPriority(String name) {
	    return driver.findElement(By.xpath("//span[@title='" + name + "']"));
	}
	
	
	public String getObjectId(String name) {
		WebElement priorityElement = driver.findElement(By.xpath("//span[@title='" + name + "']"));
		
		WebElement parentElement = priorityElement.findElement(By.xpath("./ancestor::tr"));
		// Get id from parent and return
		String attributeValue = parentElement.getAttribute("data-draggable-object-id");
		return attributeValue;
		
	}

	private WebElement newPriorityMakeDefault(String name) {
		String id = getObjectId(name);
		return driver.findElement(By.xpath("//div[@data-test-id='make-default-" + id + "']"));
	}
	
	public void waitForToastToDisappear() {
		WaitUtil.waitForElementToDisappear(By.xpath(toastMessage));
	}

	public void makeDefault(String name) {
		
		if(newPriorityMakeDefault(name) != null) {
			newPriorityMakeDefault(name).click();
		}
		else {
			logger.error("Make Default element is not found");
		}
		WaitUtil.waitForElementPresent(By.xpath("//div[@data-test-id='default-priority']"));
		
	}
	private  WebElement priorityToBeDeleted(String name) {
		
	  	return driver.findElement(By.xpath("//span[@title='" + name + "']"));
	  
	}
	
	private  WebElement statuses() {
    	return driver.findElement(By.xpath(statuses));
    }
	
	private  WebElement addPriorityOverlay() {
		
	  	return driver.findElement(By.xpath(addPriorityOverlay));
	  
		}
	
	
	private WebElement deletebutton(){
		return driver.findElement(By.xpath("//a[normalize-space()='Delete']"));
	}
	
	private WebElement deleteConfirmation(){
		return driver.findElement(By.xpath(deleteConfimation));
	}
	
	
	private WebElement confirmationDialog(){
		return driver.findElement(By.xpath("//a[normalize-space()='Delete']"));
	}
	

	public void clickOnDelete() {
    	
    	WaitUtil.waitForElementToBeClickable(By.xpath(deleteButton));
    	deletebutton().click();
    	
    }
	

	public void clickOnAddPrioriity() {
    	
    	WaitUtil.waitForElementToBeClickable(By.xpath(addPriorityButton));
    	addPriorityButton().click();
    	
    }
	
	
	
	public String getPagetitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25)); 
        wait.until(ExpectedConditions.titleIs(Constants.priorities_page_title)); 
        return driver.getTitle();
    }
	
	public void createPriority(String name,String description) {
		
		WaitUtil.waitForElementToBeClickable(By.xpath(enterPriorityBox));
		addPriorityName().click();
		addPriorityName().sendKeys(name);
		addDescription().click();
		addDescription().sendKeys(description);
		
		addPriority().click();
		WaitUtil.waitForElementToDisappear(By.xpath(addPriorityOverlay));
		
	}
	
	
	private WebElement manageSpanLocator(){
		return driver.findElement(By.xpath(manageSpanLocator));
	}
	

	public void selectPriorityToDelete(String name) {
		
		WaitUtil.waitForElementToBeClickable(By.xpath("//span[@title='" + name + "']"));
		priorityToBeDeleted(name).click();
		
		WaitUtil.waitForElementPresent(By.xpath(addPriorityOverlay));
		Assert.assertTrue(addPriorityOverlay().isDisplayed(), "The priority tab is not open");
		
		clickOnDelete();
			
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
	
	private WebElement choosePriority(){
		return driver.findElement(By.xpath(choosePriority));
	}
	
	private WebElement chooseLowPriority(){
		return driver.findElement(By.xpath(chooseLowPriority));
	}
	
	
	
	
	public void deletePriorityConfirmationModal() {
		// Should be done with Search and Delete of created Priority
		WaitUtil.waitForElementToBeClickable(By.xpath(deleteConfimation));
		
		try {
			if(choosePriority().isDisplayed()) {
				choosePriority().click();
				WaitUtil.waitForElementToBeClickable(By.xpath(chooseLowPriority));
				chooseLowPriority().click();
			}
			} catch(NoSuchElementException e) {
			e.printStackTrace();
		}
		
		deleteConfirmation().click();
			
	}
	
	public Boolean priorityDeleteSuccessToast() {
		WaitUtil.waitForElementPresent(By.xpath(toastMessage));
		return driver.findElement(By.xpath(toastMessage)).isDisplayed();
	}

	public void navigatetToStatuses(WebDriver driver) {
		
		Actions actions = new Actions(driver);
	    actions.moveToElement(manageSpanLocator()).build().perform();
	    
	    wait.until(ExpectedConditions.visibilityOf(statuses()));
	    wait.until(ExpectedConditions.elementToBeClickable(statuses()));
	    actions.moveToElement(statuses()).click().build().perform();
	}
	
	public Boolean createdPriorityCheck(String name) {
		WaitUtil.waitForElementPresent(By.xpath("//span[@title='" + name + "']"));
		return createdPriority(name).isDisplayed();
	}
	
    public void scrollToPriority(String name) {
    	Actions action = new Actions(driver);
    	action.scrollToElement(createdPriority(name));
    	action.moveToElement(createdPriority(name)).perform();
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", createdPriority(name));
    }


}
