package com.happyfox.pages;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

public class BasePage extends Page{
	
	protected WebDriver driver;
    protected WebDriverWait wait;
    private ExtentTest logger;

	public BasePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        
	}

	
	@Override
	public String getPagetitle() {
		return driver.getTitle();
	}

	@Override
	public WebElement getElement(By Locator) {
		
		try {
			return wait.until(ExpectedConditions.presenceOfElementLocated(Locator));
		} 
		catch(Exception e) {
			throw new NoSuchElementException("Element not found with locator: " + Locator);
		}
			    			
	}
	
	public void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

	@Override
	public void waitForElement(By Locator) {
		// TODO Auto-generated method stub
		try {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Locator));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void closeBrowser() {
        driver.quit();
    }

}
