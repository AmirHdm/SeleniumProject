import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;


import java.io.*;
import java.util.Iterator;

public class FunctExternal {

	public static int[] InitXLSX(String fileName) {
		int ColumnLength = 0;
		int RowLength = 0;
		InputStream XlsxFileToRead = null;
		XSSFWorkbook workbook = null;
		try {
			XlsxFileToRead = new FileInputStream(fileName);
			workbook = new XSSFWorkbook(XlsxFileToRead);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = workbook.getSheet("Data");
		XSSFRow row;
		XSSFCell cell;
		Iterator rows = sheet.rowIterator();
		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
			RowLength = row.getLastCellNum();
			Iterator cells = row.cellIterator();
			ColumnLength++;
			while (cells.hasNext()) {
				cell = (XSSFCell) cells.next();
			}
		}
		//ColumnLength--;
		return new int[] {RowLength, ColumnLength};		
		}	
	public static String[][] readXLSXFile(String fileName, int ci, int cj) {
		int ci1 = ci, cj1 = cj;
		 String[][] tabArray = new String [cj1][ci1];
		InputStream XlsxFileToRead = null;
		XSSFWorkbook workbook = null;
		try {
			XlsxFileToRead = new FileInputStream(fileName);
			
			//Getting the workbook instance for xlsx file
			workbook = new XSSFWorkbook(XlsxFileToRead);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//getting the first sheet from the workbook using sheet name. 
		// We can also pass the index of the sheet which starts from '0'.
		XSSFSheet sheet = workbook.getSheet("Data");
		XSSFRow row;
		XSSFCell cell;
		
		
		//Iterating all the rows in the sheet
		Iterator rows = sheet.rowIterator();
		

		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
		
			
			//Iterating all the cells of the current row
			Iterator cells = row.cellIterator();

			while (cells.hasNext()) {
				cell = (XSSFCell) cells.next();
				tabArray[cell.getRowIndex()][cell.getColumnIndex()] = new String( cell.getStringCellValue());				
			}
			//System.out.println();
			
			try {
				XlsxFileToRead.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/*for(int i = 0; i < tabArray.length; i++) {
		    for(int j = 0; j < tabArray[i].length; j++) {
		        System.out.print(tabArray[i][j]);
		        
		    }
		    System.out.println();
		}
		*/
		return tabArray;
	
	}
	
}
