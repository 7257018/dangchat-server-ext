<?xml version="1.0" encoding="utf-8"?>

<model xmlns="http://www.justep.com/model" xmlns:m="http://www.justep.com/model">
	<procedure name="searchTextProcedure" code-model="/chat/logic/code"
		code="SearchEngine.searchText">
		<parameter name="keyword" type="String" />
		<parameter name="senderUserId" type="Integer" />
		<parameter name="destUserId" type="Integer" />
		<parameter name="groupId" type="Integer" />
		<parameter name="limit" type="Integer" />
	</procedure>
	<procedure name="searchDocumentProcedure" code-model="/chat/logic/code"
		code="SearchEngine.searchDocument">
		<parameter name="currentUserId" type="Integer" />
		<parameter name="senderUserId" type="Integer" />
		<parameter name="destUserId" type="Integer" />
		<parameter name="groupId" type="Integer" />
		<parameter name="limit" type="Integer" />
	</procedure>
</model>
