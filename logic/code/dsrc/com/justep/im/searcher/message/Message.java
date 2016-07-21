package com.justep.im.searcher.message;
//package com.justep.im.searcher;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息基类
 * @author Lining
 * @date 2016-6-6
 */
public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long randomId;
	private Integer senderUserId;
	private String senderUserName;
	private Integer receiverUserId;
	private String receiverUserName;
	private Date sendDatetime;
	private String content;
	
	public Long getRandomId() {
		return this.randomId;
	}
	public void setRandomId(Long randomId) {
		this.randomId = randomId;
	}
	
	public Integer getSenderUserId() {
		return this.senderUserId;
	}
	public void setSenderUserId(Integer senderUserId) {
		this.senderUserId = senderUserId;
	}
	
	public String getSenderUserName() {
		return this.senderUserName;
	}
	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}
	
	public Integer getRecieverUserId() {
		return this.receiverUserId;
	}
	public void setReceiverUserId(Integer receiverUserId) {
		this.receiverUserId = receiverUserId;
	}
	
	public String getRecieverUserName() {
		return this.receiverUserName;
	}
	public void setReceiverUserName(String receiverUserName) {
		this.receiverUserName = receiverUserName;
	}
	
	public Date getSendDatetime() {
		return this.sendDatetime;
	}
	public void setSendDatetime(Date sendDatetime) {
		this.sendDatetime = sendDatetime;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}