package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class base {
	public WebDriver driver;
	
	public Properties prop;

	@SuppressWarnings("deprecation")
	public WebDriver initializeDriver() throws IOException
	{

		prop= new Properties();
		FileInputStream fis=new FileInputStream("C:\\Users\\Zuber Chowki\\eclipse-workspace\\MB\\src\\main\\java\\resources\\data.properties");

		prop.load(fis);
		String browserName=prop.getProperty("browser");
		System.out.println(browserName);


	if(browserName.equalsIgnoreCase("chrome"))
	{
		 System.setProperty("webdriver.chrome.driver", "C:\\Users\\Zuber Chowki\\eclipse-workspace\\MB\\src\\main\\java\\resources\\chromedriver.exe");
		  driver= new ChromeDriver();//Chromedriver was not detecting
			//execute in chrome driver

	}
	else if (browserName.equalsIgnoreCase("firefox"))
	{
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Zuber Chowki\\eclipse-workspace\\MB\\src\\main\\java\\resources\\geckodriver.exe");
		 driver= new FirefoxDriver(); 
		//firefox code
	}
	else if (browserName.equalsIgnoreCase("Edge"))
	{
		System.setProperty("webdriver.edge.driver", "C:\\Users\\Zuber Chowki\\eclipse-workspace\\MB\\src\\main\\java\\resources\\msedgedriver.exe");
	 driver= new EdgeDriver();
	//Edge code
	}


	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	driver.manage().window().maximize();
	return driver;
	}

	public String getScreenShotPath(String testCaseName,WebDriver driver) throws IOException
	{
		TakesScreenshot ts=(TakesScreenshot) driver;
		File source =ts.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png";
		FileUtils.copyFile(source,new File(destinationFile));
		return destinationFile;


	}

}
