package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class POXOPaymentpage {
public WebDriver driver;
	
	public POXOPaymentpage(WebDriver driver) {
		
		this.driver=driver;
	}	
	
	private By orderno = By.id("ctrlPurchase_txtPurchaseReference");
	private By choosefile=By.id("ctrlPurchase_fuPaymentAttachment");
	private By confirm= By.name("btnConfirmPurchase");
	
	
	 
	public  WebElement getOrderNo() {
			
	     return driver.findElement(orderno);
	}
	
	public  WebElement getChoosefile() {
		
	     return driver.findElement(choosefile);
	}
	
	public  WebElement getConfirm() {
		
	     return driver.findElement(confirm);
	}
	

}
