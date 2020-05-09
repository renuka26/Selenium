package webdriver;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.swing.text.Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import API.EmailConfiguration;
import API.ExcelHandling;
import API.Implementation;
import API.ReportConfigurations;
import MasterSetup.BaseClass;
import MasterSetup.Temp;
import Pages.DashboardPage;
import Pages.FlightsPage;
import PropertyFiles.PropertyFileHandling;


public class Webdriver extends BaseClass
{
	
	@Test
    public void creatingWebDriverInstance() {
		
	try {
		String browserMode=ExcelHandling.fnReadValExcel(Temp.ExcelConfigurations.DomainList_path, "Configurations", 1, 8);
		
		
		if(browserMode.equalsIgnoreCase("Headless"))
			Webdriver.driver = createHeadlessWebDriverObject(browsername);
		else
			Webdriver.driver = Webdriver.createWebDriverObject(browsername);
		
	
		ReportConfigurations.appendClientHeader(projectName);Temp.iTcCnt = 1;
		
		ReportConfigurations.appendTestCaseHeader("TC : "+Temp.iTcCnt++ +" Suite Configuration");
		driver.manage().deleteAllCookies();
		wait = new WebDriverWait(driver,30);
		System.out.println("Deleted Cookies");
		
		Implementation.launchClientURL(driver, url, browsername);
		
		PageFactory.initElements(driver, DashboardPage.class);
		PageFactory.initElements(driver, FlightsPage.class);
		
		ReportConfigurations.appendPass("Project Name : "+projectName);
		ReportConfigurations.appendPass("Environment : "+url);
		ReportConfigurations.appendPass("Browser Name : "+browsername);
			
		ReportConfigurations.close_TestcaseHeader();
		
		}catch(Exception e){
			
			System.out.println("Problem in driver creation");
			ReportConfigurations.logFailure(driver,"Exception" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	public static WebDriver createHeadlessWebDriverObject(String browser) {
		WebDriver driver = null;
		try {
			System.out.println("Browser :" + browser);

			String driverPath = PropertyFileHandling.getDataFromPropertyFile(browser);

			if (browser.equalsIgnoreCase("chrome")) {

				if (System.getProperty("os.name").startsWith("Windows")) {
					driverPath = PropertyFileHandling.getDataFromPropertyFile(browser);
					System.setProperty("webdriver.chrome.driver", driverPath);
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200",
							"--ignore-certificate-errors");
					driver = new ChromeDriver(options);

				} else {
					driverPath = PropertyFileHandling.getDataFromPropertyFile("ChromeOSX");
					System.setProperty("webdriver.chrome.driver", driverPath);

				}
			}

			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(Long.parseLong(waitingTime), TimeUnit.SECONDS);
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver, 50);
			System.out.println("Driver Object is ready");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return driver;
	}
	

	public static WebDriver createWebDriverObject(String browser) {
		WebDriver localdriver = null;
		try {
			System.out.println("Browser :" + browser);

			String driverPath = PropertyFileHandling.getDataFromPropertyFile(browser);

			if (browser.contains("firefox")) {
			} else if (browser.equalsIgnoreCase("chrome")) {

				if (System.getProperty("os.name").startsWith("Windows")) {
					driverPath = PropertyFileHandling.getDataFromPropertyFile(browser);
					System.setProperty("webdriver.chrome.driver", driverPath);
					System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
				} else {
					driverPath = PropertyFileHandling.getDataFromPropertyFile("ChromeOSX");
					System.setProperty("webdriver.chrome.driver", driverPath);

				}
				localdriver = new ChromeDriver();
			}
			localdriver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
			localdriver.manage().timeouts().implicitlyWait(Long.parseLong(waitingTime), TimeUnit.SECONDS);
			localdriver.manage().window().maximize();
			System.out.println("Driver Object is ready");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return localdriver;
	}

	public static File getScreenShotFolder(){
		return screenShotFolder;
	}	
	
	public static File getClientLogHTMLFolder(){
		return clientLogHTMLFolder;
	}
	
}
