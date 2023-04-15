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

public class TestScript006 {
	
	public String FolderPath = "C:\\Users\\Amir\\eclipse-workspace\\TestNGSample\\test-output\\TestScreenShot\\TestExecution";
	@BeforeTest
	public void CreateSCFolder() {
		
		File folder = new File(GetFolderName(FolderPath));
        // Check if the folder already exists
        if (!folder.exists()) {
        	FolderPath = GetFolderName(FolderPath);
            folder.mkdir();

        } 
        
	}
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
			Thread.sleep(2000);
			
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
		
		 Thread.sleep(1000);
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
	
	// Generate New Folder name based on current Time
	public String GetFolderName( String Folder_PATH) {
		LocalTime currentTime = LocalTime.now();
		LocalDate currentDate = LocalDate.now();
    	DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("_HH_mm_ss");
    	DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("_yyyy_MM_dd_");
		return Folder_PATH= Folder_PATH+currentDate.format(formatterDate)+currentTime.format(formatterTime)+"\\";
	}
	
	
	
}
