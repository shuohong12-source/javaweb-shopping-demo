<%--
  Created by IntelliJ IDEA.
  User: 38720
  Date: 2026/4/26
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xxxxx.model.Product" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>商品列表</title>
</head>
<body>
    <h2>商品列表</h2>
    <a href="cart">查看购物车</a>
    <a href="logout">退出登录</a>
    <%
        List<Product> productList = (List<Product>) request.getAttribute("productList");
        if (productList != null) {
            for (Product p : productList) {
    %>
    <p>
        商品编号：<%= p.getId() %>
        &nbsp;&nbsp; 商品名称：<%= p.getName() %>
        &nbsp;&nbsp; 商品价格：<%= p.getPrice() %>
    </p>
    <form action="addToCart" method="post">
        <input type="hidden" name="id" value="<%= p.getId() %>">
        <input type="submit" value="加入购物车">
    </form>
    <hr>
    <%
            }
        }
    %>

</body>
</html>
