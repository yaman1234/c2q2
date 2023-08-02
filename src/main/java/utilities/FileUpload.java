/**
 * 
 */
package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 * @author sanish
 *
 */
public class FileUpload {

	/**
	 * Upload file by copy-pasting entire file path into the windows modal dialogue
	 * box using Robot
	 * 
	 * @param filePath
	 */
	public static void uploadUsingRobot(String filePath) {
		try {
			RobotClass.copyPaste(filePath);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Upload file by copy-pasting entire file path into the windows modal dialogue
	 * box using AutoIT script
	 */
	public static void uploadUsingScript() {
		Desktop desk = Desktop.getDesktop();
		File f = new File(System.getProperty("user.dir") + "\\auth\\fileUpload.exe");
		try {
			desk.open(f);
		} catch (IOException e) {
			System.err.println("Error occurred opening file located " + f);
			e.printStackTrace();
		}
	}
}
