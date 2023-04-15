import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


import org.openqa.selenium.firefox.FirefoxOptions;

public class TestScript006 {
	
	public String FolderPath = "C:\\Users\\Amir\\eclipse-workspace\\TestNGSample\\test-output\\TestScreenShot\\TestExecution";
	@BeforeTest
	public void CreateSCFolder() {
		
		File folder = new File(FunctExternal.GetFolderName(FolderPath));
        // Check if the folder already exists
        if (!folder.exists()) {
        	FolderPath = FunctExternal.GetFolderName(FolderPath);
            folder.mkdir();
        }  
	}
	
	@SuppressWarnings("deprecation")
	@Test(dataProvider = "loginData")
	public void testCase06(String username, String password) throws Exception {

		FirefoxOptions options = new FirefoxOptions();
	    options.setHeadless(true);
	    WebDriver driver = new FirefoxDriver(options);
		String baseUrl = KeysExternal.BASE_URL;
		driver.get(baseUrl + "/V4/");
		Thread.sleep(500);
		String actualBoxMsg;
		driver.findElement(By.name("uid")).clear();
		driver.findElement(By.name("uid")).sendKeys(username);
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("btnLogin")).click();
		Thread.sleep(1000);
		LocalTime currentTime = LocalTime.now();
		DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("_HH_mm_ss");
		
		try{
	       	Alert alt = driver.switchTo().alert();
			actualBoxMsg = alt.getText(); 
			alt.accept();				
			assertEquals(actualBoxMsg,KeysExternal.EXPECT_ERROR);
			Thread.sleep(1000);
			
			// Take the screenshot
	        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        // Specify the path and filename of the screenshot
	        String screenshotPath = FolderPath+"Screenshot"+currentTime.format(formatterTime)+".png";

	        try {
	            // Copy the screenshot file to the specified location
	            FileHandler.copy(screenshotFile, new File(screenshotPath));
	            System.out.println("Screenshot saved to " + screenshotPath);
	        } catch (IOException e) {
	            System.out.println("Error saving screenshot: " + e.getMessage());
	        }
	         
		} 
		
	    catch (NoAlertPresentException Ex){
	    	
	    	String pageText = driver.findElement(By.xpath("/html/body/table/tbody")).getText();
			assertTrue(pageText.contains(username.substring(0, 4)));
			assertTrue(pageText.contains(username.substring(4)));
			Thread.sleep(1000);
			
			// Take the screenshot
	        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        // Specify the path and filename of the screenshot
	        String screenshotPath = FolderPath+"Screenshot"+currentTime.format(formatterTime)+".png";

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
            { "invalid", "valid" },
            { "valid", "invalid" },
            { "invalid", "invalid" },
			{ KeysExternal.USER_NAME, KeysExternal.USER_PASSWORD }
			
    };
    }
	
	
	
	
	
	
}
