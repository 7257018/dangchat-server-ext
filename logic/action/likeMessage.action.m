<?xml version="1.0" encoding="UTF-8" standalone="no"?><model xmlns="http://www.justep.com/model"><action name="queryLikeMessageAction" procedure="queryLikeMessageProcedure">









<public name="filter" type="String"/>
<public name="limit" type="Integer"/>
<public name="offset" type="Integer"/>




<label language="zh_CN">查询关注消息</label>
</action>
<action name="saveLikeMessageAction" procedure="bizSaveProcedure"><permission name="insertRange" type="List"/>
<permission name="deleteRange" type="List"/>
<permission name="updateRange" type="List"/>
<private name="concept" type="String" value="LikeMessage"/>
<private name="dataModel" type="String" value="/chat/data"/>
<private name="fnModel" type="String"/>
<protected name="readOnly" type="String"/>
<protected name="notNull" type="String"/>
<public name="table" required="true" type="Table"/>
</action>
<action name="addLikeMessageAction" global="false" procedure="addLikeMessageProcedure"><label language="zh_CN">关注消息</label>
<public type="String" name="msgID" required="true"></public>
<public type="String" name="msgContent"></public>
<public type="String" name="msgType" required="true"></public>
<public type="String" name="msgFileUrl"></public>
<public type="Integer" name="dialogID" required="true"></public>
<public type="String" name="dialogType" required="true"></public>
<public type="String" name="dialogName"></public>
<public type="String" name="dialogPID"></public>
<public type="Integer" name="senderID" required="true"></public>
<public type="String" name="senderName"></public>
<public type="String" name="senderPID" required="true"></public>
<public type="Integer" name="likerID" required="true"></public>
<public type="String" name="likerPID" required="true"></public>
</action>
<action name="removeLikeMessageAction" global="false" procedure="removeLikeMessageProcedure"><label language="zh_CN">取消关注消息</label>
<public type="String" name="msgID" required="true"></public>
<public type="Integer" name="dialogID" required="true"></public>
<public type="Integer" name="likerID"></public>
</action>
</model>