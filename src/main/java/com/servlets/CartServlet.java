package com.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

    public CartServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //default redirect to itself preventing unwanted get actions performed by user which can lead to malfunctions
        request.getRequestDispatcher("/index.jsp?page=cart").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int itemID = request.getParameter("BookID") != null ? Integer.parseInt(request.getParameter("BookID")) : -1;
        int itemID2 = request.getParameter("AccessoryID") != null ? Integer.parseInt(request.getParameter("AccessoryID")) : -1;

        HttpSession session = request.getSession();
        //get a cart from session
        //(array lists which store all the book and accessory id's)
        ArrayList<Integer> bookCart = (ArrayList<Integer>) session.getAttribute("bookCart");
        ArrayList<Integer> accessoryCart = (ArrayList<Integer>) session.getAttribute("accessoriesCart");

        //create new cart for session
        if (bookCart == null) {
            bookCart = new ArrayList<>();
        }
        if (accessoryCart == null) {
            accessoryCart = new ArrayList<>();
        }
        //add id's to carts
        if(itemID != -1) {
            bookCart.add(itemID);
        }
        if(itemID2 != -1) {
            accessoryCart.add(itemID2);
        }
        session.setAttribute("bookCart", bookCart);
        session.setAttribute("accessoriesCart", accessoryCart);

        //send message to the page
        request.getRequestDispatcher("index.jsp?page=booknook").forward(request, response);
    }
}