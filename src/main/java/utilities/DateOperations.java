/**
 * 
 */
package utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebElement;

//import pageObjects.A4SpotQuoteQueue;

/**
 * This class contains methods to deal with dates from a table
 * 
 * @author sanish
 *
 */
public class DateOperations extends UtilBase {

	/**
	 * Checks whether or not list of date are sorted or not
	 * 
	 * @param datesFromTable
	 * @param dateformat
	 * @return order of sort (ascending, descending, unsorted)
	 */

	public static String isDateSorted(List<String> datesFromTable, String dateformat) {

		if (datesFromTable.size() == 0)
			return "table has no data";

		List<LocalDate> dateList = new ArrayList<>();
		for (String itemDate : datesFromTable)
			dateList.add(convertToDate(itemDate, dateformat));

		List<LocalDate> unsortedDateList = new ArrayList<>(), sortedDateList = new ArrayList<>();
		unsortedDateList.addAll(dateList);
		Collections.sort(dateList);
		sortedDateList.addAll(dateList);
		Collections.reverse(dateList);

		if (sortedDateList.get(0).equals(sortedDateList.get(sortedDateList.size() - 1)))
			return "all data are same";
		else if (sortedDateList.equals(unsortedDateList))
			return "ascending";
		else if (dateList.equals(unsortedDateList))
			return "descending";
		else
			return "unsorted";
	}

	/**
	 * Checks whether or not list of date at a column from the table are sorted or
	 * not
	 * 
	 * @param targetTable
	 * @param columnIndex
	 * @param dateformat
	 * @return order of sort (ascending, descending, unsorted)
	 */
	public static String isDateSorted(WebElement targetTable, int columnIndex, String dateformat) {

		List<String> datesFromTable = TableData.getColumnData(targetTable, columnIndex);
		return isDateSorted(datesFromTable, dateformat);
	}

	/**
	 * Checks whether or not dates from table match the date used for searching
	 * 
	 * @param targetTable
	 * @param columnIndex
	 * @param dateFormat
	 * @param dateToSearch
	 * @param searchDateFormat
	 * @return true/false
	 */
	public static boolean doesDateMatch(WebElement targetTable, int columnIndex, String dateFormat, String dateToSearch,
			String searchDateFormat) {
		List<String> dateList = TableData.getColumnData(targetTable, columnIndex);
		return doesDateMatch(dateList, dateFormat, dateToSearch, searchDateFormat);
	}

	/**
	 * Checks whether or not dates match the date used for searching
	 * 
	 * @param dateList
	 * @param listDateformat
	 * @param dateToSearch
	 * @param searchDateFormat
	 * @return true/false
	 */
	public static boolean doesDateMatch(List<String> dateList, String listDateformat, String dateToSearch,
			String searchDateFormat) {

		if (dateList.size() == 0)
			return false;

		List<LocalDate> listOfDates = new ArrayList<>();
		for (String itemDate : dateList)
			listOfDates.add(convertToDate(itemDate, listDateformat));
		LocalDate searchDate = convertToDate(dateToSearch, searchDateFormat);

		return doesDateMatch(listOfDates, searchDate);
	}

	/**
	 * Checks whether or not dates match the date used for searching
	 * 
	 * @param dateList
	 * @param dateToSearch
	 * @return true/false
	 */
	public static boolean doesDateMatch(List<LocalDate> dateList, LocalDate dateToSearch) {

		if (dateList.size() == 0)
			return false;

		for (LocalDate itemDate : dateList) {
			if (!itemDate.equals(dateToSearch))
				return false;
		}
		return true;
	}

	/**
	 * Checks whether or not resulting list of dates lie in between two dates used
	 * for searching
	 * 
	 * @param targetTable
	 * @param columnIndex
	 * @param listDateformat
	 * @param startDate
	 * @param endDate
	 * @param searchDateFormat
	 * @return true/false
	 */
	public static boolean areDatesInBetween(WebElement targetTable, int columnIndex, String listDateformat,
			String startDate, String endDate, String searchDateFormat) {
		List<String> dateList = TableData.getColumnData(targetTable, columnIndex);
		return areDatesInBetween(dateList, listDateformat, startDate, endDate, searchDateFormat);
	}

	/**
	 * Checks whether or not resulting list of dates lie in between two dates used
	 * for searching
	 * 
	 * @param dateList
	 * @param listDateformat
	 * @param startDate
	 * @param endDate
	 * @param searchDateFormat
	 * @return true/false
	 */
	public static boolean areDatesInBetween(List<String> dateList, String listDateformat, String startDate,
			String endDate, String searchDateFormat) {

		if (dateList.size() == 0)
			return false;

		List<LocalDate> listOfDates = new ArrayList<>();
		for (String itemDate : dateList)
			listOfDates.add(convertToDate(itemDate, listDateformat));
		LocalDate stDate, enDate;
		stDate = convertToDate(startDate, searchDateFormat);
		enDate = convertToDate(endDate, searchDateFormat);

		return areDatesInBetween(listOfDates, stDate, enDate);
	}

	/**
	 * Checks whether or not resulting list of dates lie in between two dates used
	 * for searching
	 * 
	 * @param dateList
	 * @param startDate
	 * @param endDate
	 * @return true/false
	 */
	public static boolean areDatesInBetween(List<LocalDate> dateList, LocalDate startDate, LocalDate endDate) {

		if (dateList.size() == 0)
			return false;
		else if (startDate.equals(endDate))
			return doesDateMatch(dateList, startDate);
		else {
			for (LocalDate itemDate : dateList) {
				if (!startDate.isBefore(itemDate) || !endDate.isAfter(itemDate))
					return false;
			}
			return true;
		}
	}

	/**
	 * Returns the largest difference of dates in days from today
	 * 
	 * @param targetTable
	 * @param columnIndex
	 * @param dateFormat
	 * @return largest difference of dates in days from today
	 */
	public static long getRangeOfDates(WebElement targetTable, int columnIndex, String dateFormat) {
		List<String> dateList = TableData.getColumnData(targetTable, columnIndex);
		return getRangeOfDates(dateList, dateFormat);
	}

	/**
	 * Returns the largest difference of dates in days from today
	 * 
	 * @param dateList
	 * @param dateFormat
	 * @return largest difference of dates in days from today
	 */
	public static long getRangeOfDates(List<String> dateList, String dateFormat) {
		List<LocalDate> dates = new ArrayList<>();
		for (String itemDate : dateList)
			dates.add(convertToDate(itemDate, dateFormat));
		return getRangeOfDates(dates);
	}

	/**
	 * Returns the largest difference of dates in days from today
	 * 
	 * @param dateList
	 * @return largest difference of dates in days from today
	 */
	public static long getRangeOfDates(List<LocalDate> dateList) {
		long rangeInDays = 0, temp;
		for (LocalDate itemDate : dateList) {
			temp = getDateRange(itemDate);
			if (temp > rangeInDays)
				rangeInDays = temp;
		}
		return rangeInDays;
	}

	/**
	 * Returns the number of days between today and the given date
	 * 
	 * @param date
	 * @param dateFormat
	 * @return number of days between today and the given date
	 */
	public static long getDateRange(String date, String dateFormat) {
		LocalDate itemDate = convertToDate(date, dateFormat);
		return getDateRange(itemDate);
	}

	/**
	 * Returns the number of days between today and the given date
	 * 
	 * @param itemDate
	 * @return number of days between today and the given date
	 */
	public static long getDateRange(LocalDate itemDate) {
		LocalDate today = LocalDate.now();
		return Math.abs(ChronoUnit.DAYS.between(itemDate, today));
	}

	/**
	 * Returns the number of dates included in the given range from the list of
	 * dates
	 * 
	 * @param targetTable
	 * @param columnIndex
	 * @param dateFormat
	 * @param startRange
	 * @param endRange
	 * @return count of dates
	 */
	public static int countDatesOfRange(WebElement targetTable, int columnIndex, String dateFormat, long startRange,
			long endRange) {
		List<String> dateList = TableData.getColumnData(targetTable, columnIndex);
		return countDatesOfRange(dateList, dateFormat, startRange, endRange);
	}

	/**
	 * Returns the number of dates included in the given range from the list of
	 * dates
	 * 
	 * @param dateList
	 * @param dateFormat
	 * @param startRange
	 * @param endRange
	 * @return count of dates
	 */
	public static int countDatesOfRange(List<String> dateList, String dateFormat, long startRange, long endRange) {
		List<LocalDate> dates = new ArrayList<>();
		for (String itemDate : dateList)
			dates.add(convertToDate(itemDate, dateFormat));
		return countDatesOfRange(dates, startRange, endRange);
	}

	/**
	 * Returns the number of dates included in the given range from the list of
	 * dates
	 * 
	 * @param dateList
	 * @param startRange
	 * @param endRange
	 * @return count of dates
	 */
	public static int countDatesOfRange(List<LocalDate> dateList, long startRange, long endRange) {
		LocalDate today = LocalDate.now();
		int totalDates = 0;
		for (LocalDate date : dateList) {
			Long rangeInDays = Math.abs(ChronoUnit.DAYS.between(date, today));
			if (rangeInDays >= startRange && rangeInDays <= endRange)
				totalDates++;
		}
		return totalDates;
	}

	/**
	 * Converts the string into relative date using the format given
	 * 
	 * @param dateText
	 * @param dateFormat
	 * @return date
	 */
	public static LocalDate convertToDate(String dateText, String dateFormat) {
		LocalDate dateToReturn = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		dateToReturn = LocalDate.parse(dateText, formatter);
		return dateToReturn;
	}
	/*
	 * /**
	 * 
	 * @param date
	 * 
	 * @throws Exception
	 * 
	 * // Ant design date picker plugin
	 *
	 * 
	 * 
	 * public static void datePicker(String date) throws Exception {
	 * A4SpotQuoteQueue sqqPageObj = new A4SpotQuoteQueue(); WebElement yearLink =
	 * sqqPageObj.datePickerYearLink(); WebElement monthLink =
	 * sqqPageObj.datePickerMonthLink();
	 * 
	 * // String date_example = ("30/07/2018"); String str = new String(date);
	 * System.out.println(str); // String[]parts = str.split("/"); String[] parts =
	 * str.split("-"); String yyyy = parts[0]; String mm = parts[1]; String dd =
	 * String.valueOf(Integer.parseInt(parts[2])); //
	 * System.out.println("Day:"+dd+"Month:"+mm+"Year:"+yyyy);
	 * 
	 * WebElement yearDrop =
	 * driver.findElement(By.xpath("//*[@id=\"calendar_year_txt\"]"));
	 * ddown.selectOptionByVisibleText(yearDrop, yyyy); Thread.sleep(2000);
	 * WebElement mmDrop = driver.findElement(By.id("monthSelect"));
	 * ddown.selectOptionByIndex(mmDrop, Integer.parseInt(mm) -1);
	 * Thread.sleep(2000);
	 * 
	 * 
	 * yearLink.click();
	 * 
	 * WebElement yearTable = sqqPageObj.datePickerYearTable(); // xpath of year
	 * table Thread.sleep(1000); TableData.clickSearchText(yearTable, yyyy);
	 * Thread.sleep(3000);
	 * 
	 * monthLink.click(); Thread.sleep(1000); WebElement monthTable =
	 * sqqPageObj.datePickeMonthTable(); // xpath of month table String month =
	 * null; if (mm.equals("01")) { month = "Jan"; } else if (mm.equals("02")) {
	 * month = "Feb"; } else if (mm.equals("03")) { month = "Mar"; } else if
	 * (mm.equals("04")) { month = "Apr"; } else if (mm.equals("05")) { month =
	 * "May"; } else if (mm.equals("06")) { month = "Jun"; } else if
	 * (mm.equals("07")) { month = "Jul"; } else if (mm.equals("08")) { month =
	 * "Aug"; } else if (mm.equals("09")) { month = "Sep"; } else if
	 * (mm.equals("10")) { month = "Oct"; } else if (mm.equals("11")) { month =
	 * "Nov"; } else if (mm.equals("12")) { month = "Dec"; }
	 * TableData.clickSearchText(monthTable, month); Thread.sleep(2000); WebElement
	 * dateTable = sqqPageObj.datePickeDateTable(); // xpath of date table
	 * 
	 * WebElement yearDrop =
	 * driver.findElement(By.xpath("//*[@id=\"calendar_year_txt\"]"));
	 * ddown.selectOptionByVisibleText(yearDrop, yyyy); Thread.sleep(2000);
	 * WebElement mmDrop = driver.findElement(By.id("monthSelect"));
	 * ddown.selectOptionByIndex(mmDrop, Integer.parseInt(mm) -1);
	 * Thread.sleep(2000);
	 * 
	 * 
	 * TableData.clickSearchText(dateTable, dd); Thread.sleep(2000); }
	 */
}
