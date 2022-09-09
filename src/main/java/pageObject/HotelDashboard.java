package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HotelDashboard {

public WebDriver driver;
	
	public HotelDashboard(WebDriver driver) {
		
		this.driver=driver;
	}
	
	private By organization=By.name("Organization");
	private By organizationname= By.xpath("//li[contains(text(),'FirozResellerPoxoAE (133400)')]");
	private By hotel= By.cssSelector("label[for='hotelonly']");
	private By hotellocation= By.name("HotelLocation");
	private By hotellocationName= By.xpath("//li[contains(text(),'Delhi, India (6829)')]");
	private By leave = By.name("StartDate");
	private By leavedate= By.cssSelector(".default.active");
	private By end = By.name("EndDate");
	private By enddate= By.cssSelector(".default.active");
	private By traveler=By.name("Userids");
	private By travelername= By.xpath("//li[contains(text(),'Test Firoz')]"); 
	private By findhotel= By.xpath("//i[contains(text(),'Find hotels')]");
	private By bookguest= By.partialLinkText("book for guests");
	
	
	public WebElement getOrganization() {
		
	     return driver.findElement(organization);
	}
	
	public WebElement getOrganizationName() {
		
	     return driver.findElement(organizationname);
	}
	
	public WebElement getHotel() {
		
	     return driver.findElement(hotel);
	}
	  
	public WebElement getHotellocation() {
		
	     return driver.findElement(hotellocation);
	}
	
	public WebElement getHotellocationName() {
		
	     return driver.findElement(hotellocationName);
	}
	
	public WebElement getLeaveOn() {
		
	     return driver.findElement(leave);
	}
	
	public WebElement getLeaveDate() {
		
	     return driver.findElement(leavedate);
	}
	
	public WebElement getEndOn() {
		
	     return driver.findElement(end);
	}
	
	public WebElement getEndDate() {
		
	     return driver.findElement(enddate);
	}
	
	public WebElement getTraveler() {
		
	     return driver.findElement(traveler);
	}
	
	public WebElement getTravelerName() {
		
	     return driver.findElement(travelername);
	}
	
	public WebElement getFindHotel() {
		
	     return driver.findElement(findhotel);
	}
	public WebElement getGuest() {
		
	     return driver.findElement(bookguest);
	}
}
