/**
 * 
 */
package utilities;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author sanish
 *
 */
public class FileDownload extends UtilBase {

	private static final String downloadPath = System.getProperty("user.home") + "/Downloads";

	public static boolean doesDownloadWork(WebElement downloadLink) {
		String href = downloadLink.getAttribute("href");
		String baseUrl = driver.getCurrentUrl().toString();
		URL url = null;

		if (href == null || href == "") {
			// MouseOperations.leftClick(downloadLink);

			for (long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(5); stop > System.nanoTime();) {
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				if (HandleTabs.noOfTabsOpen() > 1) {
					HandleTabs.switchToTab(1);
					baseUrl = driver.getCurrentUrl();
					System.out.println(baseUrl);
					HandleTabs.switchToTab(0);

					try {
						url = new URL(baseUrl);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					HttpURLConnection conn;
					try {
						conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("GET");
						String userAgent = (String) jsDriver.executeScript("return navigator.userAgent;");
						conn.setRequestProperty("User-Agent", userAgent);
						System.out.println(conn.getResponseCode());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} else {
			if (!href.startsWith("http")) {
				try {
					url = new URL(baseUrl);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				baseUrl.replace(url.getPath(), "");
				baseUrl += href;
			} else
				baseUrl = href;

			System.out.println(baseUrl);
			HandleTabs.openTab(baseUrl);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		}

		return false;
	}

	public static void getActiveDownloads() {
		String script = "var tag = document.querySelector('download-manager').shadowRoot;\n"
				+ "var items = tag.querySelectorAll('downloads-item');\n return items;";
		Object o = jsDriver.executeScript(script, driver.findElement(By.cssSelector("body")));
		System.out.println("downloads: " + o);
	}

	public static boolean isFileDownloaded(String fileName) throws InterruptedException {
		File lastestfile = null;
		int i = 0;
		do {
			Thread.sleep(10000);
			lastestfile = getLatestFiles(1)[0];
			i++;
			
			String fileExt = FilenameUtils.getExtension(lastestfile.getName()).toUpperCase();

			if (lastestfile.getName().startsWith(fileName) && "|XLS|XLSX|XLSB|CSV|".contains(fileExt)
					&& ((System.currentTimeMillis() - getLatestFiles(1)[0].lastModified()) <= 60000)) {
				return true;
			}

		} while (i < 200 && !lastestfile.exists());

		return false;
	}

	public static File[] getLatestFiles(int numOfFiles) {
		File dir = new File(downloadPath);
		File[] filesToRet = new File[numOfFiles];
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
		for (int i = 0; i < numOfFiles; i++)
			filesToRet[i] = files[i];
		return filesToRet;
	}

}