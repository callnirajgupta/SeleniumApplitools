package com.robotic.SeleniumApplitools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.io.Files;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.Scenario;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class SeleniumUtil {
	
	private static SeleniumUtil seleniumUtil;
	private static WebDriver driver; 
	public static Properties configProperties;
	private static String screenShotFolder=System.getProperty("user.dir")+"\\src\\test\\resources\\ExtendReportSnap\\";
	static{
		 String configFile=System.getProperty("user.dir")+"//src//test//resources//Config.properties";
	    configProperties=new Properties();
			
			try{
				FileInputStream fisConfig=new FileInputStream(configFile);
				
				configProperties.load(fisConfig);
				
	}catch(Exception e){
		e.printStackTrace();
	}
		
	}
	private SeleniumUtil(){
		
	}
  
	public static SeleniumUtil getInstance(){
		
		if(seleniumUtil==null){
			seleniumUtil= new SeleniumUtil();
		}
		
		return seleniumUtil; 
		
	}
	
	public static WebDriver getDriver(){
		
		if(driver==null){
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//src//test//resources//chromedriver.exe");
			driver = new ChromeDriver();
		}
		
		return driver;
		
	}
	
	public static void navigate(String url){
		driver.manage().window().maximize();
		driver.get(url);
	}
	
	
	public static void setDriver(WebDriver driver){
		SeleniumUtil.driver=driver;
	}
    
	public static void clickLink(By locator){
		driver.findElement(locator).click();
		
	}
	
	public static void isElementDiplayed(By Locator){
		driver.findElement(Locator).isDisplayed();
		
	}
	
	public static void takeScreenShot() throws IOException{
		//String screenShotFolder=System.getProperty("user.dir")+"\\src\\test\\resources\\ExtendReportSnap\\";
		Date date= new Date();
		Long time=date.getTime();
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(scrFile, new File(screenShotFolder  + time+".png"));
		
	}
	
	public static String takeScreenShotReturnPath() throws IOException{
		//String screenShotFolder=System.getProperty("user.dir")+"\\src\\test\\resources\\ExtendReportSnap\\";
		Date date= new Date();
		Long time=date.getTime();
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String imagePath=screenShotFolder  + time+".png";
		Files.copy(scrFile, new File(imagePath));
		return imagePath;
	}
	
	public static void EmbedScreenShot(Scenario scenario){
		final byte[] screenshot =  ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		scenario.embed(screenshot, "image/png"); // stick it in the report
		
	}
	
	public static  void failTestStep(WebDriver driver,ExtentTest test,String failureMessage) throws IOException{
		String imagePath=takeScreenShotReturnPath();
		
		String snapPath=test.addScreenCapture(imagePath);
		                
		//String snapPath=test.addScreenCapture(imagePath);
		
		test.log(LogStatus.FAIL, failureMessage,snapPath);
		
	}
	public static  void failTestStepWebElement(WebDriver driver,ExtentTest test,String failureMessage,WebElement logoElemnent) throws IOException{
		//String imagePath=takeScreenShotReturnPath();
		 String screenShotPath = System.getProperty("user.dir") + "\\src\\test\\resources\\SnapShot\\";
		Screenshot logoElementScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver,logoElemnent);
		ImageIO.write(logoElementScreenshot.getImage(), "png", new File(screenShotPath+"testlogo.png"));
		
		String snapPath=test.addScreenCapture(screenShotPath+"testlogo.png");  
		test.log(LogStatus.FAIL, failureMessage,snapPath);
		
	}
	
	public static  void failTestStepExpected(WebDriver driver,ExtentTest test,String failureMessage,String screenshotFile) throws IOException{
		
		
		String snapPath=test.addScreenCapture(screenshotFile);
		         System.out.println("print image path for step:");       
		//String snapPath=test.addScreenCapture(imagePath);
		
		test.log(LogStatus.FAIL, failureMessage,snapPath);
		
	}
	
	
	
	
	public static void PassTestStep(WebDriver driver,ExtentTest test,String passMessage) throws IOException{
		String imagePath=takeScreenShotReturnPath(); 
		String snapPath=test.addScreenCapture(imagePath);
		test.log(LogStatus.PASS, passMessage,snapPath);
	}
}
