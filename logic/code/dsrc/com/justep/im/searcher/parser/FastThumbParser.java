package com.justep.im.searcher.parser;

import java.io.IOException;

import com.google.protobuf.CodedInputStream;
import com.justep.im.searcher.message.FastThumb;

/**
 * 缩略图API类
 * @author Lining
 * @date 2016-6-6
 */
class FastThumbParser {
	public FastThumb parseFrom(CodedInputStream in) throws IOException {
		boolean done = false;
		FastThumb thumbMsg = new FastThumb();
		while (!done) {
			int tag = in.readTag();
			switch (tag) {
			case 0:
				done = true;
				break;
			case 8:
				thumbMsg.setWidth(in.readInt32());
				break;
			case 16:
				thumbMsg.setHeight(in.readInt32());
				break;
			case 26:
				thumbMsg.setThum(in.readByteArray());
				break;
			default:
				if (!in.skipField(tag)) {
					done = true;
				}
				break;
			}
		}
		return thumbMsg;
	}
}
