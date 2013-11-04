package org.codecCentral.imageio.generic;

public abstract class EncoderBase extends DecoderEncoderBase {
	
	/**
	 * Holds the compressed stream length, which may be smaller than
	 * compressedStream.length if this byte[] is pre-allocated
	 */
	protected long compressedStreamLength = -1;
	
	protected abstract long internalEncode(String[] parameters);
	
	public abstract void encode();
	
	public long getCompressedStreamLength() {
		return compressedStreamLength;
	}
	
}
