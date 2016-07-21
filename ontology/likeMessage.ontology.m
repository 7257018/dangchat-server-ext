<?xml version="1.0" encoding="UTF-8"?>
<model xmlns="http://www.justep.com/model">
<concept name="LikeMessage" keys="fMsgID:fDialogID:fLikerID"><has-relation relation="version" default-value-expr="0"><label language="zh_CN">版本</label>
</has-relation>
<label language="zh_CN">IM消息关注</label>
<has-relation relation="fMsgID" data-type="Integer"></has-relation>
<has-relation relation="fMsgContent" data-type="String"></has-relation>
<has-relation relation="fMsgType" data-type="String"></has-relation>
<has-relation relation="fMsgFileUrl" data-type="String"></has-relation>
<has-relation relation="fDialogID" data-type="Integer"></has-relation>
<has-relation relation="fDialogType" data-type="String"></has-relation>
<has-relation relation="fDialogName" data-type="String"></has-relation>
<has-relation relation="fDialogPID" data-type="String"></has-relation>
<has-relation relation="fSenderID" data-type="Integer"></has-relation>
<has-relation relation="fSenderName" data-type="String"></has-relation>
<has-relation relation="fSenderPID" data-type="String"></has-relation>
<has-relation relation="fLikerID" data-type="Integer"></has-relation>
<has-relation relation="fLikerPID" data-type="String"></has-relation>
<has-relation relation="fCreatetime" data-type="DateTime"></has-relation>
</concept>
<relation name="fMsgID" data-type="Integer"><label language="zh_CN">消息ID</label>
</relation>
<relation name="fMsgContent" data-type="String"><label language="zh_CN">消息内容</label>
</relation>
<relation name="fMsgType" data-type="String"><label language="zh_CN">消息类型</label>
</relation>
<relation name="fMsgFileUrl" data-type="String"><label language="zh_CN">消息文件URL</label>
</relation>
<relation name="fDialogID" data-type="Integer"><label language="zh_CN">会话ID</label>
</relation>
<relation name="fDialogType" data-type="String"><label language="zh_CN">会话类型</label>
</relation>
<relation name="fDialogName" data-type="String"><label language="zh_CN">会话名称</label>
</relation>
<relation name="fDialogPID" data-type="String"><label language="zh_CN">会话人员ID</label>
</relation>
<relation name="fSenderID" data-type="Integer"><label language="zh_CN">消息发送人ID</label>
</relation>
<relation name="fSenderName" data-type="String"><label language="zh_CN">消息发送人名称</label>
</relation>
<relation name="fSenderPID" data-type="String"><label language="zh_CN">消息发送人人员ID</label>
</relation>
<relation name="fLikerID" data-type="Integer"><label language="zh_CN">关注人ID</label>
</relation>
<relation name="fLikerPID" data-type="String"><label language="zh_CN">关注人人员ID</label>
</relation>
<relation name="fCreatetime" data-type="DateTime"><label language="zh_CN">关注时间</label>
</relation>
</model>