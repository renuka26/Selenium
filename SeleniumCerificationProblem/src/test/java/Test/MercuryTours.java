package Test;

import org.testng.Assert;
import org.testng.annotations.Test;

import API.ReportConfigurations;
import Entities.UserEntity;
import MasterSetup.Temp;
import Pages.DashboardPage;
import Pages.FlightsPage;

public class MercuryTours {

	
	@Test(priority=1,enabled=true)
	public static void TestCase1() 
	{
		try{
			
			ReportConfigurations.appendTestCaseHeader("Mercury Tours - TestCase 1");
			
			ReportConfigurations.appendTestCaseSubTitle("Test Case : "+Temp.iTcCnt +" - Validate the credentials of the user on the application.");
			boolean loginTest=DashboardPage.fnLogin("Test", "Test");
			Assert.assertFalse(loginTest);
			ReportConfigurations.Close_Subtitle();
			
			ReportConfigurations.close_TestcaseHeader();
			ReportConfigurations.endExtentReport();
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	@Test(priority=2,enabled=true)
	public static void TestCase2() 
	{
		try{
			
		
			ReportConfigurations.appendTestCaseHeader("Mercury Tours - TestCase 2");
			
			UserEntity user1=new UserEntity();
			user1.setFirstName("Sanjay");user1.setLastName("Ramasamy");
			user1.setExp_month("04");user1.setExp_year("2010");
			user1.setCreditCardType("MasterCard");user1.setCreditCardNumber("1234 5678 9012");
			
			
			ReportConfigurations.appendTestCaseSubTitle("Test Case : "+Temp.iTcCnt +" -  User should be able to book a return ticket successfully by logging into application");
			boolean loginTest=DashboardPage.fnLogin("Ramasamy", "Ramasamy");
			Assert.assertTrue(loginTest);
			DashboardPage.fnNavigateToFlightsPage();
			boolean bookTicketsTest=FlightsPage.fnBookReturnTicket(user1.getFirstName(), user1.getLastName(), user1.getCreditCardType(), user1.getCreditCardNumber(), user1.getExp_month(), user1.getExp_year());
			Assert.assertTrue(bookTicketsTest);
			FlightsPage.fnLogout();
			ReportConfigurations.Close_Subtitle();
			
			
			ReportConfigurations.close_TestcaseHeader();
			
			ReportConfigurations.endExtentReport();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
