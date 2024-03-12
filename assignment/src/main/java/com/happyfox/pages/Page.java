package com.happyfox.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;



public abstract class Page {
	
	WebDriver driver;
	WebDriverWait wait;
	
	public Page(WebDriver driver) {
		this.driver = driver;
	}
	
	public abstract String getPagetitle();
	
	public abstract WebElement getElement(By Locator);
	
	public abstract void waitForElement(By Locator);
	
	public abstract void waitForElementToBeClickable(By Locator);
	
	public <TPage extends BasePage> TPage getInstance(Class<TPage> pageClass) {
		
		try {
			return pageClass.getDeclaredConstructor(WebDriver.class).newInstance(this.driver);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
		
	}

}
