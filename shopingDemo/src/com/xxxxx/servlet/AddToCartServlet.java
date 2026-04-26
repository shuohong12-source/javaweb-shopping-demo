package com.xxxxx.servlet;

import com.xxxxx.model.CartItem;
import com.xxxxx.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {
    private Product findProductByID(int id){
        List<Product> productList=new ArrayList<>();
        productList.add(new Product(1,"手机",3999.0));
        productList.add(new Product(2,"笔记本电脑",5999.0));
        productList.add(new Product(3,"耳机",299.0));
        productList.add(new Product(4,"机械键盘",499.0));
        for(Product p:productList){
            if(p.getId()==id){
                return p;
            }
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int productId=Integer.parseInt(req.getParameter("id"));
        Product product=findProductByID(productId);
        HttpSession session=req.getSession();
        List<CartItem> cart= (List<CartItem>) session.getAttribute("cart");
        if(cart==null){
            cart=new ArrayList<>();
        }
        boolean found=false;
        for(CartItem item:cart){
            if(item.getProduct().getId()==productId){
                item.setQuantity((item.getQuantity()+1));
                found=true;
                break;
            }
        }
        if(!found&&product!=null){
            cart.add(new CartItem(product,1));
        }
        session.setAttribute("cart",cart);
        resp.sendRedirect("cart");


    }
}
