package com.xxxxx.servlet;

import com.xxxxx.model.CartItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession();
        List<CartItem> cart= (List<CartItem>) session.getAttribute("cart");
        double total=0;
        if(cart!=null){
            for(CartItem item:cart){
                total+=item.getSubTotal();
            }
        }
        req.setAttribute("cart",cart);
        req.setAttribute("total",total);
        req.getRequestDispatcher("cart.jsp").forward(req,resp);
    }
}
