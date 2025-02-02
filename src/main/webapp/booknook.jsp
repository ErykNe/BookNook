<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.SQLException, com.models.BookDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.Utils.Utils" %>

<table>
<tr>
    <th>ID</th>
    <th>Title</th>
    <th>Author</th>
    <th>Price</th>
    <th>Quantity</th>
    <th>Release Date</th>
</tr>
<%
    ArrayList<BookDao> books = null;
    try (Connection conn = Utils.getConnection(application)) {
        books = Utils.getBooks(conn);
    } catch (Exception e) {
    }

    if (books != null && !books.isEmpty()) {
        for (BookDao book : books) {
%>
<tr>
    <td><%= book.getBookID() %></td>
    <td><%= book.getBookTitle() %></td>
    <td><%= book.getBookAuthor() %></td>
    <td><%= book.getBookPrice() %></td>
    <td><%= book.getQuantity() %></td>
    <td><%= book.getReleaseDate() %></td>
</tr>
<%
    }
} else {
%>
<tr>
    <td colspan="6" style="text-align:center;">No books available.</td>
</tr>
<%
    }
%>
</table>