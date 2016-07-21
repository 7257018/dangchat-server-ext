package com.justep.im.searcher.message;

/**
 * 图片消息扩展类
 * @author Lining
 * @date 2016-6-15
 */
public class PhotoMessageEx extends DocumentMessageEx {

	private Integer height;
	private Integer width;
	
	
	public Integer getHeight() {
		return this.height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public Integer getWidth() {
		return this.width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}	
	
}
