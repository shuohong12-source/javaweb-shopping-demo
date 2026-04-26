<%--
  Created by IntelliJ IDEA.
  User: 38720
  Date: 2026/4/26
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xxxxx.model.CartItem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>结算页面</title>
</head>
<body>
<h2>结算结果</h2>
<a href="products">返回商品列表</a> |
<a href="logout">退出登录</a>
<hr>
<%
    List<CartItem>cart=(List<CartItem>)request.getAttribute("cart");
    Double total=(Double)request.getAttribute("total");
    if(cart==null||cart.size()==0){
%>
<p>购物车为空，无法结算！</p>
<%
}else{
%>
<table border="1" cellspacing="0" cellpadding="10">
    <tr>
        <th>商品名称</th>
        <th>单价</th>
        <th>数量</th>
        <th>小计</th>
    </tr>
    <%
        for(CartItem item:cart){
    %>
    <tr>
        <td><%=item.getProduct().getName()%></td>
        <td><%=item.getProduct().getPrice()%></td>
        <td><%=item.getQuantity()%></td>
        <td><%=item.getSubTotal()%></td>
    </tr>
    <%
        }
    %>
</table>
<h3>应付总金额：<%= total %></h3>
<%
    }
%>
</body>
</html>