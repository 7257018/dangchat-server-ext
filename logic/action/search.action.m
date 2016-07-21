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
	<action name="searchPhotoAction" global="false" procedure="searchPhotoProcedure">
		<label language="zh_CN">搜索图片</label>
		<public type="Integer" name="senderUserId"></public>
		<public type="Integer" name="destUserId"></public>
		<public type="Integer" name="groupId"></public>
		<public type="Integer" name="limit"></public>		
	</action>
	<action name="searchDocumentAction" global="false" procedure="searchDocumentProcedure">
		<label language="zh_CN">搜索文件</label>
		<public type="Integer" name="senderUserId"></public>
		<public type="Integer" name="destUserId"></public>
		<public type="Integer" name="groupId"></public>
		<public type="Integer" name="limit"></public>		
	</action>
</model>