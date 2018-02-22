package testScripts;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class JavaScriptExecuter {
	
	
	public static WebDriver driver; 
	@BeforeTest
	public void loadPropertiesFile() 
	{

		System.setProperty("webdriver.ie.driver", "C:/Users/bhavik.p/Downloads/IEDriverServer_Win32_3.8.0/IEDriverServer.exe");
		driver = new InternetExplorerDriver();

		driver.get("http://www.google.com");
		driver.manage().window().maximize();
	}
	@Test()
	public void Create_Tweet() throws InterruptedException 
	{
	System.out.println("IE");
	Thread.sleep(5000);
	String TexttoEnter = "Kirtesh";
	WebElement test = driver.findElement(By.xpath("//*[@id='lst-ib']"));
	JavascriptExecutor jse = (JavascriptExecutor) driver;
	//jse.executeScript("document.getElementById('lst-ib').value='someValue';");
	 // can use above or below line of code to enter value in textbox
	jse.executeScript("arguments[0].value='"+TexttoEnter+"';", test);
	}
	
	

}
