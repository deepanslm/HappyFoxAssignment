package com.happyfox.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtil {
	
	private static WebDriver driver;
	

    public static void setDriver(WebDriver driver) {
        WaitUtil.driver = driver;
    }

    public static WebElement waitForElementToBeClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
 
    public static WebElement waitForElementPresent(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public static void waitForTitleLoad(String title) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        wait.until(ExpectedConditions.titleIs(title));
    }
    
    public static void waitForElementToDisappear(By locator ) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25)); 
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }   
    
    
	
}
