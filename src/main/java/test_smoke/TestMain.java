package test_smoke;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



public class TestMain {

	public static void main(String[] args) throws IOException {
		  // Specify the path of the Excel file
        String filePath = System.getProperty("user.dir")+"\\testdata\\testData.xlsx";

        // Create a FileInputStream to read the Excel file
        FileInputStream fis = new FileInputStream(filePath);

        // Create a workbook object from the Excel file
        Workbook workbook = WorkbookFactory.create(fis);

        // Get the desired sheet from the workbook (e.g., sheet at index 0)
        Sheet sheet = workbook.getSheetAt(0);

        // Iterate through rows and columns to read the data
        for (Row row : sheet) {
            for (Cell cell : row) {
                // Depending on the cell type, read the value and print it
                switch (cell.getCellType()) {
                    case STRING:
                        System.out.print(cell.getStringCellValue() + "\t");
                        break;
                    case NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "\t");
                        break;
                    case BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t");
                        break;
                    default:
                        System.out.print("\t");
                }
            }
            System.out.println(); // Move to the next row
        }

        // Close the FileInputStream and workbook objects
        fis.close();
        workbook.close();

	}

}
