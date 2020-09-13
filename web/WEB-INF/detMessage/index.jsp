<%@ page import="entity.MessageInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<html>
<head>
    <title>消息详情</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/pub/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/view/detMessage/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/pub/color.css"/>
    <%
        MessageInfo detMessage = (MessageInfo)request.getAttribute("detMessage");

    %>
</head>
<body>
<%@include file="/pub/head/head.jsp"%>



<div class="alert">
    留下您最想对他(她)说的话。他(她)可能是您曾经的朋友、曾经的恋人，或是您现在的朋友、恋人...
</div>
<div class="detInfo">
    <span>作者UID</span><span><%=detMessage.isAnony()?"匿名对象":detMessage.getUid()%></span><br />
    <span>消息MID</span><span><%=detMessage.getMid()%></span><br />
    <span>收信对象</span><span><%=detMessage.getTarget()%></span><br />
    <span>作者昵称</span><span><%=detMessage.isAnony()?"匿名对象":detMessage.getNickname()%></span><br />
    <span>作者主页</span><a href="">test</a><br />
    <span>写信时间</span><span><%=detMessage.getDate()%></span>
</div>


<div class="panel panel-style-<%=detMessage.getColor()%>" >
    <div class="panel-heading panel-heading-style-<%=detMessage.getColor()%>">
        <span>收信人：<%=detMessage.getTarget()%></span>
    </div>
    <div class="panel-body">
        <div><%=detMessage.getMsg()%></div>
    </div>
    <div class="panel-foot">
			<span class="panel-foot-info">
				<span><%=detMessage.getDate()%></span>
				<span class="nickname"><%=detMessage.isAnony()?"匿名对象":detMessage.getNickname()%></span>
			</span>
    </div>
</div>


</body>

<%--<script type="text/javascript" src="<%=request.getContextPath()%>/pub/jquery-3.5.1.js"></script>--%>
<%--<script type="text/javascript" src="<%=request.getContextPath()%>/pub/utils.js"></script>--%>
<%--<script type="text/javascript" src="<%=request.getContextPath()%>/view/myInfo/base.js"></script>--%>

</html>