<?xml version="1.0" encoding="utf-8"?>

<model xmlns="http://www.justep.com/model" xmlns:m="http://www.justep.com/model">  
  <procedure name="addLikeMessageProcedure" code-model="/chat/logic/code" code="Chat.addLikeMessage"> 
    <parameter name="msgID" type="String"/>  
    <parameter name="msgContent" type="String"/>  
    <parameter name="msgType" type="String"/>  
    <parameter name="msgFileUrl" type="String"/>  
    <parameter name="dialogID" type="Integer"/>  
    <parameter name="dialogType" type="String"/>  
    <parameter name="dialogName" type="String"/>  
    <parameter name="dialogPID" type="String"/>  
    <parameter name="senderID" type="Integer"/>  
    <parameter name="senderName" type="String"/>  
    <parameter name="senderPID" type="String"/>  
    <parameter name="likerID" type="Integer"/>  
    <parameter name="likerPID" type="String"/> 
  </procedure>  
  <procedure name="removeLikeMessageProcedure" code-model="/chat/logic/code" code="Chat.removeLikeMessage"> 
    <parameter name="msgID" type="String"/>  
    <parameter name="dialogID" type="Integer"/>  
    <parameter name="likerID" type="Integer"/> 
  </procedure>  
  <procedure name="queryLikeMessageProcedure" code-model="/chat/logic/code" code="Chat.queryLikeMessage"> 
    <parameter name="filter" type="String"/>
    <parameter name="limit" type="Integer"/>
    <parameter name="offset" type="Integer"/>
  </procedure> 
</model>
