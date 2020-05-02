package com.robotic.SeleniumApplitools;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/", tags ={"@afterdeployment"}, plugin = {
		"pretty", "html:target/cucumber-html-report",
		"json:target/cucumber.json","junit:target/cucumber.xml"  }, glue = { "com.robotic" } )


public class TestRunner {
	
	public static ExtentReports report;
	public static String reportName;
	@BeforeClass
	public static void beforeclass(){
		reportName = System.getProperty("user.dir")+"/target/test-report.html";
		report=new ExtentReports(reportName);
		
		File ScreenShotDir= new File(System.getProperty("user.dir")+"//src//test//resources//ExtendReportSnap//");
		for(File file:ScreenShotDir.listFiles()){
			file.delete();
		}
		
		
	}
	
	//public static ExtentHtmlReporter getHtmlReporter() {
		//return htmlReporter;
	//}

	public static ExtentReports getExtentReport() {
		return report;
	}
    
	@AfterClass
	public static void afterClass(){
		report.flush();
		
	}
	
}
