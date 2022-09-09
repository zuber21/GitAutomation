package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HotelRoomPage {
	
public WebDriver driver;
	
	public HotelRoomPage(WebDriver driver) {
		
		this.driver=driver;
	}
	 
	
	private By room= By.xpath("//*[@id=\"hotelRooms\"]/div[2]/div[1]/div[2]/div[2]/div[1]/div[2]/div[7]/button");
	private By roome= By.xpath("//*[@id=\"hotelRooms\"]/div[2]/div[1]/div[2]/div[2]/div[1]/div[2]/div[6]/button");
	private By allroom= By.xpath("//span[contains(text(),'Rooms')]");
	 
		public  WebElement getRooms() {
				
		     return driver.findElement(room);
		}
			
		public  WebElement getallRooms() {
			
		     return driver.findElement(allroom);
		}
		public  WebElement getRoomsE() {
			
		     return driver.findElement(roome);
		}
}
