package MB;

import java.io.IOException;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import pageObject.Checkoutpage;
import pageObject.Dashboard;
import pageObject.Loginpage;
import pageObject.POXOPaymentpage;
import pageObject.Searchresultpage;
import resources.base;
import java.util.logging.Logger;

public class MBFlightbooking extends base {
	public WebDriver driver;

	private static Logger log = Logger.getLogger(base.class.getName());

	@Test(dataProvider = "getData")
	public void loginHomePage(String username, String password) throws IOException, InterruptedException {
		driver = initializeDriver();

		log.info("Driver is initialized");

		driver.get("https://stagingapp.musafir.com");
		log.info("Navigated to login page");

		Loginpage l = new Loginpage(driver);
		l.getUserName().click();
		l.getUserName().sendKeys(username);
		l.getNext().click();
		l.getPassword().click();
		l.getPassword().sendKeys(password);
		l.getLogin().click();

		log.info("Logged in into MB");

		Dashboard d = new Dashboard(driver);
		d.getOneWay().click();

		Assert.assertTrue(d.getOneWay().isEnabled());

		d.getOrganization().click();
		d.getOrganization().sendKeys("firoz");
		d.getOrganizationName().click();

		d.getOrigin().click();
		d.getOrigin().sendKeys("BOM");
		d.getOriginName().click();

		d.getDestination().click();
		d.getDestination().sendKeys("DEL");
		d.getDestinationName().click();

		d.getLeaveOn().click();
		d.getLeaveDate().click();

		d.getTraveler().click();
		d.getTraveler().sendKeys("Test");
		d.getTravelerName().click();

		WebElement staticdropdown = d.getCabinClass();
		Select dropdown = new Select(staticdropdown);
		dropdown.selectByIndex(1);
		System.out.println(dropdown.getFirstSelectedOption().getText());

		d.getFindFlights().click();

		log.info("Searching for a flight");

		Searchresultpage srp = new Searchresultpage(driver);

		try {

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"flightScreenRight\"]/div/div[3]/div/div/div/div[4]/button")));
		}

		catch (Exception e) {
			driver.navigate().refresh();
		}

		Assert.assertEquals(srp.getMessage().getText(), "The custom message is shown here UAE");

		srp.getBookFlight().click();
		log.info("Book a Normal Flight");

		try {
			srp.getBookFFFlight().click();
			log.info("Able to find Fare Family Flight");
		}

		catch (Exception e) {
			log.info("Unable to find Fare Family Flight");
		}

		Checkoutpage c = new Checkoutpage(driver);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scroll(0, 500)");

		c.getContactNo().click();
		c.getContactNo().sendKeys("1234567890");

		c.getContinuebttn().click();

		log.info("Pressed on Continue button");

		try {
			WebDriverWait wait2 = new WebDriverWait(driver, 07);

			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"notification-440\"]/div/a")));

			// If system found lower price flight
			driver.findElement(By.cssSelector("a.button.confirmButton.blue.inlineButton.buttonBound.customButton"))
					.click();
		}

		catch (Exception e) {
			log.info("Unable to find lower price flight");
		}

		POXOPaymentpage p = new POXOPaymentpage(driver);

		driver.switchTo().frame(driver.findElement(By.className("purchasePanel")));

		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctrlPurchase_txtPurchaseReference")));

		p.getOrderNo().click();
		p.getOrderNo().sendKeys("qwerty");

		// p.getChoosefile().click();

		WebElement element = driver.findElement(By.id("ctrlPurchase_fuPaymentAttachment"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);

		Thread.sleep(2000);

		Runtime.getRuntime().exec("C:\\\\Users\\\\Zuber Chowki\\\\Documents\\\\fileupload.exe");

		Thread.sleep(2000);

		p.getConfirm().click();

		log.info("Made the POXO payment");

		driver.switchTo().defaultContent();

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("panoramaPanel")));

		try {
			driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@DataProvider
	public Object[][] getData() {

		// Row stand for how many different data types test should run
		// Coulmn stand for how many values
		Object[][] data = new Object[1][2];

		data[0][0] = "admin@musafir.com";
		data[0][1] = "MBusiness@21";

	
		  /*data[1][0] = "operationexecutive@mailinator.com"; 
		  data[1][1] = "Musafir123456";
		  
		  data[2][0] = "aeoperationmanager@yopmail.com";
		  data[2][1] = "Musafir@123456";*/
		 

		return data;

	}
}
