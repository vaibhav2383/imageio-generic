package org.codecCentral.imageio.generic;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;



public class NativeUtilities {

    private static final Logger LOGGER = Logger.getLogger("org.codecCentral.imageio.generic");
    
    private static Map<String, Boolean> available;
    private static  Map<String, Boolean> initialized;
    
/*----------------------------------------------------------------------*/

    private NativeUtilities() {

    }
    
    static
    {
    	available = new HashMap<String, Boolean>();
    	initialized = new HashMap<String, Boolean>();
    }

    
    private static String GenerateId(List<String> libraries)
    {
    	String rc = "";
    	for (String library : libraries)
    	{
    		rc += library;
    	}
    	return rc;
    }

    private static boolean isTrue(Map<String, Boolean> map, List<String> libraries)
    {
    	String key = GenerateId(libraries);
    	if (map.containsKey(key))
    		return map.get(key);
    	return false;
	
    }
    private static void setTrue(Map<String, Boolean> map, List<String> libraries)
    {
    	String key = GenerateId(libraries);
    	if (!map.containsKey(key))
    		 map.put(key, true);

    }
    /**
     * Returns <code>true</code> if the OpenJpeg native library has been loaded.
     * <code>false</code> otherwise.
     * 
     * @return <code>true</code> only if the native library has been
     *         loaded.
     */
    public synchronized static boolean areLibrariesAvailable(List<String> libraries) {
        loadLibraries(libraries);
        return isTrue(available, libraries);
    }

    /**
     * Forces loading of library libs.
     */
    public synchronized static void loadLibraries(List<String> libraries) {
        if (isTrue(initialized, libraries))
            return;
        setTrue(initialized, libraries);
        try {
        	for (String library : libraries)
        	{
        		if (library != null)
        			System.loadLibrary(library);
        		
        	}
            setTrue(available,libraries);
        } catch (UnsatisfiedLinkError e) {
            if (LOGGER.isLoggable(Level.WARNING)){
            	 LOGGER.warning("Failed to load the native libs. Plugin disabled. " + e.toString());
            }
        }
    }
    
  
}
