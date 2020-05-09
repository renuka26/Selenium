package MasterSetup;

import java.io.File;
import webdriver.Webdriver;

public class Temp {
	
	public static int iTcCnt;
	
	public static class ExcelConfigurations{
		
		public static String strImageFilePath = "D:\\Student_images\\";
		
		static File fileLogsSys = new File("LogFiles/"+Webdriver.projectName+"/System.Log");
	 	public static String SystemlogFiles_Path = fileLogsSys.getAbsolutePath();
			
	 	static File fileLogstest = new File("LogFiles/"+Webdriver.projectName+"/Test.Log");
	 	public static String TestlogFiles_Path = fileLogstest.getAbsolutePath();
			
		static File filePathPropertyFile = new File("src/main/java/PropertyFiles/data.properties");
	 	public static String PropertyFile_path = filePathPropertyFile.getAbsolutePath();
		
	 	static File fileDomainList = new File("data/domains/"+Webdriver.projectName+"/DomainList.xls");
	 	public static String DomainList_path = fileDomainList.getAbsolutePath();
	 	
	 	
	   
	}
	
	
	public static class SheetsConfigurations{
		
	
		public static String ConfigurationSheet="Configurations";
		
		public static String emailConfigurationSheet="EmailConfiguration";
							  
		
	}


	
	
	

	
}