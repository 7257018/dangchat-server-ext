package com.justep.im.searcher.parser;

import java.io.IOException;

import com.google.protobuf.CodedInputStream;
import com.justep.im.searcher.message.PhotoMessageEx;

/**
 * 图片消息扩展解析类
 * @author Lining
 * @date 2016-6-15
 */
class PhotoMessageExParser {

	public static PhotoMessageEx parseFrom(byte[] body) throws IOException {
		PhotoMessageEx messageEx = new PhotoMessageEx();
		CodedInputStream in = CodedInputStream.newInstance(body);
		boolean done = false;
		while (!done) {
			int tag = in.readTag();
			switch (tag) {
			case 0:
				done = true;
				break;
			case 8:
				messageEx.setWidth(in.readInt32());
				break;
			case 16:
				messageEx.setHeight(in.readInt32());
				break;
			default:
				if (!in.skipField(tag)) {
					done = true;
				}
				break;			
			}
		}
		return messageEx;
	}
	
}
