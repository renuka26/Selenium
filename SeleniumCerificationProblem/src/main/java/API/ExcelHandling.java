package API;

import java.io.File;

import java.io.IOException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public abstract class ExcelHandling {

	public static Workbook workbook;
	public static Sheet sheet;
	static WorkbookSettings settings = new WorkbookSettings();
	
	public static String fnReadValExcel(String ExcelName, String SheetName, int col, int row)
			throws BiffException, IOException {
		String strVal = "";
		try {
			
			//System.out.println(ExcelName+SheetName+col+row+"!!!!!!!!!!!!!");
			settings.setSuppressWarnings(true);
			workbook = Workbook.getWorkbook(new File(ExcelName),settings);
			sheet = workbook.getSheet(SheetName);
			strVal = sheet.getCell(col, row).getContents().toString().trim();
			return strVal;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Probel in reading excel");
		}
		return strVal;
	}
	
	//To write the value into given excel,sheet,row and column
	public static void fnWriteValExcel(String ExcelName, String SheetName, int col, int row,String value){
		try{
			settings.setSuppressWarnings(true);
			WritableWorkbook rwb;
			WritableSheet rws;
			workbook = Workbook.getWorkbook(new File(ExcelName),settings);
			rwb = Workbook.createWorkbook(new File(ExcelName), workbook);
			rws = rwb.getSheet(SheetName);
			System.out.println("Writing    "+value+" "+SheetName+col+row);
 if(row==7)
	 System.out.println("!!");
			Label L1 = new Label(col, row, value);
			rws.addCell(L1);
			rwb.write();
			rwb.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
}
