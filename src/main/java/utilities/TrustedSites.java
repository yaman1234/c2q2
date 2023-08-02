package utilities;
/*
import java.net.URI;

import com.registry.RegDWORDValue;
import com.registry.RegStringValue;
import com.registry.RegistryKey;
import com.registry.ValueType;

public class TrustedSites {
	static String LocalIntranetZoneKeyPath = "Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings\\Zones\\2";
	//static String LocalIntranetZoneKeyPathForTrusted = "Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings\\ZoneMap\\Ranges"; //Use this code if you are running from Client side
	static String LocalIntranetZoneKeyPathForTrusted = "Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings\\ZoneMap\\EscRanges"; //Use this code if you are running from server side
    static int trustedSiteZone = 0x2;
    static String LogonSettingValueName = "1A00";
    
    private static RegistryKey rootRegKey = null;
    
    private static void initialize() {
    	if(rootRegKey == null)
    		rootRegKey = RegistryKey.getRootKeyForIndex(RegistryKey.HKEY_CURRENT_USER_INDEX);
    }
        
    public static void addToTrustedSite(String url)
    {       
    	initialize();
    	try {
    		URI uri = new URI(url);
    		String scheme = uri.getScheme();
    		String ip = uri.getAuthority(); 	
	    	RegistryKey rangeKey = getRangeKey(ip);
	    	if(rangeKey != null) {
	    		RegDWORDValue value = (RegDWORDValue)rangeKey.newValue(scheme, ValueType.REG_DWORD);
    			value.setValue(trustedSiteZone);
    			
    			RegStringValue value2 = (RegStringValue)rangeKey.newValue(":Range", ValueType.REG_SZ);
    			value2.setValue(ip);
	    	}   
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private static RegistryKey getRangeKey(String ipAddress) {   
    	if(rootRegKey != null) {
	    	for(int i = 1; i<= Integer.MAX_VALUE;i++) {    	
				String key = LocalIntranetZoneKeyPathForTrusted + "\\Range" + i;			
				RegistryKey regSubKey = rootRegKey.getSubKey(key);	
				if(regSubKey == null) {
					regSubKey = rootRegKey.createSubKey(key);				
				}				
				RegStringValue ipKey = (RegStringValue)regSubKey.getValue(":Range");
				if(ipKey == null)
					return regSubKey;
				
				String ip = ipKey.getValue();
				if(ip == null) {
					return regSubKey;
				}	
				else {
					if(ip == ipAddress) {
						return regSubKey;
					}
				}
			}
	    }
    	return null;
    }
    
    public static void setLogonSetting() {    	
    	try
    	{
    		RegistryKey regSubKey =  rootRegKey.getSubKey(LocalIntranetZoneKeyPath);	
	    	if(regSubKey != null) {
	    		int value = LogonSetting.AutomaticallyLogonWithCurrentUsernameAndPassword.getCode();
	    		RegDWORDValue regValue = (RegDWORDValue)regSubKey.getValue(LogonSettingValueName);
	    		if(regValue == null) {	    			
	    			regValue = (RegDWORDValue)regSubKey.newValue(LogonSettingValueName, ValueType.REG_DWORD);
				}
	    		regValue.setValue(value);
	    	}    
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    enum LogonSetting
    {
    	NotSet(-1), 
    	AutomaticallyLogonWithCurrentUsernameAndPassword(0x00000),
    	PromptForUserNameAndPassword(0x10000),
    	AutomaticLogonOnlyInTheIntranetZone(0x20000),
    	AnonymousLogon(0x30000);
    	    	
    	private int code;    	 
    	private LogonSetting(int code) {
            this.code = code;
        }     
        public int getCode() {
            return code;
        }       
    }	
}


*/