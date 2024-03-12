package com.happyfox.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.happyfox.utils.WaitUtil;


public class TicketDetailsPage  extends BasePage {

	public TicketDetailsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	private String ticketStatus="//span[@class='hf-ticket-status_name']";
	private String ticketPriority="//div[@data-test-id='ticket-box_priority']";
	private String cannedAction="//span[@data-test-id='canned-action-trigger']";
	private String searchBox="//input[@class='ember-power-select-search-input']";
	private String useThisButton="//button[@data-test-id='hf-add-canned-action']";
	private String replyToCustomer="//button[@data-test-id='add-update-reply-button']";
	private String statuses = "//a[contains(text(),'Statuses')]";
	private String ticketsSpanLocator = "//span[@title='Tickets']";
	
	private WebElement ticketStatus() {
		return driver.findElement(By.xpath(ticketStatus));
	}
	private WebElement ticketPriority() {
		return driver.findElement(By.xpath(ticketPriority));
	}
	
	private WebElement linkToTicketDetails(String name) {
		return driver.findElement(By.xpath("//a[@title='"+name+"']"));
	}
	
	private WebElement replyToTicket() {
		return driver.findElement(By.xpath("//a[@data-test-id='reply-link']"));
	}
	
	private WebElement cannedActionButton() {
		return driver.findElement(By.xpath(cannedAction));
	}
	
	private WebElement cannedActionSearchBox() {
		return driver.findElement(By.xpath("//input[@class='ember-power-select-search-input']"));
	}
	
	private WebElement nameInSearchBox(String name) {
		return driver.findElement(By.xpath("//li[text()='"+name+"']"));
	}
	
	private WebElement useThisButton() {
		return driver.findElement(By.xpath(useThisButton));
	}
	
	private WebElement replyToCustomerButton() {
		return driver.findElement(By.xpath(replyToCustomer));
	}
	
	public String getTicketStatus() {
		return ticketStatus().getText();
	}
	
	public String getTicketPriority() {
		return ticketPriority().getText();
	}
	
	public void openTicketDetails(String name) {
		WaitUtil.waitForElementPresent(By.xpath("//h1[@class='hf-ticket-list-header-title']"));
		linkToTicketDetails(name).click();
	}
	
	private  WebElement statuses() {
    	return driver.findElement(By.xpath(statuses));
    }
	
	public void navigateToStauses(WebDriver driver){
    	Actions actions = new Actions(driver);
    	WebElement ticketsSpan = driver.findElement(By.xpath(ticketsSpanLocator));
        actions.moveToElement(ticketsSpan).build().perform();
        
        wait.until(ExpectedConditions.visibilityOf(statuses()));
        wait.until(ExpectedConditions.elementToBeClickable(statuses()));
        actions.moveToElement(statuses()).click().build().perform();
        
    }
	
	public void clickOnReply() {
		 replyToTicket().click();
		 WaitUtil.waitForElementPresent(By.xpath(cannedAction));
	}
	
	
	public void clickOnCannedAction() {
		 cannedActionButton().click();
		 WaitUtil.waitForElementPresent(By.xpath(searchBox));
	}
	
	public void enterCannedActionNameAndSelect(String name) {
		cannedActionSearchBox().sendKeys(name);
		WaitUtil.waitForElementPresent(By.xpath("//li[text()='"+name+"']"));
		nameInSearchBox(name).click();
		WaitUtil.waitForElementPresent(By.xpath(useThisButton));
		useThisButton().click();
		WaitUtil.waitForElementToBeClickable(By.xpath(replyToCustomer));
		replyToCustomerButton().click();
	}

}
