package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Searchresultpage {
	
	public WebDriver driver;
	
	public Searchresultpage(WebDriver driver) {
		
		this.driver=driver;
	}
	
	 private By bookflight = By.xpath("//*[@id=\"flightScreenRight\"]/div/div[3]/div/div/div/div[4]/button");
	 private By bookffflight = By.xpath("//*[@id=\"flightScreenRight\"]/div/div[3]/div/div[2]/div[2]/div[3]/div[2]/button");		 
	 private By message = By.xpath("//div[@class='MuiAlert-message']/div/div");
	 
	 public  WebElement getBookFlight() {
			
	     return driver.findElement(bookflight);
	}

	 
	public  WebElement getBookFFFlight() {
			
	     return driver.findElement(bookffflight);
	}
    
	public  WebElement getMessage() {
		
	     return driver.findElement(message);
	}

}
