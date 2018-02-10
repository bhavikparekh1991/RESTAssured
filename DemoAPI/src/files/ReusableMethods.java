package files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ReusableMethods {
	static Properties Repository = new Properties();
	public static XmlPath rawToXML(Response r)
	{
		String respon=r.asString();
		XmlPath x=new XmlPath(respon);
		return x;
	}
	
	public static JsonPath rawToJson(Response r)
	{ 
		String respon=r.asString();
		JsonPath x=new JsonPath(respon);
		return x;
	}
	
	public static Properties Init_PropertyFile() throws IOException
	{ File f = new File(System.getProperty("user.dir") + "//src//files//Env.properties");
	FileInputStream FI = new FileInputStream(f);
	Repository.load(FI);
	return Repository;
	}
}
