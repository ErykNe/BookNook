package com.servlets;

import com.models.AccessoryDao;
import com.models.BookDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@WebServlet("/RemoveCartServlet")
public class RemoveCartServlet extends HttpServlet {

    public RemoveCartServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp?page=cart").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<Integer> books = null;
        ArrayList<Integer> accessories = null;
        HttpSession session = request.getSession();
        if(session.getAttribute("bookCart") != null) {
            System.out.println("not null");
            books = (ArrayList<Integer>) session.getAttribute("bookCart");
        }
        if(session.getAttribute("accessoriesCart") != null) {
            accessories = (ArrayList<Integer>) session.getAttribute("accessoriesCart");
        }

        int itemID = request.getParameter("RemoveBookID") != null ? Integer.parseInt(request.getParameter("RemoveBookID")) : -1;
        int itemID2 = request.getParameter("RemoveAccessoryID") != null ? Integer.parseInt(request.getParameter("RemoveAccessoryID")) : -1;

        if(itemID != -1 && books != null) {
            Iterator<Integer> iterator = books.iterator();
            while (iterator.hasNext()) {
                Integer book = iterator.next();
                if(book == itemID) {
                    iterator.remove();
                }
            }
            session.setAttribute("bookCart", books);
        }
        if(itemID2 != -1 && accessories != null) {
            Iterator<Integer> iterator = accessories.iterator();
            while (iterator.hasNext()) {
                Integer accessory = iterator.next();
                if(accessory == itemID2) {
                    iterator.remove();
                }
            }
            session.setAttribute("accessoriesCart", accessories);
        }

        request.getRequestDispatcher("index.jsp?page=cart").forward(request, response);
    }
}