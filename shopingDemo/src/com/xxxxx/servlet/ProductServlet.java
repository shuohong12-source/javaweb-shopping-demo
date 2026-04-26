package com.xxxxx.servlet;

import com.xxxxx.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> productList=new ArrayList<>();
        productList.add(new Product(1,"手机",3999.0));
        productList.add(new Product(2,"笔记本电脑",5999.0));
        productList.add(new Product(3,"耳机",299.0));
        productList.add(new Product(4,"机械键盘",499.0));
        req.setAttribute("productList",productList);
        req.getRequestDispatcher("product.jsp").forward(req,resp);
    }
}
