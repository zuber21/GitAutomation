package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HotelListingPage {
	
public WebDriver driver;
	
	public HotelListingPage(WebDriver driver) {
		
		this.driver=driver;
	}
	 
	
	private By seeroom= By.linkText("See rooms");
	
	 
		public  WebElement getSeeRooms() {
				
		     return driver.findElement(seeroom);
		}
			
}
