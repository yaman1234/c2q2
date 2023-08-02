package utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * This class contains basic functions for table SR ID = JQCP-I-0101
 * 
 * @author sanish
 */
public class TableData extends DateOperations {

	/**
	 * Returns text of a cell in a table
	 * 
	 * @param targetTable
	 * @param objRow
	 * @param objCol
	 * @return text inside the cell of the table as String
	 */
	public static String getCellText(WebElement targetTable, int objRow, int objCol) {
		return getElement(targetTable, objRow, objCol).getText();
	}

	/**
	 * Searches a text in a table and returns its location in that table
	 * 
	 * @param targetTable
	 * @param searchText
	 * @return List of index of that search value as [row, col]
	 */
	public static List<Integer> getCellIndex(WebElement targetTable, String searchText) {
		int objRow = 0, objCol = 0, rowCount = getRowCount(targetTable), colCount = getColumnCount(targetTable);
		List<Integer> index = new ArrayList<Integer>();

		outerLoop: while (objRow < rowCount) {
			objCol = 0;
			while (objCol < colCount) {
				String cellText = getElement(targetTable, objRow, objCol).getText();
				if (cellText.equals(searchText)) {
					break outerLoop;
				}
				objCol++;
			}
			objRow++;
		}
		index.add(objRow);
		index.add(objCol);
		return index;
	}

	/**
	 * Checks if a row of a table contains some text or not
	 * 
	 * @param targetTable
	 * @param searchText
	 * @return true/false
	 */
	public static int doesTableContainText(WebElement targetTable, int colIndex, String searchText) {
		int objRow = 0, rowCount = getRowCount(targetTable);
		while (objRow < rowCount) {
			String cellText = getElement(targetTable, objRow, colIndex).getText().toLowerCase();
			if (cellText.equals(searchText))
				return 1;
			objRow++;
		}
		return rowCount == 0 ? -1 : 0;
	}

	public static int doesTableContainSpanText(WebElement targetTable, int colIndex, String searchText,
			String spanAttribute) {
		int rowCount = getRowCount(targetTable);
		for (int i = 0; i < rowCount; i++) {
			WebElement tempRow = TableData.getElement(targetTable, i, colIndex);
			WebElement span = GenerateXpath.getImmediateChildren(tempRow, "span").get(1);
			String cellText = WebElementLib.getAttributeValue(span, spanAttribute).toLowerCase();

			if (cellText.contains(searchText))
				return 1;
		}

		return rowCount == 0 ? -1 : 0;
	}

	public static int tableContainSpanTextCount(WebElement targetTable, int colIndex, String searchText,
			String spanAttribute) {
		int rowCount = getRowCount(targetTable), count = 0;
		for (int i = 0; i < rowCount; i++) {
			WebElement tempRow = TableData.getElement(targetTable, i, colIndex);
			WebElement span = GenerateXpath.getImmediateChildren(tempRow, "span").get(1);
			String cellText = WebElementLib.getAttributeValue(span, spanAttribute).toLowerCase();
			if (cellText.contains(searchText)) {
				count++;
			}

		}
		return rowCount == 0 ? -1 : count;

	}

	/**
	 * Counts number of rows in a table
	 * 
	 * @param targetTable
	 * @return number of rows in a table
	 */
	public static int getRowCount(WebElement targetTable) {
		if (WebElementLib.doesElementExist(targetTable)) {
			List<WebElement> rows = targetTable.findElements(By.tagName("tr"));
			return rows.size();
		} else
			return 0;
	}

	/**
	 * Counts number of columns in a table
	 * 
	 * @param targetTable
	 * @return number of columns of a table
	 */
	public static int getColumnCount(WebElement targetTable) {

		if (WebElementLib.doesElementExist(targetTable)) {
			List<WebElement> rows = new ArrayList<>();
			try {
				rows = targetTable.findElements(By.tagName("tbody")).get(0).findElements(By.tagName("tr"));
			} catch (NoSuchElementException e) {
				rows = targetTable.findElements(By.tagName("tr"));
			}
			List<WebElement> columns = rows.get(0).findElements(By.xpath("./*"));
			return columns.size();
		} else {
			return 0;
		}
	}

	/**
	 * Returns list of texts of cells under the row header defined as web element in
	 * a table
	 * 
	 * @param targetTable
	 * @param rowHeader
	 * @return list of texts of cells
	 */
	public static List<String> getColumnData(WebElement targetTable, WebElement rowHeader) {
		String rowHeaderText = rowHeader.getText();
		int columnIndex = getCellIndex(targetTable, rowHeaderText).get(1);
		return getColumnData(targetTable, columnIndex);
	}

	/**
	 * Returns list of texts of cells under the row header matched by the string in
	 * a table
	 * 
	 * @param targetTable
	 * @param rowHeader
	 * @return list of texts of cells
	 */
	public static List<String> getColumnData(WebElement targetTable, String rowHeader) {
		int columnIndex = getCellIndex(targetTable, rowHeader).get(1);
		return getColumnData(targetTable, columnIndex);
	}

	/**
	 * Returns list of texts of cells of a column located by the index in a table
	 * 
	 * @param targetTable
	 * @param columnIndex
	 * @return list of texts of cells
	 */
	public static List<String> getColumnData(WebElement targetTable, int columnIndex) {
		List<String> columnData = new ArrayList<>();
		String cellText = null;
		int i = 0, rowsCount = getRowCount(targetTable);
		while (i < rowsCount) {
			cellText = getElement(targetTable, i, columnIndex).getText();
			columnData.add(cellText);
			i++;
		}
		return columnData;
	}

	/**
	 * Returns the cells of a column as a list of web elements
	 * 
	 * @param targetTable
	 * @param columnIndex
	 * @return list of web elements
	 */
	public static List<WebElement> getColumnElements(WebElement targetTable, int columnIndex) {
		List<WebElement> columnData = new ArrayList<>();
		int i = 0, rowsCount = getRowCount(targetTable);
		while (i < rowsCount) {
			columnData.add(getElement(targetTable, i, columnIndex));
			i++;
		}
		return columnData;
	}

	/**
	 * Returns list of texts of cells under the column header defined as web element
	 * in a table
	 * 
	 * @param targetTable
	 * @param columnHeader
	 * @return list of texts of cells
	 */
	public static List<String> getRowData(WebElement targetTable, WebElement columnHeader) {
		String colHeaderText = columnHeader.getText();
		int rowIndex = getCellIndex(targetTable, colHeaderText).get(0);
		return getRowData(targetTable, rowIndex);
	}

	/**
	 * Returns list of texts of cells under the column header matched by the string
	 * in a table
	 * 
	 * @param targetTable
	 * @param columnHeader
	 * @return list of texts of cells
	 */
	public static List<String> getRowData(WebElement targetTable, String columnHeader) {
		int rowIndex = getCellIndex(targetTable, columnHeader).get(0);
		return getRowData(targetTable, rowIndex);
	}

	/**
	 * Returns list of texts of cells of a row located by the index in a table
	 * 
	 * @param targetTable
	 * @param rowIndex
	 * @return list of texts of cells
	 */
	public static List<String> getRowData(WebElement targetTable, int rowIndex) {
		List<String> rowData = new ArrayList<>();
		String cellText = null;
		int i = 0, colCount = getColumnCount(targetTable);
		while (i < colCount) {
			cellText = getElement(targetTable, rowIndex, i).getText();
			rowData.add(cellText);
			i++;
		}
		return rowData;
	}

	/**
	 * Returns the cells of a row as a list of web elements
	 * 
	 * @param targetTable
	 * @param rowIndex
	 * @return list of web elements
	 */
	public static List<WebElement> getRowElements(WebElement targetTable, int rowIndex) {
		List<WebElement> rowData = new ArrayList<>();
		int i = 0, colCount = getColumnCount(targetTable);
		while (i < colCount) {
			rowData.add(getElement(targetTable, rowIndex, i));
			i++;
		}
		return rowData;
	}

	/**
	 * Counts total number of records in a column of a table
	 * 
	 * @param targetTable
	 * @param columnIndex
	 * @param recordToSearch
	 * @return number of searched record found in a column of a table
	 */
	public static int totalRecordCount(WebElement targetTable, int columnIndex, String recordToSearch) {
		int recordCount = 0;
		int rowCount = getRowCount(targetTable);
		List<String> colData = getColumnData(targetTable, columnIndex);
		for (String item : colData) {
			if (item.equalsIgnoreCase(recordToSearch))
				recordCount++;
		}
		return rowCount == 0 ? -1 : recordCount;
	}

	/**
	 * Checks whether or not list of items are sorted or not
	 * 
	 * @param targetTable
	 * @param columnIndex
	 * @return order of sort (ascending, descending, unsorted)
	 */
	public static String isColumnSorted(WebElement targetTable, int columnIndex) {
		List<String> columnData = getColumnData(targetTable, columnIndex);
		return isColumnSorted(columnData);
	}

	/**
	 * Checks whether or not list of items are sorted or not
	 * 
	 * @param columnData
	 * @return order of sort (ascending, descending, unsorted)
	 */
	public static String isColumnSorted(List<String> columnData) {

		if (columnData.size() == 0)
			return "table has no data";

		try {
			List<Integer> unsorted = new ArrayList<Integer>();
			List<Integer> ascSorted = new ArrayList<Integer>();
			List<Integer> descSorted = new ArrayList<Integer>();

			for (String columnText : columnData) {
				unsorted.add(Integer.parseInt(columnText));
			}

			descSorted.addAll(unsorted);
			Collections.sort(descSorted);
			ascSorted.addAll(descSorted);
			Collections.reverse(descSorted);

			if (ascSorted.get(0).equals(ascSorted.get(ascSorted.size() - 1)))
				return "all data are same";
			else if (unsorted.equals(ascSorted))
				return "ascending";
			else if (unsorted.equals(descSorted))
				return "descending";
			else
				return "unsorted";

		} catch (NumberFormatException e) {

			List<String> unsorted = new ArrayList<>();
			List<String> ascSorted = new ArrayList<>();
			List<String> descSorted = new ArrayList<>();

			for (String columnText : columnData) {
				unsorted.add(columnText.toLowerCase());
			}

			descSorted.addAll(unsorted);
			Collections.sort(descSorted);
			ascSorted.addAll(descSorted);
			Collections.reverse(descSorted);

			if (ascSorted.get(0).equalsIgnoreCase(ascSorted.get(ascSorted.size() - 1)))
				return "all data are same";
			else if (unsorted.equals(ascSorted))
				return "ascending";
			else if (unsorted.equals(descSorted))
				return "descending";
			else
				return "unsorted";
		}
	}

	/**
	 * this method checks the sorting of table data
	 * 
	 * @param table
	 *            (WebElement)
	 * @param clickableHeader
	 *            table (WebElement)
	 * @return order of column sort (ascending, descending or unsorted)
	 * 
	 * @author udhakal
	 **/
	public String isColumnSorted(WebElement targetTable, WebElement clickableHeader) {

		String headerText = clickableHeader.getText();

		String tableID = targetTable.getAttribute("id");

		WebElement thead = null;
		boolean isThead = targetTable.findElements(By.tagName("thead")).size() > 0;
		if (isThead == true) {
			thead = targetTable.findElement(By.tagName("thead"));
			// System.out.println("head text : "+ headerText);
		} else {
			thead = targetTable.findElement(By.tagName("th"));
		}

		if (tableID != "") {

			/** this block gives the no of columns of table **/
			List<WebElement> col = thead.findElements(By.tagName("th"));
			int colSize = col.size();
			// System.out.println("no of column : " + colSize);

			/** this block gives no of rows of table **/
			List<WebElement> row = targetTable.findElements(By.tagName("tr"));
			int rowSize = row.size();
			// System.out.println( "no of row : "+ rowSize);

			/**
			 * this block get the header of the table which we have to click and click that
			 * respective table header for the sorting
			 **/
			int k = 0;
			for (k = 1; k <= colSize; k++) {
				if (col.get(k - 1).getText().equalsIgnoreCase(headerText)) {
					col.get(k - 1).click();
					break;
				}
			}

			/**
			 * this block iterate through the column of the respective table header and gets
			 * the column data in an array 'rowText[]'
			 **/
			char a = '"';
			String[] rowText = new String[99999];
			for (int i = 1; i < rowSize; i++) {
				String xpath = "//*[@id=" + a + tableID + a + "]/tbody/tr[" + i + "]/td[" + k + "]";
				// System.out.println(xpath);
				WebElement tableRow = targetTable.findElement(By.xpath(xpath));
				rowText[i] = tableRow.getText();

			}

			/**
			 * this block iterate over array 'rowText[]' and compares the order of the data
			 **/
			int loopCountG = 0, loopCountL = 0, loopCountC = 0, j = 0;
			for (j = 1; j < rowSize - 1; j++) {
				int lexicoValue = rowText[j].compareToIgnoreCase(rowText[j + 1]);

				if (lexicoValue < 0) {
					loopCountL++;
				} else if (lexicoValue > 0) {
					loopCountG++;
				} else {
					loopCountC++;
				}
			}

			if (j - 1 == loopCountL + loopCountC)
				return "ascending";
			else if (j - 1 == loopCountG + loopCountC)
				return "descending";
			else
				return "unsorted";
		} else
			return "unable to find table ID to check sorting";
	}

	/**
	 * Get element at a specific cell of a table located by row and column index
	 * 
	 * @param targetTable
	 * @param objRow
	 * @param objCol
	 * @return cell as WebElement
	 */
	public static WebElement getElement(WebElement targetTable, int objRow, int objCol) {
		// System.out.println(table);

		if (WebElementLib.doesElementExist(targetTable)) {
			WebElement elementToReturn;
			try {
				elementToReturn = targetTable.findElements(By.tagName("tr")).get(objRow).findElements(By.xpath("./*"))
						.get(objCol);
			} catch (Exception e) {
				elementToReturn = null;
			}

			try {
				WebElement tHead = targetTable.findElement(By.tagName("thead"));
				List<WebElement> rows = tHead.findElements(By.tagName("tr"));

				if (rows.size() > objRow) {
					List<WebElement> columns = rows.get(objRow).findElements(By.xpath("./*"));
					elementToReturn = columns.size() < objCol ? null : columns.get(objCol);
				} else {
					objRow -= rows.size();
				}
			} catch (Exception e) {
			}

			try {
				List<WebElement> tBodies = targetTable.findElements(By.tagName("tbody"));

				for (WebElement tBody : tBodies) {
					List<WebElement> rows = tBody.findElements(By.tagName("tr"));

					if (rows.size() > objRow) {
						List<WebElement> columns = rows.get(objRow).findElements(By.xpath("./*"));
						elementToReturn = columns.size() < objCol ? null : columns.get(objCol);
					} else {
						objRow -= rows.size();
					}
				}
			} catch (Exception e) {
			}

			try {
				List<WebElement> tFoots = targetTable.findElements(By.tagName("tfoot"));

				for (WebElement tFoot : tFoots) {
					List<WebElement> rows = tFoot.findElements(By.tagName("tr"));

					if (rows.size() > objRow) {
						List<WebElement> columns = rows.get(objRow).findElements(By.xpath("./*"));
						elementToReturn = columns.size() < objCol ? null : columns.get(objCol);
					} else {
						objRow -= rows.size();
					}
				}
			} catch (Exception e) {
			}
			return elementToReturn;
		} else {
			return null;
		}
	}

	/**
	 * Get element at a specific cell of a table located by row and column index
	 * 
	 * @param targetTable
	 * @param searchText
	 * @return cell as WebElement
	 */
	public static WebElement getElement(WebElement targetTable, String searchText) {
		if (WebElementLib.doesElementExist(targetTable)) {
			List<Integer> indices = getCellIndex(targetTable, searchText);
			return getElement(targetTable, indices.get(0), indices.get(1));
		} else {
			return null;
		}
	}

	/**
	 * @param table
	 * @param data
	 * @author prabin
	 */
	public static void clickSearchText(WebElement table, String data) {
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int rows_count = rows_table.size();
		outerLoop: for (int row = 0; row < rows_count; row++) {
			List<WebElement> columns_row = rows_table.get(row).findElements(By.tagName("td"));
			int columns_count = columns_row.size();

			for (int column = 0; column < columns_count; column++) {

				if (columns_row.get(column).getText().equals(data)) {
					columns_row.get(column).click();
					break outerLoop;
				}
			}
		}
	}
}
