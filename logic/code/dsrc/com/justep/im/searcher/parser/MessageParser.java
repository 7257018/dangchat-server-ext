package com.justep.im.searcher.parser;

import java.io.IOException;

import com.google.protobuf.CodedInputStream;
import com.justep.im.searcher.message.Message;

/**
 * 消息解析器类
 * 
 * @author Lining
 * @date 2016-6-6
 */
public class MessageParser {
	private int header;
	private byte[] body;

	public Message parseFrom(byte[] source) throws IOException {
		CodedInputStream in = CodedInputStream.newInstance(source);
		// 得到消息头和消息体
		this.getHeaderAndBody(in);
		switch (header) {
		case 1:
			// 普通文本消息
			return TextMessageParser.parseFrom(this.body);
		case 3:
			// 文档类型消息（图片或文件）
			return DocumentMessageParser.parseFrom(this.body);
		default:
			return null;
		}
	}

	/**
	 * 得到消息头和消息体
	 * 
	 * @param in
	 *            CodedInputStream类的实例
	 * @throws IOException
	 */
	private void getHeaderAndBody(CodedInputStream in) throws IOException {
		boolean done = false;
		while (!done) {
			int tag = in.readTag();
			switch (tag) {
			case 0:
				done = true;
				break;
			case 8:
				this.header = in.readInt32();
				break;
			case 18:
				this.body = in.readByteArray();
				break;
			default:
				if (!in.skipField(tag)) {
					done = true;
				}
				break;
			}
		}
	}
}
