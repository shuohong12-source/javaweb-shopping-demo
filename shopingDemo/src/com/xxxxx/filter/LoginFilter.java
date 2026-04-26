package com.xxxxx.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;

        String uri=request.getRequestURI();
        HttpSession session=request.getSession(false);

        boolean loggedIn=(session!=null&&session.getAttribute("user")!=null);

        boolean allowedRequest=
                uri.endsWith("login.jsp")
                ||uri.endsWith("login")
                ||uri.contains("/css/")
                ||uri.contains("/js/")
                ||uri.contains("/images/");
        if(loggedIn||allowedRequest){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }

    }
}
