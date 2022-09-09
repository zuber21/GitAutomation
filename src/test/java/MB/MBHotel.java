package MB;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObject.Checkoutpage;
import pageObject.HotelDashboard;
import pageObject.HotelListingPage;
import pageObject.HotelRoomPage;
import pageObject.Loginpage;
import pageObject.POXOPaymentpage;
import resources.base;


public class MBHotel extends base {
	public WebDriver driver;

	private static Logger log = Logger.getLogger(base.class.getName());

	@Test(dataProvider = "getData")
	public void HotelHomePage(String username, String password) throws IOException, InterruptedException {

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

		HotelDashboard h = new HotelDashboard(driver);
		h.getHotel().click();
		h.getOrganization().click();
		h.getOrganization().sendKeys("firoz");
		h.getOrganizationName().click();

		h.getHotellocation().click();
		h.getHotellocation().sendKeys("Delhi");
		h.getHotellocationName().click();

		h.getLeaveOn().click();
		h.getLeaveDate().click();
		Thread.sleep(1000);

		h.getEndDate().click();
		Thread.sleep(1000);

		h.getGuest().click();

		log.info("From dashboard, booking for Corporate guest");

		// h.getFindHotel().click();

		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.xpath("//i[contains(text(),'Find hotels')]"))).click().perform();

		HotelListingPage b1 = new HotelListingPage(driver);

		try {

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("See rooms")));
		}

		catch (Exception e) {
			driver.navigate().refresh();
		}

		b1.getSeeRooms().click();

		Set<String> windows = driver.getWindowHandles(); // [parentid,childid,subchildId]

		Iterator<String> it = windows.iterator();

		@SuppressWarnings("unused")
		String parentId = it.next();

		String childId = it.next();

		driver.switchTo().window(childId);

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Rooms')]")));

		HotelRoomPage o = new HotelRoomPage(driver);

		o.getallRooms().click();

		try {
			WebDriverWait wait1 = new WebDriverWait(driver, 10);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"hotelRooms\"]/div[2]/div[1]/div[2]/div[2]/div[1]/div[2]/div[7]/button")));
		} catch (Exception e) {
			driver.navigate().refresh();
			o.getallRooms().click();

		}
		Thread.sleep(2000);

		try {
			o.getRooms().click();
			log.info("Book for  Travelguru rooms");
		} catch (Exception e) {
			o.getRoomsE().click();
			log.info("Book for  EAN rooms");
		}

		Checkoutpage c = new Checkoutpage(driver);
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"resellerAmount\"]/a")));

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

		WebElement staticdropdown = c.getTitle();
		Select dropdown = new Select(staticdropdown);
		dropdown.selectByIndex(3);
		System.out.println(dropdown.getFirstSelectedOption().getText());

		c.getName().click();
		c.getName().sendKeys("Afroz");

		c.getLastname().click();
		c.getLastname().sendKeys("Chowki");

		c.getContactNo().click();
		c.getContactNo().sendKeys("1234567890");

		c.getSpecialrequest().click();
		c.getSpecialrequest().sendKeys("This is a special request");

		c.getContinuebttn().click();

		POXOPaymentpage p = new POXOPaymentpage(driver);

		driver.switchTo().frame(driver.findElement(By.className("purchasePanel")));

		WebDriverWait wait2 = new WebDriverWait(driver, 30);
		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctrlPurchase_txtPurchaseReference")));

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

		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("panoramaPanel")));

		try {
			driver.quit();
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

		/*
		 * data[1][0]="operationexecutive@mailinator.com"; data[1][1]="Musafir123456";
		 * 
		 * data[2][0]="aeoperationmanager@yopmail.com"; data[2][1]="Musafir@123456";
		 */

		return data;

	}
}
