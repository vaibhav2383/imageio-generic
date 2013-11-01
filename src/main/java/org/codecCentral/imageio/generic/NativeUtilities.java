package org.codecCentral.imageio.generic;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class NativeUtilities {

    private static final Logger LOGGER = Logger.getLogger("org.codecCentral.imageio.generic");
    
    private  boolean available;
    private  boolean initialized;
    
/*----------------------------------------------------------------------*/
    
    public NativeUtilities()
    {
    	
    }

    
    /**
     * Returns <code>true</code> if the OpenJpeg native library has been loaded.
     * <code>false</code> otherwise.
     * 
     * @return <code>true</code> only if the native library has been
     *         loaded.
     */
    public synchronized boolean areLibrariesAvailable(List<String> libraries) {
        loadLibraries(libraries);
        return available;
    }

    /**
     * Forces loading of library libs.
     */
    public synchronized void loadLibraries(List<String> libraries) {
        if (initialized)
            return;
        initialized = true;
        try {
        	for (String library : libraries)
        	{
        		if (library != null)
        			System.loadLibrary(library);
        		
        	}
            available = true;
        } catch (UnsatisfiedLinkError e) {
            if (LOGGER.isLoggable(Level.WARNING)){
            	 LOGGER.warning("Failed to load the native libs. Plugin disabled. " + e.toString());
            }
        }
    }
    
  
}
