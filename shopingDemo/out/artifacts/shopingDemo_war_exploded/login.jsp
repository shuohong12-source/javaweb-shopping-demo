<%--
  Created by IntelliJ IDEA.
  User: 38720
  Date: 2026/4/26
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
</head>
<body>
    <h2>用户登录</h2>
    <form action="login" method="post">
        用户：<input type="text" name="username"> <br><br>
        密码：<input type="password" name="password"> <br><br>
        <input type="submit" value="登录">
    </form>
    <p style="color: red;">
        <%=request.getAttribute("msg")==null?"":request.getAttribute("msg")%>
    </p>
</body>
</html>
