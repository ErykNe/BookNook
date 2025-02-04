<%@ page import="java.sql.Connection" %>
<%@ page import="com.Utils.Utils" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.models.BookDao" %>
<%@ page import="com.models.AccessoryDao" %>
<%@ page import="java.util.Map" %>

<style>
    <%@ include file="css/style.css"%>
</style>

<%
    ArrayList<Integer> bookCart = null;
    ArrayList<Integer> accessoriesCart = null;
    Map<BookDao, Integer> books = null;
    Map<AccessoryDao, Integer> accessories = null;

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
        for (Map.Entry<BookDao, Integer> entry : books.entrySet()) {
            BookDao book = entry.getKey();
            double count = entry.getValue();
            totalPrice += book.bookPrice * count;
        }
    }

    if (accessories != null) {
        for (Map.Entry<AccessoryDao, Integer> entry : accessories.entrySet()) {
            AccessoryDao accessory = entry.getKey();
            int count = entry.getValue();
            totalPrice += accessory.accessoryPrice * count;
        }
    }


%>

<div class="cart">
    <%
        if ((books != null && !books.isEmpty()) || (accessories != null && !accessories.isEmpty())) {

    %>
    <h1>Your Cart</h1>
    <div>
    <form action="<%=request.getContextPath()%>/RemoveCartServlet" method="post">
    <div class="books-table">
        <table>
            <% if (books != null && !books.isEmpty()) {%>
            <tr>
                <th>Book</th>
                <th>Title</th>
                <th>Author</th>
                <th>Price</th>
                <th>Release Date</th>
                <th>Quantity</th>
                <th>Cart</th>
            </tr>
            <tr class="spacer-row">
                <td colspan="4"></td>
            </tr>
            <%

            for (Map.Entry<BookDao, Integer> entry : books.entrySet()) {
                BookDao book = entry.getKey();
                Integer frequency = entry.getValue();
            %>
            <tr class="item-row">
                <td><img src="images/book.png"></td>
                <td><%= book.getBookTitle() %></td>
                <td><%= book.getBookAuthor() %></td>
                <td><%= book.getBookPrice() %></td>
                <td><%= book.getReleaseDate() %></td>
                <td><input type="number" min="1" max=<%=book.getQuantity()%> value="<%= frequency.toString()%>"></td>
                <td><button type="submit" id="RemoveBookID" name="RemoveBookID" value="<%=book.getBookID()%>">Remove from Cart</button></td>
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

                for (Map.Entry<AccessoryDao, Integer> entry : accessories.entrySet()) {
                    AccessoryDao accessory = entry.getKey();
                    Integer frequency = entry.getValue();
            %>
            <tr class="item-row">
                <td><img src="images/pack.png"></td>
                <td><%= accessory.getAccessoryName() %></td>
                <td><%= accessory.getAccessoryPrice() %></td>
                <td><input type="number" min="1" max=<%=accessory.getQuantity()%> value="<%= frequency.toString()%>"></td>
                <td><button type="submit" id="RemoveAccessoryID" name="RemoveAccessoryID" value="<%= accessory.getAccessoryID()%>">Remove from Cart</button></td>
            </tr>
            <%
                    }
            }
                %>
        </table>
    </div>
    </form>
    <hr>
    <div class="summary-cart">
        <form class="summary-form" action="/OrderServlet" method="post">
            <a>Total Price: $<%= String.format("%.2f", totalPrice) %></a>
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
    %>
</div>