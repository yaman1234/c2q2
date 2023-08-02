package utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentManager extent;
    private static ExtentTest test;
    
    public synchronized ExtentManager getInstance() {
        if (extent == null) {
        	// directory where output is to be printed
        	ExtentSparkReporter spark = new ExtentSparkReporter("user/build/name/");
        	extent = new ExtentManager();
        	

        }
        return extent;
    }
	
    public void method() {
    	
    }
    
	
}
