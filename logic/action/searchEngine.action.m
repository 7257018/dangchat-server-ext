<?xml version="1.0" encoding="UTF-8"?>
<model xmlns="http://www.justep.com/model">
	<action name="searchTextAction" global="false" procedure="searchTextProcedure">
		<label language="zh_CN">搜索文本</label>
		<public type="String" name="keyword"></public>
		<public type="Integer" name="senderUserId"></public>
		<public type="Integer" name="destUserId"></public>
		<public type="Integer" name="groupId"></public>
		<public type="Integer" name="limit"></public>
	</action>
	<action name="searchDocumentAction" global="false" procedure="searchDocumentProcedure">
		<label language="zh_CN">搜索文档</label>
		<public type="Integer" name="currentUserId"></public>
		<public type="Integer" name="senderUserId"></public>
		<public type="Integer" name="destUserId"></public>
		<public type="Integer" name="groupId"></public>
		<public type="Integer" name="limit"></public>
	</action>
</model>