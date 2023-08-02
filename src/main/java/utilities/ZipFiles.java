package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author sanish
 *
 */
public class ZipFiles {
	private static File html;

	/**
	 * Zips screenshot folder and test report into a file
	 * 
	 * @param fileName
	 */
	public static String zip(String fileName) {
		String zipFile = System.getProperty("user.dir") + "\\test-reports\\" + fileName + ".zip";
		List<String> srcFiles = new ArrayList<>();

		File[] files = new File("screenshots").listFiles();
		// If this pathname does not denote a directory, then listFiles() returns null.

		for (File file : files) {
			if (file.isFile()) {
				srcFiles.add(file.getAbsolutePath()); // Listing all files inside screenshots folder with its actual
														// path
			}
		}
		try {
			byte[] buffer = new byte[2048];
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			// For each file in screenshots folder
			for (String file : srcFiles) {
				File srcFile = new File(file);
				FileInputStream fis = new FileInputStream(srcFile);
				// Writing a new ZIP entry, positions the stream to the start of the entry data
				zos.putNextEntry(new ZipEntry("screenshots/" + srcFile.getName()));

				int length;
				while ((length = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, length);
				}
				zos.closeEntry();
				// Close the InputStream
				fis.close();

			}

			// To add html report file
			html = new File("test-reports\\" + fileName + ".html");
			FileInputStream fis = new FileInputStream(html);
			zos.putNextEntry(new ZipEntry(html.getName()));

			int length;
			while ((length = fis.read(buffer)) > 0) {
				zos.write(buffer, 0, length);
			}
			html.delete();
			zos.closeEntry();
			// Close the InputStream
			fis.close();
			// Close the ZipOutputStream
			zos.close();

			return zipFile;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return "";
		}
	}

	/**
	 * Deletes file inside the screenshot folder of current project
	 */
	public static void deleteFiles() {
		try {
			File screenShotFolder = new File("screenshots");
			File screenShotsFromZip = new File(System.getProperty("user.dir") + "\\test-reports\\screenshots");
			File[] files = screenShotFolder.listFiles();
			File[] files2 = screenShotsFromZip.listFiles();
			// If this pathname does not denote a directory, then listFiles() returns null.

			for (File file : files) {
				if (file.isFile()) {
					file.delete();
				}
			}

			for (File file : files2) {
				if (file.isFile()) {
					file.delete();
				}
			}

			screenShotsFromZip.delete();
			screenShotFolder.delete();
			html.delete();
		} catch (Exception e) {
		}
	}
}
