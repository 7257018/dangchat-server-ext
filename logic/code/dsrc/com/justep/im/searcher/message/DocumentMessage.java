package com.justep.im.searcher.message;

/**
 * 文档消息类（图片、文件等）
 * @author Lining
 * @date 2016-6-6
 */
public class DocumentMessage extends Message {
	private Long fileId;
	private String fileName;
	private String mimeType;
	private Integer fileSize;
	private String fileUrl;
	private String preview;
	private Long accessHash;
	private FastThumb thumb;
	private DocumentMessageEx messageEx;
	
	public Long getFileId() {
		return this.fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getMimeType() {
		return this.mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public Integer getFileSize() {
		return this.fileSize;
	}
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getFileUrl() {
		return this.fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	public String getPreview() {
		return this.preview;
	}
	public void setPreview(String preview) {
		this.preview = preview;
	}
	
	public Long getAccessHash() {
		return this.accessHash;
	}
	public void setAccessHash(Long accessHash) {
		this.accessHash = accessHash;
	}
	
	public FastThumb getFastThumb() {
		return this.thumb;
	}
	public void setFastThumb(FastThumb thumb) {
		this.thumb = thumb;
	}
	
	public DocumentMessageEx getMessageEx() {
		return this.messageEx;
	}
	public void setMessageEx(DocumentMessageEx messageEx) {
		this.messageEx = messageEx;
	}
}
