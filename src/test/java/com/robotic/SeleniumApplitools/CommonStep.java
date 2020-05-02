package com.robotic.SeleniumApplitools;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class CommonStep {
	WebDriver driver;
   
    //helps to generate the logs in test report.

	public static Scenario scenario;
	private static String scenarioName;
	public static ExtentTest extenttest;
	
	
	@Before
	public void setup(Scenario scenario){
		extenttest=TestRunner.getExtentReport().startTest(scenario.getName());
		CommonStep.scenario=scenario;
		scenarioName=scenario.getName();
		SeleniumUtil.getInstance();
		driver=SeleniumUtil.getDriver();
	}
    
	
	@Before("@beforedeployment")
	public void login1(){
		System.out.println("Delete all screenshot before executing before deployment scenario");
		File ScreenShotDir= new File(System.getProperty("user.dir")+"//src//test//resources//SnapShot");
		for(File file:ScreenShotDir.listFiles()){
			file.delete();
		}
		
	}
		
	
	
	@After
	public void tearDown(Scenario scenario) throws IOException{
		
		scenario.write("I am inside tear down");
		if(driver != null){
		if(scenario.isFailed()){	
		SeleniumUtil.EmbedScreenShot(CommonStep.scenario);
		SeleniumUtil.failTestStep(driver, CommonStep.getExtentTest(), "Fail");
		}else{	
		//	test.log(Status.FAIL, SeleniumUtil.takeScreenShotReturnPath());
		}
		
		driver.close();
		
		SeleniumUtil.setDriver(null);
		}
		
	
		
	}
	
	
	
	public static ExtentTest getExtentTest() {
		return extenttest;
	}
	
	 public static String getScenarioName() {
			return scenarioName;
		}
	 
	 
}
