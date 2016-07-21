package com.justep.im.searcher.message;

/**
 * 缩略图类
 * @author Lining
 * @date 2016-6-6
 */
public class FastThumb {
	private int height = 0;
	private int width = 0;
	private byte[] thumb;
	
	public int getHeight() {
		return this.height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return this.width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public byte[] getThumb() {
		return this.thumb;
	}
	public void setThum(byte[] thumb) {
		this.thumb = thumb;
	}
}
