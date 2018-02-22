package testScripts;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.Resources;
import reusableMethods.ModifyXMLDOM;
import reusableMethods.ReusableMethods;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Twitter_GET {
	
	String TweetCreated;
	Properties Repository = new Properties();
	ExtentReports reports;
	ExtentTest testInfo;
	ExtentHtmlReporter htmlReporter;
	@BeforeTest
	public void loadPropertiesFile() throws IOException
	{
		File f = new File(System.getProperty("user.dir") + "//src//config//env.properties");
		FileInputStream FI = new FileInputStream(f);
		Repository.load(FI);
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/ExtentReportAPI.html");
		htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir")+"//extent-config.xml"));
		reports = new ExtentReports();
		reports.setSystemInfo("Host Name" ,"API Automation_Extent Report");
		reports.setSystemInfo("Environment" ,"API Automation Testing");
		reports.setSystemInfo("User Name" ,"Test Tester");
		reports.attachReporter(htmlReporter);
		
	}
	
	@Test()
	public void Create_Tweet() 
	{
		RestAssured.baseURI=Repository.getProperty("T_HOST");
		Response res= given().auth().oauth(Repository.getProperty("Consumer_Key"), Repository.getProperty("Consumer_Secret"), Repository.getProperty("Token"), Repository.getProperty("Token_Secret")).
		queryParam("status", "REST API Automation").
		when().post(Resources.Create_Tweet()).then().assertThat().statusCode(200).and().
	    contentType(ContentType.JSON).extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(res);
		
		TweetCreated = js.get("id").toString();
		System.out.println("--------------Tweets After Adding------------------");
		//testInfo.log(Status.INFO, "Test Case name is Create_Tweet");
		
	}
	
	@Test(priority = 2,dependsOnMethods = { "Create_Tweet" })
	public void Get_Tweets() 
	{
		RestAssured.baseURI=Repository.getProperty("T_HOST");
		Response res= given().auth().oauth(Repository.getProperty("Consumer_Key"), Repository.getProperty("Consumer_Secret"), Repository.getProperty("Token"), Repository.getProperty("Token_Secret")).
		queryParam("count", "3").
		when().get(Resources.Get_Tweets()).then().log().ifError().assertThat().statusCode(200).and().
		contentType(ContentType.JSON).extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(res);
		int count =js.get("text.size");

		for(int i=0; i<count; i++)
		{
			System.out.println(js.getString("text["+i+"]"));
		}
		System.out.println("");
		String resString =  res.toString();
		testInfo.log(Status.INFO, resString);
	}
	
	@Test(priority = 3,dependsOnMethods = { "Get_Tweets" })
	public void Delete_Tweet() 
	{
		RestAssured.baseURI=Repository.getProperty("T_HOST");
		Response res= given().auth().oauth(Repository.getProperty("Consumer_Key"), Repository.getProperty("Consumer_Secret"), Repository.getProperty("Token"), Repository.getProperty("Token_Secret")).
		when().post(Resources.Delete_Tweet(TweetCreated)).then().log().ifError().assertThat().statusCode(200).and().
	    contentType(ContentType.JSON).log().all().extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(res);
		System.out.println("Deleted tweet is - " + js.get("text").toString());
		System.out.println("Truncated status is - " + js.get("truncated").toString());
		System.out.println("");
		System.out.println("--------------Tweets After Deleting------------------");
		Get_Tweets();
		testInfo.log(Status.INFO, "Test Case name is Delete_Tweet");
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
