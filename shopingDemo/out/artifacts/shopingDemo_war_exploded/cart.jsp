<%--
  Created by IntelliJ IDEA.
  User: 38720
  Date: 2026/4/26
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xxxxx.model.CartItem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>购物车</title>
</head>
<body>
<h2>我的购物车</h2>
<a href="products">继续购物</a> |
<a href="checkout">去结算</a> |
<a href="logout">退出登录</a>
<hr>

<%
    List<CartItem> cart = (List<CartItem>) request.getAttribute("cart");
    Double total = (Double) request.getAttribute("total");

    if (cart == null || cart.size() == 0) {
%>
<p>购物车为空！</p>
<%
} else {
%>
<table border="1" cellspacing="0" cellpadding="10">
    <tr>
        <th>商品ID</th>
        <th>名称</th>
        <th>价格</th>
        <th>数量</th>
        <th>小计</th>
    </tr>
    <%
        for (CartItem item : cart) {
    %>
    <tr>
        <td><%= item.getProduct().getId() %></td>
        <td><%= item.getProduct().getName() %></td>
        <td><%= item.getProduct().getPrice() %></td>
        <td><%= item.getQuantity() %></td>
        <td><%= item.getSubTotal() %></td>
    </tr>
    <%
        }
    %>
</table>
<h3>总价：<%= total %></h3>
<%
    }
%>
</body>
</html>