package com.happyfox.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.happyfox.utils.Constants;
import com.happyfox.utils.WaitUtil;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	private String logoutConfirmation ="//div[@class='confirmation']";
	
	public void openAgentLogin() {
        driver.get(Constants.agent_url);
        waitForElement(By.id("id_username"));
        
    }
	
	public void openUserLogin() {
        driver.get(Constants.customer_url);
        waitForElement(By.id("id_subject"));
    }
		
	
	private WebElement getUsernameField() {
        return driver.findElement(By.id("id_username"));
    }
    
	private WebElement getPasswordFiesld() {
		return driver.findElement(By.id("id_password"));
	}
	
	private WebElement loginButton() {
		return driver.findElement(By.id("btn-submit"));
	}
	
	private WebElement userNameErrorMessage() {
		return driver.findElement(By.cssSelector("div[class='xhr_errors errors_username']"));
	}
	
	private WebElement logoutConfirmation() {
		return driver.findElement(By.xpath(logoutConfirmation));
	} 
	
	public Boolean  logoutConfirmationMessage() {
		return logoutConfirmation().isDisplayed();
	}
	
	public String getLogOutMessage() {
		WaitUtil.waitForElementPresent(By.xpath(logoutConfirmation));
		return logoutConfirmation().getText();
	}
	
	
	public void login(String username, String password) {
        getUsernameField().sendKeys(username);
        getPasswordFiesld().sendKeys(password);
        loginButton().click();
    }
	
	public void enterUsername(String username) {
		getUsernameField().sendKeys(username);
	}
	
	public void enterPassword(String password) {
		getPasswordFiesld().sendKeys(password);
	}
	
	
	public void clickLogin() {
        loginButton().click();
    }
	
	public boolean verifyUsernameErrorMessage() {
	    WebElement errorMessageElement = userNameErrorMessage();
	    String actualText = errorMessageElement.getText().trim(); 
	    String expectedText = "This field is required.";
	    return actualText.equals(expectedText);
	}
	
}
