import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

public class TestScript006 {
	@Test(dataProvider = "loginData")
	public void testCase05(String username, String password) throws Exception {

		System.setProperty("webdriver.firefox.driver","C:\\firefox\\geckodriver.exe");
		Thread.sleep(2000);	
    	WebDriver driver = new FirefoxDriver();
		String baseUrl = KeysExternal.BASE_URL;
		driver.get(baseUrl + "/V4/");
		Thread.sleep(2000);
		String actualBoxMsg;
		driver.findElement(By.name("uid")).clear();
		driver.findElement(By.name("uid")).sendKeys(username);
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("btnLogin")).click();
		Thread.sleep(2000);
	    try{
	       	Alert alt = driver.switchTo().alert();
			actualBoxMsg = alt.getText(); 
			alt.accept();				
			assertEquals(actualBoxMsg,KeysExternal.EXPECT_ERROR);
		} 
	    catch (NoAlertPresentException Ex){
	    	String pageText = driver.findElement(By.xpath("/html/body/table/tbody")).getText();
			assertTrue(pageText.contains(username.substring(0, 4)));
			assertTrue(pageText.contains(username.substring(4)));
			Thread.sleep(2000);
			// Take the screenshot
	        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

	        // Specify the path and filename of the screenshot
	        String screenshotPath = "C:\\Users\\Amir\\eclipse-workspace\\TestNGSample\\test-output\\screenshot.png";

	        try {
	            // Copy the screenshot file to the specified location
	            FileHandler.copy(screenshotFile, new File(screenshotPath));
	            System.out.println("Screenshot saved to " + screenshotPath);
	        } catch (IOException e) {
	            System.out.println("Error saving screenshot: " + e.getMessage());
	        }
        }
	    driver.manage().deleteAllCookies();
	    driver.close();
	}
	
	@DataProvider(name = "loginData")
	public Object[][] TestingData() throws Exception {
		
		return new Object[][] {
            { KeysExternal.USER_NAME, KeysExternal.USER_PASSWORD },
            /*
            { "invalid", "valid" },
            { "valid", "invalid" },
            { "invalid", "invalid" },
			{ KeysExternal.USER_NAME, KeysExternal.USER_PASSWORD }
			*/
    };
    }
	
}
