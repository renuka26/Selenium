package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import API.Implementation;
import MasterSetup.BaseClass;

public class DashboardPage extends BaseClass{

	

	//Page Factory
	@FindBy(xpath="//input[@name='userName']")
	public static WebElement username_input;
	
	@FindBy(xpath="//input[@name='password']")
	public static WebElement password_input;
	
	@FindBy(xpath="//input[@name='login']")
	public static WebElement signIn_Button;
	
	@FindBy(linkText="Flights")
	public static WebElement flights_Button;
	
	@FindBy(xpath="//*[text()='SIGN-OFF']")
	public static WebElement signOff_Button;
	
	@FindBy(xpath="//*[@value='roundtrip']")
	public static WebElement roundtrip;
	
	
	public static boolean fnLogin(String username,String Password){
		boolean status = false;
		
		try{
			
			Implementation.enterTextFeild(driver, username_input, "UserName", username);
			Implementation.enterTextFeild(driver, password_input, "Password", Password);
			Implementation.clickField(driver, signIn_Button, "Sign In Button");
			
			 status=signOff_Button.isDisplayed();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
		
		
	}
	
	public static void fnNavigateToFlightsPage() throws InterruptedException{
		
		Implementation.clickField(driver, flights_Button, "Flights Button");
		Thread.sleep(3000);
	}
}
