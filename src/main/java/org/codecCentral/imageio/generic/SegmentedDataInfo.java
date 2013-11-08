package org.codecCentral.imageio.generic;

public class SegmentedDataInfo {
	
	public SegmentedDataInfo(String fileName, long[] segmentOffsets, long[] segmentLengths)
	{
		this.fileName = fileName;
		this.segmentOffsets = segmentOffsets;
		this.segmentLengths = segmentLengths;
		frame = -1;
		numFrames = -1;
	}
	
	public String fileName;
	public long[] segmentOffsets;
	public long[] segmentLengths;
	public long frame;
	public long numFrames;

}
