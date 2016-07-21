<?xml version="1.0" encoding="UTF-8"?>
<model xmlns="http://www.justep.com/model">
	<process name="imProcess" kind="system">
		<label language="zh_CN">im调用</label>

		<static-activity name="imActivity" />
		<static-activity name="searchActivity" />

		<has-action action="queryLikeMessageAction"
			access-permission="public"></has-action>
		<has-action action="addLikeMessageAction"
			access-permission="public"></has-action>
		<has-action action="removeLikeMessageAction"
			access-permission="public"></has-action>
		<has-action action="searchTextAction" access-permission="public"></has-action>
		<has-action action="searchDocumentAction" access-permission="public"></has-action>
	</process>
</model>
