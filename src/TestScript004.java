import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;

import static org.testng.Assert.assertEquals;



public class TestScript004 {
 
	
	
	@DataProvider(name = "GuruTest")
	public Object[][] testData() throws Exception {
		int tab []=FunctExternal.InitXLSX(KeysExternal.TEST_DATA_PATH);
		return FunctExternal.readXLSXFile(KeysExternal.TEST_DATA_PATH, tab[0], tab[1]);
	}
	
	
	
	@Test(dataProvider = "GuruTest")
	public void testCase04(String username, String password) throws Exception {
		//Sitting the webdriver parameters
		System.setProperty("webdriver.firefox.driver","C:\\firefox\\geckodriver.exe");
		Thread.sleep(2000);	
    	WebDriver driver = new FirefoxDriver();
		String baseUrl = KeysExternal.BASE_URL;
		driver.get(baseUrl + "/V4/");
		Thread.sleep(2000);
		String actualTitle;
		String actualBoxMsg;
		driver.findElement(By.name("uid")).clear();
		driver.findElement(By.name("uid")).sendKeys(username);
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("btnLogin")).click();
		Thread.sleep(5000);
	    try{ 
	    
	       	Alert alt = driver.switchTo().alert();
			actualBoxMsg = alt.getText(); 
			alt.accept();				
			assertEquals(actualBoxMsg,KeysExternal.EXPECT_ERROR);
		}    
	    catch (NoAlertPresentException Ex){ 
	    	actualTitle = driver.getTitle();
	    	assertEquals(actualTitle,KeysExternal.EXPECT_TITLE);
	    	
	    	
        }
	    driver.manage().deleteAllCookies();
		Thread.sleep(2000);
	    driver.quit();
	    
	}
	
}
