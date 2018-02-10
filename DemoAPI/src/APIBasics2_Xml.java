import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ModifyXMLDOM;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;


public class APIBasics2_Xml {
	
	
	@DataProvider(name = "Inputs")
	public static Object[][] credentials() 
	{
		return new Object[][]
		{ 
	        { "name", "Good Google"}, 
	        { "name", "Google Test"}
	    };    
	 
	 }
	
	@Test(dataProvider = "Inputs")
	public void postData(String Element,String Value) throws IOException
	{
		ModifyXMLDOM.G_placePostData(Element, Value);
		String xmlPostdata = GenerateStringFromResource(System.getProperty("user.dir") + "//src//files//Body_Payload_XML.xml");
		RestAssured.baseURI="https://maps.googleapis.com";
		
		Response resp = given().
		queryParam("key", "AIzaSyDTuIBpunv2dYjYTY1w5QRWyPVNPszBpU8").
		body(xmlPostdata).
		when().
		post("/maps/api/place/add/xml").
		then().assertThat().statusCode(200).and().contentType(ContentType.XML).
		extract().response();
		
		XmlPath xml = ReusableMethods.rawToXML(resp);		
		System.out.println(xml.getString("PlaceAddResponse.place_id"));
		//.and().body("status",equalTo("OK"))
		
	}
	
	public static String GenerateStringFromResource(String path) throws IOException
	{
		return new String(Files.readAllBytes(Paths.get(path)));
		
	}
}
