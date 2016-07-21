package com.justep.im.searcher.parser;

import java.io.IOException;

import com.google.protobuf.CodedInputStream;
import com.justep.im.searcher.message.TextMessage;

/**
 * 普通文本类消息解析器
 * 
 * @author Lining
 * @date 2016-6-6
 */
class TextMessageParser {
	public static TextMessage parseFrom(byte[] body) throws IOException {
		TextMessage message = new TextMessage();
		CodedInputStream in = CodedInputStream.newInstance(body);
		boolean done = false;
		while (!done) {
			int tag = in.readTag();
			switch (tag) {
			case 0:
				done = true;
				break;
			case 10:
				message.setContent(in.readString());
				break;
			default:
				break;
			}
		}
		return message;
	}
}
