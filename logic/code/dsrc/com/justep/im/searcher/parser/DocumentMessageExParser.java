package com.justep.im.searcher.parser;

import java.io.IOException;

import com.google.protobuf.CodedInputStream;
import com.justep.im.searcher.message.DocumentMessageEx;

/**
 * 文档消息扩展解析器类
 * 
 * @author Lining
 * @date 2016-6-15
 */
public class DocumentMessageExParser {

	private int header;
	private byte[] body;

	/**
	 * 解析文档消息扩展
	 * 
	 * @param body
	 * @return
	 * @throws IOException
	 */
	public DocumentMessageEx parseFrom(byte[] body) throws IOException {
		CodedInputStream in = CodedInputStream.newInstance(body);
		// 得到消息头和消息体
		this.getHeaderAndBody(in);
		switch (header) {
		case 1:
			return PhotoMessageExParser.parseFrom(this.body);
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
