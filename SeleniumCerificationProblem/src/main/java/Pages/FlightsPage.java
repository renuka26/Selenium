package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import API.Implementation;
import MasterSetup.BaseClass;

public class FlightsPage extends BaseClass{

	

	
	@FindBy(xpath="//*[@value='roundtrip']")
	public static WebElement roundtrip_radioBtn;
	
	@FindBy(xpath="//*[@name='findFlights']")
	public static WebElement continue_btn;	
	
	@FindBy(xpath="//*[@name='reserveFlights']")
	public static WebElement reserveFlights_btn;	
	
	
	
	@FindBy(xpath="//*[@name='passFirst0']")
	public static WebElement firstName_input;
	
	@FindBy(xpath="//*[@name='passLast0']")
	public static WebElement lastName_input;
	
	@FindBy(xpath="//*[@name='creditCard']")
	public static WebElement creditCardType_dropdown;
	
	@FindBy(xpath="//*[@name='creditnumber']")
	public static WebElement creditCardNumber;
	
	@FindBy(xpath="//select[@name='cc_exp_dt_mn']")
	public static WebElement exp_Month;
	
	@FindBy(xpath="//*[@name='cc_exp_dt_yr']")
	public static WebElement exp_Year;
	
	
	@FindBy(xpath="//*[@name='buyFlights']")
	public static WebElement securePurchase_Btn;
	
	
	@FindBy(xpath="//*[contains(text(),'itinerary has been booked!')]")
	public static WebElement confirmation_text;
	
	@FindBy(xpath="//img[@src='/images/forms/Logout.gif']")
	public static WebElement logout_btn;
	
	
	
	
	public static boolean fnBookReturnTicket(String firstName,String lastName,String creditCardType,String creditCardNo,String expMonth,String expYear) throws InterruptedException{
		
		Implementation.clickField(driver, roundtrip_radioBtn, "Round Trip");
		Implementation.clickField(driver, continue_btn, "Find Flights Continue Button");Thread.sleep(3000);
		Implementation.clickField(driver, reserveFlights_btn, "Select Flights Continue Button");Thread.sleep(3000);
		
		Implementation.enterTextFeild(driver, firstName_input, "First Name", firstName);
		Implementation.enterTextFeild(driver, lastName_input, "Last Name", lastName);
		
		Implementation.selectField(driver, creditCardType_dropdown, "Card Type", "text", creditCardType);
		Implementation.enterTextFeild(driver, creditCardNumber, "Card Number", creditCardNo);
		Implementation.selectField(driver, exp_Month, "Exp Month", "text", expMonth);
		Implementation.selectField(driver, exp_Year, "Exp Year", "text", expYear);
		
		Implementation.clickField(driver, securePurchase_Btn, "Secure Purchase Button");Thread.sleep(3000);
		
		return confirmation_text.isDisplayed();
		
		
	}
	
	public static void fnLogout()
	{
		Implementation.clickField(driver, logout_btn, "Logout");
	}
}
