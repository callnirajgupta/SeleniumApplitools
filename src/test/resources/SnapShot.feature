@SnapShot
Feature: Verify that the page screen is matching before deplyoment and after development 

@beforedeployment
Scenario: Before deployment take screenshot
Given user navigate to Advance testing page and take screenshot 
And user navigate to simple testing page and take screenshot

@afterdeployment
Scenario: After deployment take screenshot and compare with before deployment screen
Given user navigate to Advance testing page and validate Advance testing page 
And user navigate to simple testing page validate simple testing page
