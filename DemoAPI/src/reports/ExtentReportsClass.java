package reports;

import java.io.File;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


public class ExtentReportsClass {
	ExtentReports reports;
	ExtentTest testInfo;
	ExtentHtmlReporter htmlReporter;
	
	@BeforeTest
	public void startTest()
	{
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/ExtentReport.html");
		htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir")+"//extent-config.xml"));
		reports = new ExtentReports();
		reports.setSystemInfo("Host Name" ,"API Automation_Extent Report");
		reports.setSystemInfo("Environment" ,"API Automation Testing");
		reports.setSystemInfo("User Name" ,"Test Tester");
		reports.attachReporter(htmlReporter);
	}
	
	@Test
	public void passTest()
	{
		
		//Assert.assertTrue(true);
		testInfo.log(Status.INFO, "Test Case name is passTest");
	}
	
	@Test
	public void failTest()
	{
		
		Assert.assertTrue(false);
		testInfo.log(Status.INFO, "Test Case name is failTest");
	}
	
	@Test
	public void skipTest()
	{
		testInfo.log(Status.SKIP, "Test Case name is skipTest");
		throw new SkipException("Skipping the Test as its not ready for testing");
	}

	@BeforeMethod
	public void Register(Method method)
	{
		String testName=method.getName();
		testInfo=reports.createTest(testName);
	}

	@AfterMethod
	public void getResult(ITestResult result)
	{
		if(result.getStatus()== ITestResult.SUCCESS){
			testInfo.log(Status.PASS, "Test Method Named as : " + result.getName()+" is Passed");
		}
		else if(result.getStatus()== ITestResult.FAILURE){
			
			testInfo.log(Status.FAIL, "Test Method Named as : " + result.getName()+" is Failed");
			testInfo.log(Status.FAIL,"Test Case failed is " + result.getThrowable());
		}else if(result.getStatus()== ITestResult.SKIP){
			
			testInfo.log(Status.SKIP, "Test Method Named as : " + result.getName()+" is Skipped");
		}
	}
	
	@AfterTest
	public void endReprot()
	{
		reports.flush();
	}
	
}
