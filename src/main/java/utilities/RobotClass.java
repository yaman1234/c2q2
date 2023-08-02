/**
 * This class uses clipboard and pastes the values at the active cursor
 */
package utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

/**
 * @author sanish
 *
 */
public class RobotClass {

	// Storing OS clip-board as a variable
	static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	static StringSelection selection = null;

	/**
	 * Copy pastes values one by one using key strokes of physical keyboard
	 * 
	 * @param values
	 * @throws InterruptedException
	 */
	public static void copyPaste(String... values) throws InterruptedException {

		int index = 1, size = values.length;
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String value : values) {
			selection = new StringSelection(value);
			clipboard.setContents(selection, selection);
			Thread.sleep(50);

			// performing keystroke press from physical keyboard
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(50);

			if (index < size) {
				// press tab until it's not the last input field
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				index++;
			} else {
				// press enter after the last input field
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
			}
			Thread.sleep(50);
		}
	}

}
