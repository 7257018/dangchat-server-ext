
import java.util.ArrayList;
import java.util.Date;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import com.justep.system.context.ContextHelper;
import com.justep.system.data.SQL;
import com.justep.system.data.Table;

//import com.justep.system.data.KSQL;

public class Chat {
	public static final String DATA_MODEL = "/chat/data";

	public static void addLikeMessage(String msgID, String msgContent, String msgType, String msgFileUrl, Integer dialogID, String dialogType,
			String dialogName, String dialogPID, Integer senderID, String senderName, String senderPID, Integer likerID, String likerPID) {
		/*
		String ksql = "insert into LikeMessage LikeMessage"
				+" (LikeMessage.fMsgID,LikeMessage.fMsgContent,LikeMessage.fMsgType,LikeMessage.fMsgFileUrl,LikeMessage.fDialogID,LikeMessage.fDialogType,LikeMessage.fDialogName,LikeMessage.fDialogPID,LikeMessage.fSenderID,LikeMessage.fSenderName,LikeMessage.fSenderPID,LikeMessage.fLikerID,LikeMessage.fLikerPID,LikeMessage.fCreatetime)"
				+" values (:msgID,:msgContent,:msgType,:msgFileUrl,:dialogID,:dialogType,:dialogName,:dialogPID,:senderID,:senderName,:senderPID,:likerID,:likerPID,:createtime)";
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("msgID", msgID);
		variables.put("msgContent", msgContent);
		variables.put("msgType", msgType);
		variables.put("msgFileUrl", msgFileUrl);
		variables.put("dialogID", dialogID);
		variables.put("dialogType", dialogType);
		variables.put("dialogName", dialogName);
		variables.put("dialogPID", dialogPID);
		variables.put("senderID", senderID);
		variables.put("senderName", senderName);
		variables.put("senderPID", senderPID);
		variables.put("likerID", likerID);
		variables.put("likerPID", likerPID);
		variables.put("createtime", new java.sql.Timestamp(new Date().getTime()));
		KSQL.executeUpdate(ksql, variables, DATA_MODEL, null);
		 */
		String sql = "insert into LikeMessage"
				+ "(fMsgID,fMsgContent,fMsgType,fMsgFileUrl,fDialogID,fDialogType,fDialogName,fDialogPID,fSenderID,fSenderName,fSenderPID,fLikerID,fLikerPID,fCreatetime)"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		List<Object> variables = new ArrayList<Object>();
		variables.add(msgID);
		variables.add(msgContent);
		variables.add(msgType);
		variables.add(msgFileUrl);
		variables.add(dialogID);
		variables.add(dialogType);
		variables.add(dialogName);
		variables.add(dialogPID);
		variables.add(senderID);
		variables.add(senderName);
		variables.add(senderPID);
		variables.add(likerID);
		variables.add(likerPID);
		variables.add(new java.sql.Timestamp(new Date().getTime()));
		SQL.executeUpdate(sql, variables, DATA_MODEL);
	}

	public static void removeLikeMessage(String msgID, Integer dialogID, Integer likerID) {
		/*
		String sql = "delete from LikeMessage x where x.fMsgID=:msgID and x.fDialogID=:dialogID and x.fLikerID=:likerID";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("msgID", msgID);
		variables.put("dialogID", dialogID);
		variables.put("likerID", likerID);
		SQL.executeUpdate(ksql, variables, DATA_MODEL, null);
		*/
		String sql = "delete from LikeMessage where fMsgID=? and fDialogID=? and fLikerID=?";
		List<Object> variables = new ArrayList<Object>();
		variables.add(msgID);
		variables.add(dialogID);
		variables.add(likerID);
		SQL.executeUpdate(sql, variables, DATA_MODEL);
	}

	public static Table queryLikeMessage(String filter, Integer limit, Integer offset) {
		Integer count = 0;
		List<Object> vars = new ArrayList<Object>();
		vars.add(ContextHelper.getPerson().getID());
		if(limit!=-1 && offset==0){
			String sql = "select count(*) from LikeMessage where (fLikerPID=?) %s";
			sql = String.format(sql,(null!=filter && !"".equals(filter))?("and("+filter+")"):"");
			Table t = SQL.select(sql, vars, DATA_MODEL);
			count = t.iterator().next().getInt(0);
		}
		String sql = "select * from LikeMessage where (fLikerPID=?) %s order by fCreatetime desc %s";
		String sLimit = "";
		if(limit!=-1){
			sLimit = String.format(" limit %d offset %d",limit,offset);
		}
		sql = String.format(sql,(null!=filter && !"".equals(filter))?("and("+filter+")"):"",sLimit);
		Table ret = SQL.select(sql, vars, DATA_MODEL);
		ret.getProperties().put(Table.PROP_DB_COUNT, count);
		return ret;
	}
}