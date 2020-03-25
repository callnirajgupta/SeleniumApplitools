package com.robotic.SeleniumApplitools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;


public class VisualTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//src//test//resources//chromedriverwin.exe");
		WebDriver driver=null;
		Eyes eyes = new Eyes();

		// Raise an error if no API Key has been found.
		

		// Set your personal Applitols API Key from your environment variables.
		eyes.setApiKey("Ix9Iory7ugeWC1GU3dRJ4Pq3ef9m6bbdeUd102103mCXJzw110");
		try{
			
		 driver = new ChromeDriver();
		 eyes.open(driver, "pluralsight.com", "Visual Test", new RectangleSize(1000, 600));

			// Navigate the browser to the "ACME" demo app.
			driver.get("https://www.pluralsight.com/");
			driver.manage().window().maximize();
			eyes.checkWindow(30, "Home page");
			WebDriverWait wait= new WebDriverWait(driver,60);
			wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Courses")));
			driver.findElement(By.partialLinkText("Courses")).click();
			driver.findElement(By.partialLinkText("Products")).click();
			Thread.sleep(2000);
			eyes.checkWindow("course page");
			eyes.close();

				
		}catch(Exception e){
			
		}finally {
			driver.close();
			driver.quit();
		}
		
        
		

	}

}
