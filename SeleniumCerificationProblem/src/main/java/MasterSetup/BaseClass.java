package MasterSetup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.xml.XmlSuite;

import API.EmailConfiguration;
import API.ExcelHandling;
import API.ReportConfigurations;
import PropertyFiles.PropertyFileHandling;
import jxl.read.biff.BiffException;
import webdriver.Webdriver;

public class BaseClass {
	
	public static WebDriver driver, driver1;
	public static String waitingTime="0", totalTime, browsername, url, project ;
	public int waitTime= 30;
	private long start,end,startSuite;
	public static FileReader reader;
	public static Properties prop;
	public static WebDriverWait wait;
	public static String testLogHTMLLocation,projectName,testLogHTMLFolderName,testLogScreenShotFolderName,testLogFileName, Environment,sheetConfiguration= "Configurations";
	public static File testlogFolder,clientLogHTMLFolder,screenShotFolder;
	static String domainsFilePath;String timeStamp;
	static JProgressBar testCaseBar; static JTextArea	taskoutput; static JFrame f; 
	
	
	private void createProgressBar() {
		//String projectName=context.getCurrentXmlTest().getParameter("project");
		f = new JFrame(Webdriver.projectName+" Progress Bar"); f.setSize(500, 200); 
		JPanel testCasePanel = new JPanel(); 
		
		testCasePanel.setVisible(true);
		testCasePanel.setBackground(Color.LIGHT_GRAY);
		
		testCaseBar = new JProgressBar();
		testCaseBar.setStringPainted(true);
		
		taskoutput=new JTextArea(8, 30);
		taskoutput.setLineWrap(true);
		
        Font font = new Font("Cambria", Font.BOLD,10);
        taskoutput.setFont(font);
        taskoutput.setForeground(Color.BLUE);
        taskoutput.setEditable(false);
		taskoutput.setMargin(new Insets(5,5,5,5));
		 
		f.add(testCasePanel,BorderLayout.PAGE_START);
		testCasePanel.add(new JScrollPane(taskoutput),BorderLayout.CENTER);
		f.add(testCasePanel);testCasePanel.add(testCaseBar);
		f.setVisible(true);
		
	}
	public static void update(String testCaseHeader) {
		String totalTestCaseCount=PropertyFileHandling.getDataFromPropertyFile("totalTestCasesCount");
		int progress=100/Integer.parseInt(totalTestCaseCount);
		testCaseBar.setValue(testCaseBar.getValue()+progress);
		taskoutput.append(testCaseHeader+"\n");
		
	}	
	@BeforeSuite
	public void beforeSuite(ITestContext context)
	{		
		try{
			
			
		startSuite = Reporter.getCurrentTestResult().getStartMillis();
		System.out.println("Test Suite Started....");
		projectName = context.getCurrentXmlTest().getParameter("project");
		System.out.println("Class : "+this.getClass().getName());
		System.out.println("Current Project : "+projectName);
		
		timeStamp = new SimpleDateFormat("_yy-MMM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		  

		createProgressBar();
		
        url=ExcelHandling.fnReadValExcel(Temp.ExcelConfigurations.DomainList_path, sheetConfiguration, 1, 1);
        browsername=ExcelHandling.fnReadValExcel(Temp.ExcelConfigurations.DomainList_path, sheetConfiguration, 1, 2);
        project=ExcelHandling.fnReadValExcel(Temp.ExcelConfigurations.DomainList_path, sheetConfiguration, 1, 3);
        testLogHTMLLocation=ExcelHandling.fnReadValExcel(Temp.ExcelConfigurations.DomainList_path, sheetConfiguration, 1, 4);
        testLogHTMLFolderName=ExcelHandling.fnReadValExcel(Temp.ExcelConfigurations.DomainList_path, sheetConfiguration, 1, 5);
        testLogScreenShotFolderName=ExcelHandling.fnReadValExcel(Temp.ExcelConfigurations.DomainList_path, sheetConfiguration, 1, 6);
        Environment=ExcelHandling.fnReadValExcel(Temp.ExcelConfigurations.DomainList_path, "Configurations", 1, 7);
  
        System.out.println("Browser Name: "+browsername);
        
  		timeStamp = new SimpleDateFormat("_yy-MMM-dd-HH-mm").format(Calendar.getInstance().getTime());
  
  		testLogFileName=project+timeStamp;
        testLogHTMLLocation = testLogHTMLLocation.concat("//").concat(testLogFileName);
        
        testlogFolder = new File(testLogHTMLLocation);
        testlogFolder.mkdir();

        clientLogHTMLFolder = new File(testlogFolder.getAbsolutePath().concat("//").concat(testLogHTMLFolderName));
        clientLogHTMLFolder.mkdir();
                          
        screenShotFolder = new File(clientLogHTMLFolder.getAbsolutePath().concat("//").concat(testLogScreenShotFolderName));
        screenShotFolder.mkdir();

	    System.out.println("WebDriver done");
		}catch(Exception e){
			e.printStackTrace();
			Reporter.log("Log Files Error : "+e);
			
		}
	}
	
	@AfterSuite
	public void afterSuite() throws BiffException, IOException, InterruptedException
	{
		long end = Reporter.getCurrentTestResult().getEndMillis();
		totalTime = DurationFormatUtils.formatDuration((end-startSuite), "HH:mm:ss,SSS");
		System.out.println("Total Suite Execution time(HMS): " + totalTime);
		
		
		String rpt=Webdriver.testlogFolder.getAbsolutePath().concat("\\").concat(Webdriver.testLogHTMLFolderName)+"\\"+Webdriver.projectName+".html";
		driver.get(rpt);
		EmailConfiguration.sendReport();
		
		XmlSuite suite = new XmlSuite();
		suite.setName("NextGen Suite");
		ReportConfigurations.endExtentReport();
		System.out.println("Test Suite Ended....");
	}
	
	@BeforeClass
	public void setupSelenium(ITestContext context) {
		System.out.println("Before Class Executed");
		System.out.println("Test Name: "+context.getName());	
	}

	@BeforeMethod
	public void beforeTest(ITestContext context){
		System.out.println("Before Test Executed");
		start = Reporter.getCurrentTestResult().getStartMillis();
		System.out.println("Class Name: "+context.getName());
	}
	
	@AfterMethod
	public void afterTest(ITestContext context){
		ReportConfigurations.endExtentReport();
		System.out.println("After Test Executed");
		end = Reporter.getCurrentTestResult().getEndMillis();
		String execTime =DurationFormatUtils.formatDurationHMS((end-start));
		System.out.println("Total Test Execution time - millisec: " + execTime);
		System.out.println("Total Test Execution time - HMS: " + DurationFormatUtils.formatDurationHMS((end-start)));
	}
	

}
