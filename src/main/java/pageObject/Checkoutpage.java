package pageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Checkoutpage {
public WebDriver driver;
	
	public Checkoutpage(WebDriver driver) {
		
		this.driver=driver;
	}
	
	private By title = By.xpath("//select[@data-value-rule='required']");
	private By name= By.xpath("//input[@placeholdevalue='First name']");
	private By lastname= By.xpath("//input[@placeholdevalue='Last name']");
	private By contactno = By.id("txtContactNumber");
	private By continuebttn=By.id("btnContinue");
	private By specialrq = By.id("txtSpecialRequest");
	
	
	
	 
	public  WebElement getContactNo() {
			
	     return driver.findElement(contactno);
	}
	public  WebElement getTitle() {
		
	     return driver.findElement(title);
	}
	
	public  WebElement getName() {
		
	     return driver.findElement(name);
	}
	
	public  WebElement getLastname() {
		
	     return driver.findElement(lastname);
	}
	
	public  WebElement getContinuebttn() {
		
	     return driver.findElement(continuebttn);
	}
    
	public  WebElement getSpecialrequest() {
		
	     return driver.findElement(specialrq);
	}
}
