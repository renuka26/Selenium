package PropertyFiles;

import java.io.FileReader;
import java.util.Properties;

import MasterSetup.BaseClass;
import MasterSetup.Temp;

public class PropertyFileHandling extends BaseClass{
	//To read data from property files
		public static String getDataFromPropertyFile(String variableName)
		{
			String dataFromPropertyFile=null;	
			try {
		
				//reader = new FileReader("D:/Selenium_Assignment/SeleniumAssignment/src/PropertyFiles/data.properties");
				//reader = new FileReader("src/PropertyFiles/data.properties");
				
				System.setProperty("SystemLogFile", Temp.ExcelConfigurations.SystemlogFiles_Path);
				System.setProperty("TestLogFile", Temp.ExcelConfigurations.TestlogFiles_Path);
				
				reader = new FileReader(Temp.ExcelConfigurations.PropertyFile_path);
				prop = new Properties();
				
				prop.load(reader);
				System.out.println(prop.getProperty(variableName));	
				dataFromPropertyFile=prop.getProperty(variableName);
				return dataFromPropertyFile;
			} catch (Exception e) {
				e.printStackTrace();
				} 
			return dataFromPropertyFile;
		}
		
}
