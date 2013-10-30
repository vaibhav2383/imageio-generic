package org.codecCentral.imageio.generic;


import java.util.logging.Level;
import java.util.logging.Logger;



public class NativeUtilities {

    private static final Logger LOGGER = Logger
            .getLogger("org.openJpeg.imageio_openjpeg");

    /** is OpenJpeg available on this machine?. */
    private static boolean available;

    private static boolean init = false;
    
/*----------------------------------------------------------------------*/

    private NativeUtilities() {

    }



    /**
     * Returns <code>true</code> if the OpenJpeg native library has been loaded.
     * <code>false</code> otherwise.
     * 
     * @return <code>true</code> only if the native library has been
     *         loaded.
     */
    public static boolean areLibrariesAvailable(String libraryName,String jniLibraryName) {
        loadLibraries(libraryName, jniLibraryName);
        return available;
    }

    /**
     * Forces loading of library libs.
     */
    public synchronized static void loadLibraries(String libraryName,String jniLibraryName) {
        if (init == false)
            init = true;
        else
            return;
        try {
        	if (libraryName != null)
				System.loadLibrary(libraryName);
			if (jniLibraryName != null)
				System.loadLibrary(jniLibraryName);
            available = true;
        } catch (UnsatisfiedLinkError e) {
            if (LOGGER.isLoggable(Level.WARNING)){
            	 LOGGER.warning("Failed to load the native libs. Plugin disabled. " + e.toString());
            }
            available = false;
        }
    }
    
  
}
