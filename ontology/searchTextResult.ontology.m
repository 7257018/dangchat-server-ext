<?xml version="1.0" encoding="UTF-8"?>
<model xmlns="http://www.justep.com/model">
<concept name="searchTextResult" keys="rid:destUserId"><has-relation relation="version" default-value-expr="0"><label language="zh_CN">版本</label>
</has-relation>
<label language="zh_CN">IM全文检索结果</label>
<has-relation relation="rid" data-type="String"></has-relation><has-relation relation="senderId" data-type="Integer"></has-relation><has-relation relation="senderName" data-type="String"></has-relation><has-relation relation="destUserId" data-type="Integer"></has-relation><has-relation relation="destUserName" data-type="String"></has-relation><has-relation relation="date" data-type="DateTime"></has-relation>





<has-relation relation="content" data-type="String"></has-relation>
</concept>
<relation name="date" data-type="DateTime"><label language="zh_CN">发送日期时间</label>
</relation>
<relation name="rid" data-type="Integer"><label language="zh_CN">消息ID</label>
</relation>
<relation name="senderId" data-type="Integer"><label language="zh_CN">发送人Id</label>
</relation>
<relation name="senderName" data-type="String"><label language="zh_CN">发送人名称</label>
</relation>
<relation name="destUserId" data-type="Integer"><label language="zh_CN">接收人ID</label>
</relation>
<relation name="destUserName" data-type="String"><label language="zh_CN">接收人名称</label>
</relation>
<relation name="content" data-type="String"><label language="zh_CN">消息内容</label>
</relation>
</model>