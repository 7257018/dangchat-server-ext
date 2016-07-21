//package com.justep.im.searcher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.justep.im.searcher.parser.MessageParser;
import com.justep.im.searcher.message.Message;
import com.justep.im.searcher.message.FileMessage;
import com.justep.im.searcher.message.DocumentMessage;
import com.justep.im.searcher.message.PhotoMessage;
import com.justep.im.searcher.message.PhotoMessageEx;
import com.justep.im.searcher.message.TextMessage;
import com.justep.im.searcher.message.UserSignature;


/**
 * 搜索Action
 * 
 * @author Lining
 * @date 2016-5-19
 */
public class SearchEngine {

	//private static final String DATA_MODEL = "/chat/data";
	
	/**
	 * 文本搜索
	 * 
	 * @param keyword
	 *            关键字
	 * @param senderUserId
	 *            发送人Id
	 * @param destUserId
	 *            接收人Id
	 * @param groupId
	 *            群组Id
	 * @param limit
	 *            获取的数据条数
	 * @return 包含查询结果的JSON数组
	 */
	public static JSONArray searchText(String keyword, Integer senderUserId, Integer destUserId, Integer groupId, Integer limit) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// message_content_header: 1-文本；2-系统提示信息（如XXX退出群组）；3-图片和文件
			String sql = getSearchTextSql(keyword, senderUserId, destUserId, groupId, limit);
			conn = getConnection();
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			return getSearchResult(null, result);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * 文档搜索（包括图片和文件）
	 * @param currentUserId	当前用户Id
	 * @param senderUserId	发送人Id
	 * @param destUserId	接收人Id
	 * @param groupId		群组Id
	 * @param limit			获取的数据条数
	 * @return				包含查询结果的JSON数组
	 */
	public static JSONArray searchDocument(Integer currentUserId, Integer senderUserId, Integer destUserId, Integer groupId, Integer limit) {
		Connection conn = null;
		Statement stmt = null;
		try {
			String sql = getSearchDocumentSql(senderUserId, destUserId, groupId, limit);
			conn = getConnection();
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			
			//得到用户签名信息
			UserSignature signature = getUserSignature(currentUserId, conn);
			return getSearchResult(signature, result);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 得到搜索文本的SQL语句
	 * 
	 * @param keyword
	 *            搜索关键字
	 * @param senderUserId
	 *            发送人Id
	 * @param destUserId
	 *            接收人Id
	 * @param groupId
	 *            群组Id
	 * @param limit
	 *            获取的数据条数
	 * @return 搜索文本的SQL语句
	 */
	private static String getSearchTextSql(String keyword, Integer senderUserId, Integer destUserId, Integer groupId, Integer limit) {
		String sql = "";
		if (groupId == null || groupId.equals(0)) {
			sql = "SELECT DISTINCT ON (msg.date, msg.random_id) msg.date,msg.sender_user_id,u.name as sender_user_name,"
					+ "msg.user_id as receiver_user_id,u1.name as receiver_user_name,msg.random_id,msg.message_content_data" + " FROM history_messages msg INNER JOIN users u ON msg.sender_user_id=u.\"id\""
					+ " INNER JOIN users u1 ON msg.user_id=u1.\"id\"" + " WHERE msg.message_content_header=1 and msg.user_id IN(" + destUserId + "," + senderUserId + ") AND msg.sender_user_id IN(" + destUserId + "," + senderUserId + ")"
					+ " AND msg.deleted_at IS NULL " + " AND msg.message_content_data LIKE '%" + keyword + "%'" + " ORDER BY msg.date ASC, msg.random_id ASC " + "LIMIT " + limit;
		} else {
			sql = "SELECT DISTINCT ON (msg.date, msg.random_id) msg.date,msg.sender_user_id,u.name as sender_user_name,"
					+ "0 as receiver_user_id,'' AS receiver_user_name,msg.random_id,msg.message_content_data" + " FROM history_messages msg INNER JOIN users u ON msg.sender_user_id=u.\"id\""
					+ " WHERE msg.message_content_header=1 AND msg.peer_id=" + groupId + " AND msg.deleted_at IS NULL " + " AND msg.message_content_data LIKE '%" + keyword + "%'"
					+ " ORDER BY msg.date ASC, msg.random_id ASC LIMIT " + limit;
		}
		return sql;
	}

	/**
	 * 得到搜索文档（图片和文件）的SQL语句
	 * @param senderUserId	发送人Id
	 * @param destUserId	接收人Id
	 * @param groupId		群组Id
	 * @param limit			获取的数据条数
	 * @return				搜索文档的SQL语句
	 */
	private static String getSearchDocumentSql(Integer senderUserId, Integer destUserId, Integer groupId, Integer limit) {
		String sql = "";
		if (groupId == null || groupId.equals(0)) {
			sql = "SELECT DISTINCT ON (msg.date, msg.random_id) msg.date,msg.sender_user_id,u.name as sender_user_name,"
					+ "msg.user_id as receiver_user_id,u1.name as receiver_user_name,msg.random_id,msg.message_content_data" + " FROM history_messages msg INNER JOIN users u ON msg.sender_user_id=u.\"id\""
					+ " INNER JOIN users u1 ON msg.user_id=u1.\"id\"" + " WHERE msg.message_content_header=3 AND msg.user_id IN(" + destUserId + "," + senderUserId + ") AND sender_user_id IN(" + destUserId + "," + senderUserId + ")"
					+ " AND msg.deleted_at IS NULL " + " ORDER BY msg.date ASC, msg.random_id ASC " + "LIMIT " + limit;
		} else {
			sql = "SELECT DISTINCT ON (msg.date, msg.random_id) msg.date,msg.sender_user_id,u.name as sender_user_name,"
					+ "0 as receiver_user_id,'' AS receiver_user_name,msg.random_id,msg.message_content_data" + " FROM history_messages msg INNER JOIN users u ON msg.sender_user_id=u.\"id\""
					+ " WHERE msg.message_content_header=3 AND msg.peer_id=" + groupId + " AND msg.deleted_at IS NULL"
					+ " ORDER BY msg.date ASC LIMIT " + limit;
		}
		return sql;
	}
	
	/**
	 * 得到用户签名
	 * @param userId		用户Id
	 * @param connection	数据库连接
	 * @return				UserSignature类的实例
	 * @throws SQLException
	 */
	private static UserSignature getUserSignature(Integer userId, Connection connection) throws SQLException {
		String sql = "SELECT signature,expire,base_url FROM user_signatures WHERE user_id=?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			UserSignature us = new UserSignature();
			us.setSignature(javax.xml.bind.DatatypeConverter.printBase64Binary(rs.getBytes("signature")));
			us.setExpire(rs.getInt("expire"));
			us.setBaseUrl(rs.getString("base_url"));
			return us;
		} else {
			return null;
		}
	}

	/**
	 * 得到数据库连接
	 * 
	 * @return
	 * @throws NamingException 
	 * @throws SQLException 
	 */
	private static Connection getConnection() throws NamingException, SQLException {
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource) envContext.lookup("im");
		return ds.getConnection();
	}

	/**
	 * 得到搜索结果
	 * 
	 * @param userSignature
	 * 			     用户签名
	 * @param result
	 *            查询结果
	 * @return
	 * @throws Exception
	 */
	private static JSONArray getSearchResult(UserSignature userSignature, ResultSet result) throws Exception {
		JSONArray jArray = new JSONArray();
		MessageParser mp = new MessageParser();		
		while (result.next()) {
			Message message = mp.parseFrom(result.getBytes("message_content_data"));
			if(message instanceof DocumentMessage) {
				if(((DocumentMessage)message).getMimeType().equals("audio/mp3")) {
					continue;
				} else {
					System.out.println(((DocumentMessage)message).getMimeType());
				}
			}
			JSONObject json = new JSONObject();
			json.put("rid", result.getLong("random_id"));
			json.put("receiverUserId", result.getInt("receiver_user_id"));
			json.put("receiverUserName", result.getString("receiver_user_name"));
			json.put("date", result.getTimestamp("date").toString());
			json.put("senderUserId", result.getInt("sender_user_id"));
			json.put("senderUserName", result.getString("sender_user_name"));
			if(message instanceof TextMessage) {
				json.put("content", message.getContent());
			}
			
			if(message instanceof DocumentMessage) {
				DocumentMessage docMessage = (DocumentMessage)message;
				json.put("fileId", docMessage.getFileId());
				json.put("fileName", docMessage.getFileName());
				json.put("fileSize", docMessage.getFileSize());
				if(userSignature != null) {
					json.put("fileUrl",userSignature.getBaseUrl() + "/" + docMessage.getFileId() + "/" + docMessage.getFileName() + "?signature=" + 
							userSignature.getSignature().replace("=", "") + "&expires=" + userSignature.getExpire());
				}
				json.put("type", message instanceof FileMessage ? "file" : "photo");
				if(message instanceof PhotoMessage) {
					String photoContent = javax.xml.bind.DatatypeConverter.printBase64Binary(docMessage.getFastThumb().getThumb());
					photoContent = "<img src='data:image/jpg;base64," + photoContent + "' class='img-rounded'/>";
					json.put("content", photoContent);
					PhotoMessageEx photoMessageEx = (PhotoMessageEx)docMessage.getMessageEx();
					json.put("height", photoMessageEx.getHeight());
					json.put("width", photoMessageEx.getWidth());
					json.put("thumbHeight", docMessage.getFastThumb().getHeight());
					json.put("thumbWidth", docMessage.getFastThumb().getWidth());
				} else {
					String fileContent = "<a class='fileMsg'><i class='iconfont icon-wenjianjia x-fileIcon'/>" + docMessage.getFileName() + "</a>";
					json.put("content", fileContent);
				}
			}
			
			jArray.add(json);
		}
		return jArray;
	}
	
	/**
	 * 设置消息属性
	 * @param message
	 * @param result
	 * @throws SQLException
	 */
/*	private static void setMessageAttribute(Message message, ResultSet result) throws SQLException {
		message.setRandomId(result.getLong("random_id"));
		message.setReceiverUserId(result.getInt("receiver_user_id"));
		message.setReceiverUserName(result.getString("receiver_user_name"));
		message.setSendDatetime(result.getTimestamp("date"));
		message.setSenderUserId(result.getInt("sender_user_id"));
		message.setSenderUserName(result.getString("sender_user_name"));
	}*/
}