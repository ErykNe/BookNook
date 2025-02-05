package com.servlets;

import com.models.BookDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public UpdateCartServlet() {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp?page=cart").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookID = -1;
        int bookQuantity = -1;
        if (request.getParameter("BookID") != null) {
            bookID = Integer.parseInt(request.getParameter("BookID"));
        }
        System.out.println(bookID);
        if (request.getParameter("bookQuantity") != null) {
            bookQuantity = Integer.parseInt(request.getParameter("bookQuantity"));
        }
        System.out.println(bookQuantity);

        int accessoryID = -1;
        int accessoryQuantity = -1;

        if (request.getParameter("AccessoryID") != null) {
            accessoryID = Integer.parseInt(request.getParameter("AccessoryID"));
        }
        System.out.println(accessoryID);
        if (request.getParameter("accessoryQuantity") != null) {
            accessoryQuantity = Integer.parseInt(request.getParameter("accessoryQuantity"));
        }
        System.out.println(accessoryQuantity);

        HttpSession session = request.getSession();

        ArrayList<Integer> bookIDs = (ArrayList<Integer>) session.getAttribute("bookCart");
        ArrayList<Integer> accessoryIDs = (ArrayList<Integer>) session.getAttribute("accessoriesCart");

        if (bookID != -1){
            int count = 0;
            for (Integer id : bookIDs) {
                if (id.equals(bookID)) {
                    count++;
                }
            }

            if (count > bookQuantity) {
                int removeCount = count - bookQuantity;
                for (int i = bookIDs.size() - 1; i >= 0 && removeCount > 0; i--) {
                    if (bookIDs.get(i).equals(bookID)) {
                        bookIDs.remove(i);
                        removeCount--;
                    }
                }
            } else if (count < bookQuantity) {
                int addCount = bookQuantity - count;
                for (int i = 0; i < addCount; i++) {
                    bookIDs.add(bookID);
                }
            }

            session.setAttribute("bookCart", bookIDs);
        }
        if (accessoryID != -1){
            int count = 0;
            for (Integer id : accessoryIDs) {
                if (id.equals(accessoryID)) {
                    count++;
                }
            }

            if (count > accessoryQuantity) {
                int removeCount = count - accessoryQuantity;
                for (int i = accessoryIDs.size() - 1; i >= 0 && removeCount > 0; i--) {
                    if (accessoryIDs.get(i).equals(accessoryID)) {
                        accessoryIDs.remove(i);
                        removeCount--;
                    }
                }
            } else if (count < accessoryQuantity) {
                int addCount = accessoryQuantity - count;
                for (int i = 0; i < addCount; i++) {
                    accessoryIDs.add(accessoryID);
                }
            }

            session.setAttribute("accessoriesCart", accessoryIDs);
        }
        request.getRequestDispatcher("/index.jsp?page=cart").forward(request, response);
    }
}
