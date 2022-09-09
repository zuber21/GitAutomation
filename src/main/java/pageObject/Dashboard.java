package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Dashboard {
	
	public WebDriver driver;
	
	public Dashboard(WebDriver driver) {
		
		this.driver=driver;
	}
	
	private By organization=By.name("Organization");
	private By organizationname= By.xpath("//li[contains(text(),'FirozResellerPoxoAE (133400)')]");
	private By origin= By.name("Origin");
	private By originname=By.xpath("//li[contains(text(),'Mumbai, India (')]");
	private By destination=By.name("Destination");
	private By destinationname=By.xpath("//li[contains(text(),'hi, India (')]");
	private By traveler=By.name("Userids");
	private By oneway= By.xpath("//label[contains(text(),'One way')]");
	private By cabinclass= By.name("CabinClass");
	private By travelername= By.xpath("//li[contains(text(),'Test Firoz')]"); 
	private By leave = By.name("StartDate");
	private By leavedate= By.cssSelector(".default.active");
	private By findflights= By.cssSelector("a[class*='flightsOnly_all']");
	
	
	
	public WebElement getOrganization() {
		
	     return driver.findElement(organization);
	}
	
	public WebElement getOrganizationName() {
		
	     return driver.findElement(organizationname);
	}
	
	public WebElement getOrigin() {
		
	     return driver.findElement(origin);
	}
	
	public WebElement getOriginName() {
		
	     return driver.findElement(originname);
	}
	
	public WebElement getDestination() {
		
	     return driver.findElement(destination);
	}
	
	public WebElement getDestinationName() {
		
	     return driver.findElement(destinationname);
	}
	
	public WebElement getTraveler() {
		
	     return driver.findElement(traveler);
	}
	
	public WebElement getTravelerName() {
		
	     return driver.findElement(travelername);
	}
	
	public WebElement getOneWay() {
		
	     return driver.findElement(oneway);
	}
	
	public WebElement getCabinClass() {
		
	     return driver.findElement(cabinclass);
	}
	
	public WebElement getLeaveOn() {
		
	     return driver.findElement(leave);
	}
	
	public WebElement getLeaveDate() {
		
	     return driver.findElement(leavedate);
	}
	
	public WebElement getFindFlights() {
		
	     return driver.findElement(findflights);
	}
	
}
