package API;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import MasterSetup.BaseClass;
import PropertyFiles.PropertyFileHandling;
import webdriver.Webdriver;

public class ReportConfigurations {
	public static int failStep;
	public static String reportFileLocation;
	public static String testFailed;
	
	static ExtentReports extent;
	static ExtentTest test,test1;
	static ExtentTest child;
	
	public static Logger log;
	
	public static String takeScreenshot(WebDriver driver) {
		String screenShotName = new SimpleDateFormat("_yy-MMM-dd-HH-mm-ss").format(Calendar.getInstance().getTime());
				
		File screenShotFolder = Webdriver.getScreenShotFolder();
		if (driver instanceof TakesScreenshot) {
			File tempFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try{
				FileUtils.copyFile(tempFile, new File(screenShotFolder+"/" + screenShotName + ".jpg"));
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		return screenShotFolder + "/" + screenShotName + ".jpg";
	}
	
	public static void endExtentReport(){
		try{
			extent.endTest(test);
			extent.flush();
	     }catch(Exception e){
	    	System.out.println("File not completed properly!");
	    	e.printStackTrace();
		}		
	}

	public static void appendClientHeader(String reportName){
		
		File reportFolderName = Webdriver.getClientLogHTMLFolder();
		
		try{
	        reportFileLocation = reportFolderName.getName();
	        String clientHTMLFile = reportFolderName + "//"+reportName+".html";
	        System.out.println(clientHTMLFile);
	        
	        String ExtentFilePath = PropertyFileHandling.getDataFromPropertyFile("ExtentFile");
	        
	        File file = new File(ExtentFilePath);
	        String configfile = file.getAbsolutePath();
	        
	       // extent = new ExtentReports(clientHTMLFile,false);
	        extent = new ExtentReports(clientHTMLFile,false);
	        extent.addSystemInfo("Component", Webdriver.projectName)
	        .addSystemInfo("Feature", "Cerification Project")
	        .addSystemInfo("Automation Status", "Automation Done")
            .addSystemInfo("Environment", BaseClass.url)
            .addSystemInfo("Status", "Running")
            .addSystemInfo("Script Name/Project name", Webdriver.projectName)//Include execution status
            .addSystemInfo("Script Path", System.getProperty("user.dir"));
	        extent.loadConfig(new File(configfile));
	        
	        // Create reference variable �log� referencing getLogger method of Logger class, it is used to store logs in selenium.txt
	        log = Logger.getLogger("devpinoyLogger");

	     // Call debug method with the help of referencing variable log and log the information in test.logs file
	     
	        
	     }catch(Exception e){
	    	System.out.println("Report file creation error : " + e.getMessage());
		}
	}
	
	public static void appendTestCaseHeader(String testCaseHeader){
		Webdriver.update(testCaseHeader);
		test = extent.startTest(testCaseHeader);	
	}
	public static void appendTestCaseHeader(String testCaseHeader,String Category){
		test = extent.startTest(testCaseHeader).assignCategory(Category);	
	}
	public static void appendTestCaseSubTitle(String testCaseHeaderSubTitle){
		test1 = test;
		child = extent.startTest(testCaseHeaderSubTitle);
		test = child;
	}
	
	public static void Close_Subtitle(){
		test = test1;
		test.appendChild(child);
		//endExtentReport();
	}
	
	public static void close_TestcaseHeader() {
		extent.endTest(test);
		//endExtentReport();
	}
	public static void appendPass(String stepDescription){
		//failStep++;
		test.log(LogStatus.PASS, stepDescription);
		log.info(stepDescription);//.debug(stepDescription);
	}
	
	
	public static int logScreenshot(WebDriver driver, String strDescription) {
	try {
		appendPassWithScreenshot(strDescription, ReportConfigurations.takeScreenshot(driver));
		return 1;
		}catch(Exception e)
		{
		return 0;
	}
	}
	
	public static void appendPassWithScreenshot(String stepDescription1, String screenShot){
		test.log(LogStatus.PASS, stepDescription1+" ScreenShot - "+test.addScreenCapture(screenShot));
	}
	
	public static void logFailure(WebDriver driver, String stepDescription, Exception e){
		ReportConfigurations.appendFail(stepDescription + "\n Landed Page Title is : " + driver.getTitle() ,takeScreenshot(driver));
		log.warn("ERROR :"+stepDescription,e);
		
	}
	
	public static void logFailure(WebDriver driver, String stepDescription){
		ReportConfigurations.appendFail(stepDescription + "\n Landed Page Title is : " + driver.getTitle() ,takeScreenshot(driver));
		log.warn("ERROR :"+stepDescription);
	}
	
	public static void appendFail(String stepDescription1, String screenShot){
		test.log(LogStatus.FAIL, stepDescription1+" ScreenShot - "+test.addScreenCapture(screenShot));
		
	}
	
	public static void appendInfo(String stepDescription1){
		test.log(LogStatus.INFO, "<b>"+stepDescription1+"<b>");
		log.debug("Info"+stepDescription1);
	}
}
