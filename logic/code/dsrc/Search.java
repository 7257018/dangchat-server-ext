import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 搜索Action
 * @author Lining
 * @date 2016-5-19
 */
public class Search {

	/**
	 * 文本搜索
	 * @param keyword 关键字
	 * @param senderUserId 发送人Id
	 * @param destUserId 接收人Id
	 * @param groupId 群组Id
	 * @param limit 获取的数据条数
	 * @return 包含查询结果的JSON数组
	 */
	public static JSONArray searchText(String keyword, Integer senderUserId, Integer destUserId, Integer groupId, Integer limit) {
		//message_content_header: 1-文本；2-系统提示信息（如XXX退出群组）；3-图片和文件
		String sql = getSearchTextSql(keyword, senderUserId, destUserId, groupId, limit);
		Connection conn = null;
		Statement stmt = null;
		try {
		    Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    DataSource ds = (DataSource)envContext.lookup("im");
		    conn = ds.getConnection();
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			return getSearchTextResult(result);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (stmt != null){
					stmt.close();
				}
			} catch (Exception e) {}
			try {
				if (conn != null){
					conn.close();
				}
			} catch (Exception e) {}
		}
	}
	
	/**
	 * 得到搜索文本的SQL语句
	 * @param keyword 搜索关键字
	 * @param senderUserId 发送人Id
	 * @param destUserId 接收人Id
	 * @param groupId 群组Id
	 * @param limit 获取的数据条数
	 * @return 搜索文本的SQL语句
	 */
	private static String getSearchTextSql(String keyword, Integer senderUserId, Integer destUserId, Integer groupId, Integer limit) {
		String sql = "";
		if(groupId == null || groupId.equals(0)) {
			sql = "select distinct on (msg.date, msg.random_id) msg.date,msg.sender_user_id,u.name as sender_user_name," +
					"msg.user_id as dest_user_id,u1.name as dest_user_name,msg.random_id,msg.message_content_data" + 
					" from history_messages msg inner join users u on msg.sender_user_id=u.\"id\"" +
					" inner join users u1 on msg.user_id=u1.\"id\"" +
					" where msg.message_content_header=1 and msg.user_id=" + destUserId + " and msg.sender_user_id=" + senderUserId + 
					" and msg.deleted_at is null " + " and msg.message_content_data like '%" + keyword + "%'" +
					" order by msg.date asc, msg.random_id asc " + "limit " + limit;			
		} else {
			sql = "select distinct on (msg.date, msg.random_id) msg.date,msg.sender_user_id,u.name as sender_user_name," +
					"0 as dest_user_id,'' as dest_user_name,msg.random_id,msg.message_content_data" + 
					" from history_messages msg inner join users u on msg.sender_user_id=u.\"id\"" +
					" where msg.message_content_header=1 and msg.peer_id=" + groupId + 
					" and msg.deleted_at is null " + " and msg.message_content_data like '%" + keyword + "%'" +
					" order by msg.date asc, msg.random_id asc " + "limit " + limit;			
		}
		return sql;
	}
	
	/**
	 * 得到文本搜索结果
	 * @param result 查询结果
	 * @return
	 * @throws Exception
	 */
	private static JSONArray getSearchTextResult(ResultSet result) throws Exception {
		JSONArray jArray = new JSONArray();
		while(result.next()) {
			JSONObject row = new JSONObject();
			row.put("rid", result.getLong("random_id"));
			row.put("senderId", result.getInt("sender_user_id"));
			row.put("senderName", result.getString("sender_user_name"));
			row.put("destUserId", result.getInt("dest_user_id"));
			row.put("destUserName", result.getString("dest_user_name"));				
			row.put("date", result.getTimestamp("date").toString());
			byte[] bs = result.getBytes("message_content_data");
			byte[] bs1 = new byte[bs.length - 6];
			for(int i = 6; i < bs.length; i++) {
				bs1[i - 6] = bs[i];
			}
			row.put("content", dealString4JSON(new String(bs1, "utf-8")));
			jArray.add(row);
		}
		return jArray;		
	}
	
	/**
	 * 将JSON字符串处理成javascript可以识别的
	 * 
	 * @param ors
	 * @return
	 */
	private static String dealString4JSON(String ors) {
		ors = ors == null ? "" : ors;
		StringBuffer buffer = new StringBuffer(ors);
		// /在替换的时候不要使用 String.replaceAll("\\","\\\\"); 这样不会达到替换的效果
		// 因为这些符号有特殊的意义,在正则 ///表达式里要用到
		int i = 0;
		while (i < buffer.length()) {
			if (buffer.charAt(i) == '\'' || buffer.charAt(i) == '\\') {
				buffer.insert(i, '\\');
				i += 2;
			} else {
				i++;
			}
		}
		return buffer.toString().replaceAll("\r", "").replaceAll("\n", "");
	}	
	
	/**
	 * 图片搜索 
	 * @param senderUserId 发送人Id
	 * @param destUserId 接收人Id
	 * @param groupId 群组Id
	 * @param limit 获取的数据条数
	 * @return 包含查询结果的JSON数组
	 */
	public static JSONArray searchPhoto(Integer senderUserId, Integer destUserId, Integer groupId, Integer limit) {
		String sql = "select distinct on (msg.date, msg.random_id) msg.date,msg.sender_user_id,u.name as sender_user_name," +
				"msg.user_id as dest_user_id,u1.name as dest_user_name,msg.random_id,msg.message_content_data" + 
				" from history_messages msg inner join users u on msg.sender_user_id=u.\"id\"" +
				" inner join users u1 on msg.user_id=u1.\"id\"" +
				" where msg.message_content_header=3 and msg.user_id=" + destUserId + " and sender_user_id=" + senderUserId + 
				" and msg.deleted_at is null and random_id=-5859271756607806870 " +
				" order by msg.date asc, msg.random_id asc " + "limit " + limit;
		Connection conn = null;
		Statement stmt = null;
		try {
		    Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    DataSource ds = (DataSource)envContext.lookup("im");
		    conn = ds.getConnection();
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			return getSearchPhotoResult(result);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (stmt != null){
					stmt.close();
				}
			} catch (Exception e) {}
			try {
				if (conn != null){
					conn.close();
				}
			} catch (Exception e) {}
		}
	}	
	
	/**
	 * 得到图片搜索结果
	 * @param result 查询结果
	 * @return
	 * @throws Exception
	 */
	private static JSONArray getSearchPhotoResult(ResultSet result) throws Exception {
		JSONArray jArray = new JSONArray();
		while(result.next()) {
			JSONObject row = new JSONObject();
			row.put("rid", result.getLong("random_id"));
			row.put("senderId", result.getInt("sender_user_id"));
			row.put("senderName", result.getString("sender_user_name"));
			row.put("destUserId", result.getInt("dest_user_id"));
			row.put("destUserName", result.getString("dest_user_name"));				
			row.put("date", result.getTimestamp("date").toString());
			byte[] bs = result.getBytes("message_content_data");
			byte[] bs1 = new byte[bs.length - 6];
			for(int i = 6; i < bs.length; i++) {
				bs1[i - 6] = bs[i];
			}
			row.put("content", new String(bs1, "utf-8"));
			jArray.add(row);
		}
		return jArray;		
	}

}