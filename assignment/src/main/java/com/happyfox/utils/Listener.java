package com.happyfox.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IAnnotationTransformer;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.happyfox.base.HappyFoxTestBase;

public class Listener implements ITestListener  , IAnnotationTransformer{

	
//capture Screenshot on Failure
 
public void onTestFailure(ITestResult result)	{
	
	String filename = System.getProperty("user.dir") + "/screenshots/" + result.getMethod().getMethodName() + "png";
	File file = ((TakesScreenshot)HappyFoxTestBase.driver).getScreenshotAs(OutputType.FILE);
	
	try {
		FileUtils.copyFile(file, new File(filename));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

}
