package utilities;

/**
 * This class will calculate startTime and endTime to find out the PageLoad Time
 * @author prabin
 *
 */
public class ResponseTime extends UtilBase
{
	
	private long startTime;
	
	/**
	 * StartTimer
	 */
	public void startTimer()
	{
		this.startTime = System.currentTimeMillis();
	}
	
	/**
	 * Calculate end time and return total pageLoad time in seconds
	 * @return
	 */
	public double totalTime()
	{
		return (System.currentTimeMillis()- this.startTime)/1000.0;
	}
	

}
