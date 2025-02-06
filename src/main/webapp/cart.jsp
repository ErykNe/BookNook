<%@ page import="java.sql.Connection" %>
<%@ page import="com.Utils.Utils" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.models.Book" %>
<%@ page import="com.models.Accessory" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.TreeMap" %>

<style>
    <%@ include file="css/style.css"%>
</style>

<%
    ArrayList<Integer> bookCart = null;
    ArrayList<Integer> accessoriesCart = null;
    Map<Book, Integer> books = null;
    Map<Accessory, Integer> accessories = null;

    try (Connection conn = Utils.getConnection(application)) {

        bookCart = session.getAttribute("bookCart") != null || !((ArrayList<Integer>) session.getAttribute("bookCart")).isEmpty()
                ? (ArrayList<Integer>) session.getAttribute("bookCart")
                : new ArrayList<Integer>();
        accessoriesCart = session.getAttribute("accessoriesCart") != null || !((ArrayList<Integer>) session.getAttribute("accessoriesCart")).isEmpty()
                ? (ArrayList<Integer>) session.getAttribute("accessoriesCart")
                : new ArrayList<Integer>();

        books = Utils.getBooksByIDs(conn, bookCart);
        accessories = Utils.getAccessoriesByIDs(conn, accessoriesCart);

    } catch (Exception e) {
        e.printStackTrace();
    }

    double totalPrice = 0.0;

    if (books != null) {
        for (Map.Entry<Book, Integer> entry : books.entrySet()) {
            Book book = entry.getKey();
            double count = entry.getValue();
            totalPrice += book.getPrice() * count;
        }
    }

    if (accessories != null) {
        for (Map.Entry<Accessory, Integer> entry : accessories.entrySet()) {
            Accessory accessory = entry.getKey();
            int count = entry.getValue();
            totalPrice += accessory.getPrice() * count;
        }
    }

    session.setAttribute("totalPrice", totalPrice);


%>

<div class="cart">
    <%
        String messageOrder = (String) request.getAttribute("messageOrder");
        if(messageOrder == null){
            if ((books != null && !books.isEmpty()) || (accessories != null && !accessories.isEmpty())) {

    %>
    <h1>Your Cart</h1>
    <div>
        <div class="books-table">
            <table>
                <% if (books != null && !books.isEmpty()) {%>
                <tr>
                    <th>Book</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Cart</th>
                </tr>
                <tr class="spacer-row">
                    <td colspan="4"></td>
                </tr>
                <%

                    for (Map.Entry<Book, Integer> entry : books.entrySet()) {
                        Book book = entry.getKey();
                        Integer frequency = entry.getValue();
                %>
                <tr class="item-row">
                    <td><img src="images/book.png"></td>
                    <td><%= book.getName() %></td>
                    <td><%= book.getAuthor() %></td>
                    <td><%= book.getPrice() %></td>
                    <td>
                        <form id="<%=book.getBookID()%>updateCartForm" action="<%=request.getContextPath()%>/UpdateCartServlet" method="post">
                            <input
                                    id="<%=book.getBookID()%>book"
                                    type="number"
                                    name="bookQuantity"
                                    min="1"
                                    max="<%= book.getQuantity() %>"
                                    value="<%= frequency %>">
                            <input type="text" name="BookID" id="BookID" value="<%=book.getBookID()%>" style="display: none">
                        </form>
                        <script>
                            document.getElementById('<%=book.getBookID()%>book').addEventListener('change', function() {
                                document.getElementById('<%=book.getBookID()%>updateCartForm').submit();
                            });
                        </script>
                    </td>
                    <td>
                        <form action="<%=request.getContextPath()%>/RemoveCartServlet" method="post">
                            <button type="submit" id="RemoveBookID" name="RemoveBookID" value="<%=book.getBookID()%>">Remove from Cart</button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>
        <div class="accessories-table">
            <table>
                <% if (accessories != null && !accessories.isEmpty()) {%>
                <tr>
                    <th>Accessory</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Cart</th>
                </tr>
                <tr class="spacer-row">
                    <td colspan="4"></td>
                </tr>
                <%

                    for (Map.Entry<Accessory, Integer> entry : accessories.entrySet()) {
                        Accessory accessory = entry.getKey();
                        Integer frequency = entry.getValue();
                %>
                <tr class="item-row">
                    <td><img src="images/pack.png"></td>
                    <td><%= accessory.getName() %></td>
                    <td><%= accessory.getPrice() %></td>
                    <td>
                        <form id="<%=accessory.getAccessoryID()%>updateCartForm2" action="<%=request.getContextPath()%>/UpdateCartServlet" method="post">
                            <input
                                    id="<%=accessory.getAccessoryID()%>accessory"
                                    type="number"
                                    name="accessoryQuantity"
                                    min="1"
                                    max="<%= accessory.getQuantity() %>"
                                    value="<%= frequency %>">
                            <input type="text" name="AccessoryID" id="AccessoryID" value="<%=accessory.getAccessoryID()%>" style="display: none">
                        </form>
                        <script>
                            document.getElementById('<%=accessory.getAccessoryID()%>accessory').addEventListener('change', function() {
                                document.getElementById('<%=accessory.getAccessoryID()%>updateCartForm2').submit();
                            });
                        </script>
                    </td>
                    <td>
                        <form action="<%=request.getContextPath()%>/RemoveCartServlet" method="post">
                            <button type="submit" id="RemoveAccessoryID" name="RemoveAccessoryID" value="<%=accessory.getAccessoryID()%>">Remove from Cart</button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>
        <hr>
        <div class="summary-cart">
            <form class="summary-form" action="<%=request.getContextPath()%>/OrderServlet" method="post">
                <a>Total Price: $<%= String.format("%.2f", totalPrice) %></a>
                <input type="hidden" name="placeOrder" value="true">
                <button type="submit">Place order</button>
            </form>
        </div>
    </div>
    <%
    }else {
    %>
    <tr>
        <td colspan="7" style="text-align:center;">The cart is empty.</td>
    </tr>
    <%
        }
    } else {

    %>
    <tr>
        <td colspan="7" style="text-align:center;"><%=messageOrder%></td>
    </tr>
    <% } %>
</div>