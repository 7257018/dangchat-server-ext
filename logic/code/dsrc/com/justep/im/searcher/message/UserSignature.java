package com.justep.im.searcher.message;

/**
 * 用户签名类
 * @author Lining
 * @date 2016-6-13
 */
public class UserSignature {
	private String signature;
	private Integer expire;
	private String baseUrl;
	
	public String getSignature() {
		return this.signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public Integer getExpire() {
		return this.expire;
	}
	public void setExpire(Integer expire) {
		this.expire = expire;
	}
	
	public String getBaseUrl() {
		return this.baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}
