package com.happyfox.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.happyfox.utils.Constants;
import com.happyfox.utils.WaitUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HappyFoxTestBase {


	public static  WebDriver driver;
	private Properties prop;
	private ExtentSparkReporter sparkReporter;
	private ExtentReports extent;
	private ExtentTest logger;

	public HappyFoxTestBase() {
		
		// Load the prop files once
		if (prop == null) {
            try {
                prop = new Properties();
                prop.load(new FileInputStream("/Users/deepanmurugesan/HappyFox/assignment/src/main/java/com/happyfox/config/config.properties"));
            } catch (IOException e) {
                throw new RuntimeException("Failed to load configuration properties", e);
            }
        }
    }

	public String getProperty(String key) {
        return prop.getProperty(key);
    }
	
	@BeforeTest
	public void beforeTest(){
		
		String reportPath = prop.getProperty("report.path"); 
		sparkReporter = new ExtentSparkReporter(reportPath + "/HappyFoxExtentReport.html");
		extent = new ExtentReports();
		sparkReporter.config().setReportName("HappyFox Automation Sanity Test Report");
		extent.attachReporter(sparkReporter);

	}

	@AfterTest
	public void afterTest() {
		extent.flush();
	}

	@BeforeMethod
	public void beforeMethod(Method testMethod){
		logger = extent.createTest(testMethod.getName());
		setupDriver(Constants.browser);
		driver.manage().window().maximize();
		driver.get(Constants.agent_url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(55));
		WaitUtil.setDriver(driver); 

	}
	@AfterMethod
	public void afterMethod(ITestResult result){
		String testName = result.getName();
		String logMessage;

		if (result.getStatus() == ITestResult.SUCCESS) {
			logMessage = "Test Passed! " + testName;
			logger.log(Status.PASS, logMessage);
		} else if (result.getStatus() == ITestResult.FAILURE) {
			logMessage = "Test Failed! " + testName + "\n" + result.getThrowable();
			logger.log(Status.FAIL, logMessage);

			//Failed case screenshot
			String screenshotPath = captureScreenshot(testName);
			logger.log(Status.FAIL, "Screenshot: " + logger.addScreenCaptureFromPath(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logMessage = "Test Skipped! " + testName;
			logger.log(Status.SKIP, logMessage);
		} 

		driver.quit();
	}
	
	
	
	
	//Screenshot on failure

	private String captureScreenshot(String testName) {
		String screenshotPath = prop.getProperty("screenshot.path"); 
		System.out.println("Capturing screenshot for failed test: " + testName);
		return screenshotPath + testName + ".png"; 
	}

	
	// browser setup

	public void setupDriver(String browser) {
		if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();;
			driver = new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		else if(browser.equalsIgnoreCase("safari")) {
			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
		}
		else {
			throw new IllegalArgumentException(browser + " is not supported for automation run currently");
		}
	}

}
