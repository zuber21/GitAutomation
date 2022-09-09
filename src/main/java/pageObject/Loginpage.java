package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Loginpage {
	
	public WebDriver driver;
	
	private By username= By.id("email");
	private By next= By.className("MuiButton-label");
	private By password= By.cssSelector("input#password.MuiInputBase-input.MuiInput-input.MuiInputBase-inputAdornedEnd");
	private By login= By.className("MuiButton-label");
	
	public Loginpage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		
		this.driver=driver;
	}

	public WebElement getUserName() {
	
     return driver.findElement(username);
}
	
	public WebElement getNext() {
		
	     return driver.findElement(next);
	}
	public WebElement getPassword() {
		
	     return driver.findElement(password);
	}
	
	public WebElement getLogin() {
		
	     return driver.findElement(login);
	}
}