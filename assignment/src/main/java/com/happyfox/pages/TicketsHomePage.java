package com.happyfox.pages;

import java.time.Duration;

import com.happyfox.utils.Constants;
import com.happyfox.utils.WaitUtil;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TicketsHomePage  extends BasePage {

    public TicketsHomePage(WebDriver driver) {
        super(driver);
    }

    private String ticketsSpanLocator = "//span[@title='Tickets']";
    private String ticketsList= "//section[@data-test-id='list-scrollable-pane']";
    private String statuses = "//a[contains(text(),'Statuses')]";
    private String priorities = "//a[contains(text(),'Priorities')]";
    private String manageSpanLocator = "//span[@title='Manage']";
    private String staffMenu="//div[@data-test-id='staff-menu']";
    private String logout = "//li[contains(@class,'hf-mod-logout')]";
    
   
   private  WebElement statuses() {
    	return driver.findElement(By.xpath(statuses));
    }
   
   private  WebElement priorities() {
   		return driver.findElement(By.xpath(priorities));
   }

   
    public boolean isTicketsHomepageDisplayed() {
    	waitForElement(By.xpath(ticketsSpanLocator));
        return driver.findElement(By.xpath(ticketsSpanLocator)).isDisplayed();
    }

    public boolean isTicketsListDisplayed() {
    	waitForElement(By.xpath(ticketsList));
        return driver.findElement(By.xpath(ticketsList)).isDisplayed();
    }
	
    public String getPagetitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25)); // Adjust timeout as needed
        wait.until(ExpectedConditions.titleIs(Constants.ticket_page_title)); 
        return driver.getTitle();
    }
    
    private  WebElement staffMenu() {
   		return driver.findElement(By.xpath(staffMenu));
   }
    
    private  WebElement logout() {
   		return driver.findElement(By.xpath(logout));
   }
    
    
    public void navigateToStauses(WebDriver driver){
    	Actions actions = new Actions(driver);
    	WebElement ticketsSpan = driver.findElement(By.xpath(ticketsSpanLocator));
        actions.moveToElement(ticketsSpan).build().perform();
        
        wait.until(ExpectedConditions.visibilityOf(statuses()));
        wait.until(ExpectedConditions.elementToBeClickable(statuses()));
        actions.moveToElement(statuses()).click().build().perform();
        
    }
    
    
    public void navigateToPriorities(WebDriver driver) {
    	Actions actions = new Actions(driver);
    	WebElement manageSpan = driver.findElement(By.xpath(manageSpanLocator));
        actions.moveToElement(manageSpan).build().perform();
    	
        wait.until(ExpectedConditions.visibilityOf(priorities()));
        wait.until(ExpectedConditions.elementToBeClickable(priorities()));
        actions.moveToElement(priorities()).click().build().perform();
    }
    	
    public void logoutAgent() {
    	WaitUtil.waitForElementToBeClickable(By.xpath(staffMenu));
    	staffMenu().click();
    	WaitUtil.waitForElementToBeClickable(By.xpath(logout));
    	logout().click();
    	
    }
        
	
}
