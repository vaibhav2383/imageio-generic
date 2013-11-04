package org.codecCentral.imageio.generic;

import java.io.IOException;

import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;

public abstract class GenericImageWriter extends ImageWriter{

    protected ImageOutputStream outputStream = null;
	protected EncoderBase encoder;
	
	protected GenericImageWriter(ImageWriterSpi originatingProvider) {
		super(originatingProvider);
		// TODO Auto-generated constructor stub
	}
	
    /**
     * Write back the compressed JP2 file to the provided output stream.
     * @throws IOException
     */
    protected void writeOnStream() throws IOException {
    	if (outputStream == null)
    		return;
    	int size = (int)encoder.getCompressedStreamLength()/(encoder.getDepth());
    	outputStream.write(encoder.getCompressedStream(), 0, size);
        
    }



}
