package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * @author sanish
 *
 */
public class ExcelRead {

	private static XSSFCell cell = null;
	private static String path = "";

	/**
	 * To open excel file
	 * 
	 * @return Excel path
	 */
	private static String getExcelFile() {

		// Getting Location of the project
		String path1 = "";
		try {
			path1 = ExcelRead.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String jarName = path1.substring(path1.lastIndexOf('/') + 1); // Finding the name of jar file
		try {
			// If project is running from jar file
			if (path1.contains(".jar")) {
				path = path1.replace(jarName, "/testdata.xlsx"); // Excel File if the project run from jar file
			} else {
				path = System.getProperty("user.dir") + "//testData//testdata.xlsx"; // Direct path for excel file
			}
			return path;
		} catch (Exception e) {
			return "";
		}
	}

	
	private static String getExcelFile_writer() {

		// Getting Location of the project
		String path1 = "";
		try {
			path1 = ExcelRead.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String jarName = path1.substring(path1.lastIndexOf('/') + 1); // Finding the name of jar file
		try {
			// If project is running from jar file
			if (path1.contains(".jar")) {
				path = path1.replace(jarName, "/writedata.xlsx"); // Excel File if the project run from jar file
			} else {
				path = System.getProperty("user.dir") + "//testData//writedata.xlsx"; // Direct path for excel file
			}
			return path;
		} catch (Exception e) {
			return "";
		}
	}
	/**
	 * Getting value from particular excel cell from given sheet name
	 * 
	 * @param row
	 * @param col
	 * @return cell data from particular cell
	 * 
	 */
	public static String getData(int row, int col, String sheetName) {
		String cellData = null;
		String path1 = getExcelFile(); // Calling GetExcelFile function to get excel file
		// If path is empty
		if (path1 == "") {
			path = "";
			JOptionPane.showMessageDialog(null, "Excel File is not selected!!", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			path = path1;
		}
		try {
			FileInputStream excelFile = new FileInputStream(path);
			XSSFWorkbook excelWBook = new XSSFWorkbook(excelFile);
			XSSFSheet excelWSheet = excelWBook.getSheet(sheetName); // Name of the Excel Sheet
			DataFormatter formatter = new DataFormatter();
			cell = excelWSheet.getRow(row).getCell(col);
			cellData = formatter.formatCellValue(cell);
			excelWBook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellData.equals(null) ? "" : cellData;
	}

	/**
	 * Getting value from particular excel cell from given sheet index
	 * 
	 * @param row
	 * @param col
	 * @return cell data from particular cell
	 * 
	 */
	public static String getData(int row, int col, int sheetIndex) {
		String cellData = null;
		String path1 = getExcelFile(); // Calling GetExcelFile function to get excel file
		// If path is empty
		if (path1 == "") {
			path = "";
			JOptionPane.showMessageDialog(null, "Excel File is not selected!!", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			path = path1;
		}
		try {
			FileInputStream excelFile = new FileInputStream(path);
			XSSFWorkbook excelWBook = new XSSFWorkbook(excelFile);
			XSSFSheet excelWSheet = excelWBook.getSheetAt(sheetIndex); // Name of the Excel Sheet
			DataFormatter formatter = new DataFormatter();
			cell = excelWSheet.getRow(row).getCell(col);
			cellData = formatter.formatCellValue(cell);
			excelWBook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellData.equals(null) ? "" : cellData;
	}

	public static int getRowCount(String sheetName) {

		String path1 = getExcelFile(); // Calling GetExcelFile function to get excel file
		// If path is empty
		if (path1 == "") {
			path = "";
			JOptionPane.showMessageDialog(null, "Excel File is not selected!!", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			path = path1;
		}

		FileInputStream excelFile = null;
		try {
			excelFile = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFWorkbook excelWBook = null;
		try {
			excelWBook = new XSSFWorkbook(excelFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		XSSFSheet excelWSheet = excelWBook.getSheet(sheetName); // Name of the Excel Sheet

		return excelWSheet.getLastRowNum() - excelWSheet.getFirstRowNum();

	}

	public static int getRowCount_writer(String sheetName) throws IOException {
		String path1 = getExcelFile_writer(); // Calling GetExcelFile function to get excel file
		// If path is empty
		if (path1 == "") {
			path = "";
			JOptionPane.showMessageDialog(null, "Excel File is not selected!!", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			path = path1;
		}

		FileInputStream excelFile = null;
		try {
			excelFile = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFWorkbook excelWBook = null;
		try {
			excelWBook = new XSSFWorkbook(excelFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		XSSFSheet excelWSheet = excelWBook.getSheet(sheetName); // Name of the Excel Sheet

		return excelWSheet.getLastRowNum() - excelWSheet.getFirstRowNum();

	}
	
	public static String getData_writer(int row, int col, String sheetName) {
		String cellData = null;
		String path1 = getExcelFile_writer(); // Calling GetExcelFile function to get excel file
		// If path is empty
		if (path1 == "") {
			path = "";
			JOptionPane.showMessageDialog(null, "Excel File is not selected!!", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			path = path1;
		}
		try {
			FileInputStream excelFile = new FileInputStream(path);
			XSSFWorkbook excelWBook = new XSSFWorkbook(excelFile);
			XSSFSheet excelWSheet = excelWBook.getSheet(sheetName); // Name of the Excel Sheet
			DataFormatter formatter = new DataFormatter();
			cell = excelWSheet.getRow(row).getCell(col);
			cellData = formatter.formatCellValue(cell);
			excelWBook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellData.equals(null) ? "" : cellData;
	}
	
	public static void writeExcel_SQ(String currentCase, String status) throws InterruptedException, IOException {

		File file = new File(System.getProperty("user.dir") + "//testData//writedata.xlsx");
		FileInputStream inputStream = new FileInputStream(file);

		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheet("SQ");

//		get last row no.
		int rowNum = sheet.getLastRowNum();
		XSSFRow row = sheet.createRow(rowNum + 1);

		row.createCell(0).setCellValue(currentCase);
		row.createCell(1).setCellValue(status);

//		write the data in excel using output stream
		FileOutputStream outputStream = new FileOutputStream(file);
		wb.write(outputStream);
		wb.close();

	}

	public static void writeExcel_SF(String case_number, String status, String type, String subject) throws InterruptedException, IOException {

		File file = new File(System.getProperty("user.dir") + "//testData//writedata.xlsx");
		FileInputStream inputStream = new FileInputStream(file);

		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheet("SF");

//		get last row no.
		int rowNum = sheet.getLastRowNum();
		XSSFRow row = sheet.createRow(rowNum + 1);

		row.createCell(0).setCellValue(case_number);
		row.createCell(1).setCellValue(status);
		row.createCell(2).setCellValue(type);
		row.createCell(3).setCellValue(subject);

//		write the data in excel using output stream
		FileOutputStream outputStream = new FileOutputStream(file);
		wb.write(outputStream);
		wb.close();

	}

}
