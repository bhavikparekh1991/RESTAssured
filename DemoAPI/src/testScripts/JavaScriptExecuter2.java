package testScripts;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class JavaScriptExecuter2 {
		
	
	@Test
	public void javascripttest() throws InterruptedException
	{System.setProperty("webdriver.ie.driver", "C:/Users/bhavik.p/Downloads/IEDriverServer_Win32_3.8.0/IEDriverServer.exe");
		WebDriver driver = new InternetExplorerDriver();

		driver.get("http://www.google.com");
		driver.manage().window().maximize();
	
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
