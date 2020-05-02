package com.robotic.SeleniumApplitools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import com.google.common.io.Files;

import cucumber.api.java.en.Given;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class SnapShotStepDefinition {
	private String screenShotPath= System.getProperty("user.dir")+"\\src\\test\\resources\\SnapShot\\";
    
	
	@Given("^user navigate to Advance testing page and take screenshot$")
	public void user_navigate_to_home_page_and_take_screen() throws Throwable {
		
		SeleniumUtil.navigate("https://www.webpagetest.org/");
		Thread.sleep(2000);
		takeSnapShot(SeleniumUtil.getDriver(),"Advance");
	   
	    SeleniumUtil.PassTestStep(SeleniumUtil.getDriver(), CommonStep.getExtentTest(), "Women page loaded successfully");
	   
		
		
	        
	        
	}

	@Given("^user navigate to simple testing page and take screenshot$")
	public void user_navigate_to_best_seller_page_and_take_screen() throws Throwable {
	    
		SeleniumUtil.getDriver().findElement(By.xpath("//a[text()='Simple Testing']")).click();
		Thread.sleep(2000);
	
        takeSnapShot(SeleniumUtil.getDriver(),"Simple");
        
        SeleniumUtil.PassTestStep(SeleniumUtil.getDriver(), CommonStep.getExtentTest(), "Tshirts loaded successfully");
	    SeleniumUtil.EmbedScreenShot(CommonStep.scenario);
	}
	
	
	@Given("^user navigate to Advance testing page and validate Advance testing page$")
	public void user_navigate_to_home_page_and_validate_home_page() throws Throwable {
	    
     
		SeleniumUtil.navigate("https://www.webpagetest.org/");
		Thread.sleep(2000);
		//SeleniumUtil.getDriver().findElement(By.xpath("//a[@title='Women']")).click();
		
	
       if(!compareImage("Advance")){
        //s.find(screenShotPath+"Women.png"); 
      
		SeleniumUtil.PassTestStep(SeleniumUtil.getDriver(), CommonStep.getExtentTest(), "Advance Test ScreenShot matching");
	} else{
        	System.out.println("Advance test screen Shot not matching");
    		SeleniumUtil.failTestStep(SeleniumUtil.getDriver(), CommonStep.getExtentTest(), "Advance Test ScreenShot  not matching and actual Screen");
    		SeleniumUtil.failTestStepExpected(SeleniumUtil.getDriver(), CommonStep.getExtentTest(), "Advance Test ScreenShot  expected",screenShotPath+"Advance.png");
    		
    	
    		
        }
        
        

        
        	
	    
	}

	@Given("^user navigate to simple testing page validate simple testing page$")
	public void user_navigate_to_best_seller_page_validate_home_page() throws Throwable {
		SeleniumUtil.getDriver().findElement(By.xpath("//a[text()='Simple Testing']")).click();
    	Thread.sleep(2000);

   
       if(!compareImage("Simple")){
    
		SeleniumUtil.PassTestStep(SeleniumUtil.getDriver(), CommonStep.getExtentTest(), "Simple test ScreenShot matching");
        }else{
        	System.out.println("simple test screen Shot not matching");
    		SeleniumUtil.failTestStep(SeleniumUtil.getDriver(), CommonStep.getExtentTest(), "Simple test ScreenShot  not matching and actual image");
    		SeleniumUtil.failTestStepExpected(SeleniumUtil.getDriver(), CommonStep.getExtentTest(), "Simple test ScreenShot  expected",screenShotPath+"Simple.png"); 
    	
        }
    	
	    
	}

	
	public void takeSnapShot(WebDriver driver,String filename) throws Exception{

		
		//String screenShotPath= System.getProperty("user.dir")+"//src//test//resources//SnapShot//";
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(scrFile, new File(screenShotPath +filename+".png"));
			
}

	
	public boolean compareImage(String ExpectedImage) throws IOException{
		Screenshot shot = new AShot().takeScreenshot(SeleniumUtil.getDriver());
        File file = new File(screenShotPath+ExpectedImage+".png");
        //System.out.println(file);
        //ImageIO.write(shot.getImage(), "PNG", file);
        //Getting Expected Image
        BufferedImage expectedImg = ImageIO.read(file);
        //Getting Actual Image
        BufferedImage actualImg = shot.getImage();
        //Image Comparison
        ImageDiffer imgDiff = new ImageDiffer();
        ImageDiff dif = imgDiff.makeDiff(expectedImg, actualImg);
       return dif.hasDiff();
	}

}
