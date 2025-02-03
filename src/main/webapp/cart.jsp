<%@ page import="java.sql.Connection" %>
<%@ page import="com.Utils.Utils" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.models.BookDao" %>
<%@ page import="com.models.AccessoryDao" %>
<%@ page import="java.util.Map" %>

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

%>

<div class="cart">
    <div class="tables">
    <div class="books-table">
        <%
            if (books != null && !books.isEmpty()) {

        %>
        <table>

            <tr>
                <th>Book</th>
                <th>Title</th>
                <th>Author</th>
                <th>Price</th>
                <th>Release Date</th>
                <th></th>
                <th>Quantity</th>
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
                <td></td>
                <td><%= frequency.toString()%></td>
            </tr>
            <%
                }
                } else {
            %>
            <tr>
                <td colspan="7" style="text-align:center;">No books available.</td>
            </tr>

        </table>
        <%

            }
        %>
    </div>
    <div class="accessories-table">
        <%
            if (accessories != null && !accessories.isEmpty()) {

        %>
        <table>
            <tr>
                <th>Accessory</th>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
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
                <td><%= frequency.toString() %></td>
            </tr>
            <%
                    }
                 %>
        </table>
        <%
            }
        %>
    </div>
    </div>
</div>