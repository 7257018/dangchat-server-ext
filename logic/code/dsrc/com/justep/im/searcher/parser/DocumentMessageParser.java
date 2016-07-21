package com.justep.im.searcher.parser;

import java.io.IOException;

import com.google.protobuf.CodedInputStream;
import com.justep.im.searcher.message.DocumentMessage;
import com.justep.im.searcher.message.FileMessage;
import com.justep.im.searcher.message.PhotoMessage;

/**
 * 文档消息解析器类
 * @author Lining
 * @date 2016-6-6
 */
class DocumentMessageParser {
	public static DocumentMessage parseFrom(byte[] body) throws IOException {
		DocumentMessage message = new DocumentMessage();
		CodedInputStream in = CodedInputStream.newInstance(body);
		boolean done = false;
		while(!done) {
			int tag = in.readTag();
			switch(tag) {
				case 0:
					done = true;
					break;
				case 8:
					message.setFileId(in.readInt64());
					break;
				case 16:
					message.setAccessHash(in.readInt64());
					break;
				case 24:
					message.setFileSize(in.readInt32());
					break;
				case 34:
					message.setFileName(in.readString());
					break;
				case 42:
					message.setMimeType(in.readString());
					break;
				case 50:
		            int length = in.readRawVarint32();
		            int oldLimit = in.pushLimit(length);
		            message.setFastThumb(new FastThumbParser().parseFrom(in));
		            in.checkLastTagWas(0);
		            in.popLimit(oldLimit);
		            break;
				case 66:
					byte[] bytes = in.readByteArray();
					message.setMessageEx(new DocumentMessageExParser().parseFrom(bytes));
	                break;
				default:
					if (!in.skipField(tag)) {
						done = true;
					}
					break;
			}	
		}
		return getMessage(message);
	}
	
	
	/**
	 * 得到消息
	 * @param message
	 * @return
	 */
	private static DocumentMessage getMessage(DocumentMessage message) {
		//if(message.getMimeType().equals("image/jpeg")) {
		if(message.getFastThumb() != null) {
			PhotoMessage photoMsg = new PhotoMessage();
			cloneMessage(message, photoMsg);
			photoMsg.setMessageEx(message.getMessageEx());
			return photoMsg;
		} else {
			FileMessage fileMsg = new FileMessage();
			cloneMessage(message, fileMsg);
			return fileMsg;
		}
	}
	
	/**
	 * 克隆文档消息
	 * @param source
	 * @param dest
	 */
	private static void cloneMessage(DocumentMessage source, DocumentMessage dest) {
		dest.setFileId(source.getFileId());
		dest.setAccessHash(source.getAccessHash());
		dest.setFileSize(source.getFileSize());
		dest.setFileName(source.getFileName());
		dest.setMimeType(source.getMimeType());
		dest.setFastThumb(source.getFastThumb());
	}
}
