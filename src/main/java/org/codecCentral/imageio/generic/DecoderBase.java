package org.codecCentral.imageio.generic;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public abstract class DecoderBase extends DecoderEncoderBase {
	
	public interface IJavaJ2KDecoderLogger {
		public void logDecoderMessage(String message);

		public void logDecoderError(String message);
	}
	

	private Vector<IJavaJ2KDecoderLogger> loggers = new Vector<IJavaJ2KDecoderLogger>();


	/** This method is called either directly or by the C methods */
	public void logMessage(String message) {
		for (IJavaJ2KDecoderLogger logger : loggers)
			logger.logDecoderMessage(message);
	}

	/** This method is called either directly or by the C methods */
	public void logError(String error) {
		for (IJavaJ2KDecoderLogger logger : loggers)
			logger.logDecoderError(error);
	}
	
	public void addLogger(IJavaJ2KDecoderLogger messagesAndErrorsLogger) {
		loggers.addElement(messagesAndErrorsLogger);
	}

	public void removeLogger(IJavaJ2KDecoderLogger messagesAndErrorsLogger) {
		loggers.removeElement(messagesAndErrorsLogger);
	}
	
	private long[] segmentPositions;
	private long[] segmentLengths;


	public void SetSegmentPositions(long[] positions)
	{
		segmentPositions = positions;
	}

	public void SetSegmentLengths(long[] lengths)
	{
		segmentLengths = lengths;
	}
	
	private static boolean DEBUG_DECOMPRESS_FROM_BUFFER = false;


	// ===== decompression parameters =============>
	/*
	 * These value may be changed for each image
	 */
	protected String[] decoder_arguments = null;
	
	
	/** Contains all the decoding arguments other than the input/output file */
	public void setDecoderArguments(String[] argumentsForTheDecoder) {
		decoder_arguments = argumentsForTheDecoder;
	}

	public void decode(String fname) {
		String[] args = null;
		reset();
		if (fname != null)
		{
			args = new String[1];
			if (DEBUG_DECOMPRESS_FROM_BUFFER) {
				try {
					compressedStream = getBytesFromFile(new File(fname));
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}

			} else {
				args[0] = fname;
			}			
		}
		decoder_arguments = args;
		internalDecode(convertArguments(decoder_arguments));;
	}

	// Returns the contents of the file in a byte array.
	private static byte[] getBytesFromFile(File file) throws IOException {
		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
			throw new IOException("File is too large!");
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;

		InputStream is = new FileInputStream(file);
		try {
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
		} finally {
			is.close();
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		return bytes;
	}
	
	
	void alloc8() {
		if ((image8 == null || (image8 != null && image8.length != width
				* height))
				&& (getDepth() == 1)) {
			image8 = new byte[width * height];
			logMessage("Decoder.alloc8: image8 length = "
					+ image8.length + " (" + width + " x " + height + ") ");
		}
	}

	void alloc16() {
		if ((image16 == null || (image16 != null && image16.length != width
				* height))
				&& (getDepth() == 2)) {
			image16 = new short[width * height];
			logMessage("Decoder.alloc16: image16 length = "
					+ image16.length + " (" + width + " x " + height + ") ");
		}
	}

	void alloc24() {
		if ((image24 == null || image24.length != width * height) &&
				getDepth() == 3)
		{
			image24 = new int[width * height];
			logMessage("Decoder.alloc24: image24 length = "
					+ image24.length + " (" + width + " x " + height + ") ");
		}
	}
	

	public boolean canDecode(String fname)
	{
		String[] args = new String[]{fname};
		return  internalGetFormat(convertArguments(args)) == 0;
	}
	public boolean canDecode(byte[] buffer)
	{
		compressedStream = buffer;
		boolean canDecode =   internalGetFormat(convertArguments(new String[]{})) == 0;
		compressedStream = null;
		return canDecode;
	}

	
	protected abstract int internalDecode(String[] parameters);
	protected abstract int internalGetFormat(String[] parameters);
}
