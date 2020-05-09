package API;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.google.common.base.Function;

import MasterSetup.BaseClass;
import PropertyFiles.PropertyFileHandling;
import dk.brics.automaton.StringUnionOperations;
import jxl.read.biff.BiffException;

public class Implementation extends BaseClass{
	

	//To verify object present
	public static boolean isobjPresent(WebElement obj) {
		try {
			if (obj.isDisplayed() || obj.isEnabled() || obj.isSelected())
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Waiting 3");
			return false;
		}
		return false;
	}
	

	//To get current time
	public static String getCurrentTime(){
		return new SimpleDateFormat("_MMddyyyy_HHmmss").format(Calendar.getInstance().getTime());
	}
	
	//To verify web element is displayed
	public static boolean isobjDisplayed(WebElement obj) {
		try {
			if (obj.isDisplayed())
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}


	//To highlight element
	public static void highLightElement(WebDriver driver, WebElement element)
	{
		try {
			
			JavascriptExecutor js=(JavascriptExecutor)driver; 			 
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
			Thread.sleep(40);
			js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
	 
	}

	//To move to focus to element position to take screenshot
	public static void moveToElementPosition(WebDriver driver, WebElement obj) {
		try {
		JavascriptExecutor js = (JavascriptExecutor) driver;
	    int yPosition = obj.getLocation().getY();
	    js.executeScript("window.scroll (0, " + yPosition + "-300) "); 
	    Thread.sleep(400);
		}
	    catch (Exception e) {
			e.printStackTrace();
		}
	}


	//To click element
	public static boolean clickField(WebDriver driver, WebElement obj, String ObjectName) {
		boolean clickElement = false;
		try {
			
			if (isobjPresent(obj)) {
				
				if(!obj.toString().contains("style"))
				{
				moveToElementPosition(driver,obj);
				highLightElement(driver, obj);
				}
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", obj);
				ReportConfigurations.appendPass("Object Name: " + ObjectName + ",  Action : Click");
				System.out.println(ObjectName+" clicked");
				clickElement = true;
				} else {
				ReportConfigurations.logFailure(driver,
						"Object Name: " + ObjectName + ",  Action : Click");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ReportConfigurations.logFailure(driver,
					"Click the element - " + ObjectName + " Problem in Clicking on - " + ObjectName,e);
		}
		return clickElement;
	}

	//To enter text in a field
	public static boolean enterTextFeild(WebDriver driver, WebElement Obj, String ObjectName, String Value) {
		boolean sendKeys = false;
		try {
			if (isobjPresent(Obj)) {
				
				moveToElementPosition(driver,Obj);
				highLightElement(driver, Obj);
				Obj.sendKeys(Value);
				sendKeys = true;
				System.out.println(Value+" entered in "+ObjectName);
				ReportConfigurations.appendPass("Object Name: " + ObjectName + ", Action: Enter Text,  Value: "+Value);

			} else {
				ReportConfigurations.logFailure(driver, "Enter the text '" + Value + "' in the " + ObjectName + " Textbox "
						+ " , Element is not found - " + ObjectName + " Textbox");
			}

		} catch (Exception e) {

			e.printStackTrace();
			ReportConfigurations.logFailure(driver, "Enter the text '" + Value + "' in the " + ObjectName + " Textbox"
					+ " , Problem in entering the text '" + Value + "' in the " + ObjectName + " Textbox",e);
		}

		return sendKeys;
	}
	
	
	//To select option in the dropdown
	public static boolean selectField(WebDriver driver, WebElement obj, String ObjectName, String option, String value) {
		boolean Select = false;
		try {

			if (isobjPresent(obj)) {

				if(!obj.toString().contains("style"))
				{
				moveToElementPosition(driver,obj);
				highLightElement(driver, obj);
				}
				Select dd = new Select(obj);

				if (option.equalsIgnoreCase("text")) {
					dd.selectByVisibleText(value);
				} else if (option.equalsIgnoreCase("index")) {
					int index = Integer.parseInt(value);
					dd.selectByIndex(index);
				} else {
					dd.selectByValue(value);
				}
				Select = true;
				ReportConfigurations.appendPass("Object Name: " + ObjectName + ", Action: Select dropdown  Value: "+value);
				System.out.println(value+" selected in "+ObjectName);
				return Select;

			} else {
				ReportConfigurations.logFailure(driver,"Object Name: " + ObjectName + ", Action: Select dropdown  Value: "+value);
				return Select;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			ReportConfigurations.logFailure(driver,"Object Name: " + ObjectName + ", Action: Select dropdown  Value: "+value,exception);
			return Select;
		}
	}
	
	public static void launchClientURL(WebDriver driver, String clientName, String browsername) {
		String url; 

		url = "http://" + clientName;
		try {
			driver.get(url);
			System.out.println("URL found : " + driver.getTitle());
		} catch (Exception e) {
			e.printStackTrace();
			ReportConfigurations.logFailure(driver, url + " URL is not available", e);
			Assert.fail(driver.getTitle());
		}
	}
	
}
