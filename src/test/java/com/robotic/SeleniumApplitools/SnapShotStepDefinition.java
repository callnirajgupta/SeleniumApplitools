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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.io.Files;

import cucumber.api.java.en.Given;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class SnapShotStepDefinition {
	private String screenShotPath = System.getProperty("user.dir") + "\\src\\test\\resources\\SnapShot\\";

	@Given("^user navigate to Advance testing page and take screenshot$")
	public void user_navigate_to_home_page_and_take_screen() throws Throwable {

		SeleniumUtil.navigate("https://www.webpagetest.org/");
		Thread.sleep(2000);
		takeSnapShot(SeleniumUtil.getDriver(), "Advance");

		SeleniumUtil.PassTestStep(SeleniumUtil.getDriver(), CommonStep.getExtentTest(),
				"Women page loaded successfully");
		
		    WebElement logoElemnent = SeleniumUtil.getDriver().findElement(By.xpath("//a[@title='QUANTIL - Global Content Delivery Network']/div"));
		    
		    Screenshot logoElementScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(SeleniumUtil.getDriver(),logoElemnent);
			ImageIO.write(logoElementScreenshot.getImage(), "png", new File(screenShotPath+"Quantil.png"));
	        
	        SeleniumUtil.PassTestStep(SeleniumUtil.getDriver(), CommonStep.getExtentTest(),
					"Women page loaded successfully");

	}

	@Given("^user navigate to simple testing page and take screenshot$")
	public void user_navigate_to_best_seller_page_and_take_screen() throws Throwable {

		SeleniumUtil.getDriver().findElement(By.xpath("//a[text()='Simple Testing']")).click();
		Thread.sleep(2000);

		takeSnapShot(SeleniumUtil.getDriver(), "Simple");

		SeleniumUtil.PassTestStep(SeleniumUtil.getDriver(), CommonStep.getExtentTest(),
				"Simple Testing loaded successfully");
		SeleniumUtil.EmbedScreenShot(CommonStep.scenario);
	}

	@Given("^user navigate to Advance testing page and validate Advance testing page$")
	public void user_navigate_to_home_page_and_validate_home_page() throws Throwable {

		SeleniumUtil.navigate("https://www.webpagetest.org/");
		Thread.sleep(2000);

		if (!compareImage("Advance")) {

			SeleniumUtil.PassTestStep(SeleniumUtil.getDriver(), CommonStep.getExtentTest(),
					"Advance Test ScreenShot matching");
		} else {
			System.out.println("Advance test screen Shot not matching");
			SeleniumUtil.failTestStep(SeleniumUtil.getDriver(), CommonStep.getExtentTest(),
					"Advance Test ScreenShot  not matching and actual Screen");
			SeleniumUtil.failTestStepExpected(SeleniumUtil.getDriver(), CommonStep.getExtentTest(),
					"Advance Test ScreenShot  expected", screenShotPath + "Advance.png");

		}
		
		 WebElement logoElemnent = SeleniumUtil.getDriver().findElement(By.xpath("//a[@title='QUANTIL - Global Content Delivery Network']/div"));
	    
	     if (!compareElementImage("Quantil",logoElemnent)) {

				SeleniumUtil.PassTestStep(SeleniumUtil.getDriver(), CommonStep.getExtentTest(),
						"Quantil logo ScreenShot matching");
			} else {
				System.out.println("Quantil Logo screen Shot not matching");
				SeleniumUtil.failTestStepWebElement(SeleniumUtil.getDriver(), CommonStep.getExtentTest(),
						"Quantil logo ScreenShot  not matching and actual Screen",logoElemnent);
				SeleniumUtil.failTestStepExpected(SeleniumUtil.getDriver(), CommonStep.getExtentTest(),
						"Quantil logo ScreenShot  expected", screenShotPath + "Quantil.png");

			}
	     
	}

	@Given("^user navigate to simple testing page validate simple testing page$")
	public void user_navigate_to_best_seller_page_validate_home_page() throws Throwable {
		SeleniumUtil.getDriver().findElement(By.xpath("//a[text()='Simple Testing']")).click();
		Thread.sleep(2000);

		if (!compareImage("Simple")) {

			SeleniumUtil.PassTestStep(SeleniumUtil.getDriver(), CommonStep.getExtentTest(),
					"Simple test ScreenShot matching");
		} else {
			System.out.println("simple test screen Shot not matching");
			SeleniumUtil.failTestStep(SeleniumUtil.getDriver(), CommonStep.getExtentTest(),
					"Simple test ScreenShot  not matching and actual image");
			SeleniumUtil.failTestStepExpected(SeleniumUtil.getDriver(), CommonStep.getExtentTest(),
					"Simple test ScreenShot  expected", screenShotPath + "Simple.png");

		}

	}

	public void takeSnapShot(WebDriver driver, String filename) throws Exception {

		// String screenShotPath=
		// System.getProperty("user.dir")+"//src//test//resources//SnapShot//";
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(scrFile, new File(screenShotPath + filename + ".png"));

	}

	public boolean compareImage(String ExpectedImage) throws IOException {
		Screenshot shot = new AShot().takeScreenshot(SeleniumUtil.getDriver());
		File file = new File(screenShotPath + ExpectedImage + ".png");
		// System.out.println(file);
		// ImageIO.write(shot.getImage(), "PNG", file);
		// Getting Expected Image
		BufferedImage expectedImg = ImageIO.read(file);
		// Getting Actual Image
		BufferedImage actualImg = shot.getImage();
		// Image Comparison
		ImageDiffer imgDiff = new ImageDiffer();
		ImageDiff dif = imgDiff.makeDiff(expectedImg, actualImg);
		return dif.hasDiff();
	}
	
	
	public boolean compareElementImage(String ExpectedImage,WebElement logoElemnent) throws IOException {
		Screenshot logoElementScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(SeleniumUtil.getDriver(),logoElemnent);
		
		File file = new File(screenShotPath + ExpectedImage + ".png");
		
		BufferedImage expectedImg = ImageIO.read(file);
		// Getting Actual Image
		BufferedImage actualImg = logoElementScreenshot.getImage();
		// Image Comparison
		ImageDiffer imgDiff = new ImageDiffer();
		ImageDiff dif = imgDiff.makeDiff(expectedImg, actualImg);
		return dif.hasDiff();
	}

}
