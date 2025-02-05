<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.SQLException, com.models.BookDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.Utils.Utils" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="com.models.AccessoryDao" %>

<style>
    <%@ include file="css/style.css"%>
</style>
<%
    ArrayList<AccessoryDao> accessories = null;
    ArrayList<BookDao> books = null;
    try (Connection conn = Utils.getConnection(application)) {
        books = request.getAttribute("books") != null &&  request.getAttribute("books") != "noresult"
                ? (ArrayList<BookDao>) request.getAttribute("books") : Utils.getBooks(conn);
        if(request.getAttribute("books") == "noresult"){
            books = null;
        }
        accessories = request.getAttribute("books") != null ? null : Utils.getAccessories(conn);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<div class="booknook">
    <div class="search-bar">
        <form action="<%=request.getContextPath()%>/SearchServlet" method="post">
            <img src="images/lupe.png">
            <input type="text" placeholder="Search For Books" id="search-input" name="search-input">
            <button type="submit" style="display: none;">Search</button>
        </form>
    </div>
    <form action="<%=request.getContextPath()%>/CartServlet" method="post">
        <div class="tables">
            <div class="books-table">
                <table>
                    <tr>
                        <th>Book</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Release Date</th>
                        <th>Cart</th>
                    </tr>
                    <tr class="spacer-row">
                        <td colspan="4"></td>
                    </tr>
                    <%
                        if (books != null && !books.isEmpty()) {
                            for (BookDao book : books) {
                    %>
                    <tr class="item-row">
                        <td><img src="images/book.png"></td>
                        <td><%= book.getBookTitle() %></td>
                        <td><%= book.getBookAuthor() %></td>
                        <td><%= book.getBookPrice() %></td>
                        <td><%= book.getQuantity() %></td>
                        <td><%= book.getReleaseDate() %></td>
                        <% if (session != null && session.getAttribute("username") != null) { %>
                        <td>
                            <button type="submit" id="BookID" name="BookID" value="<%= book.getBookID()%>">Add to Cart</button>
                        </td>
                        <% } else { %>
                        <td>
                            <button disabled>Add to Cart</button>
                        </td>
                        <% } %>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="7" style="text-align:center;">No books available.</td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>
            <hr>
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
                        <th>Cart</th>
                    </tr>
                    <tr class="spacer-row">
                        <td colspan="4"></td>
                    </tr>
                    <%
                        for (AccessoryDao accessory : accessories) {
                    %>
                    <tr class="item-row">
                        <td><img src="images/pack.png"></td>
                        <td><%= accessory.getAccessoryName() %></td>
                        <td><%= accessory.getAccessoryPrice() %></td>
                        <td><%= accessory.getQuantity() %></td>
                        <% if (session != null && session.getAttribute("username") != null) { %>
                        <td>
                            <button type="submit" id="AccessoryID" name="AccessoryID" value="<%= accessory.getAccessoryID()%>">Add to Cart</button>
                        </td>
                        <% } else { %>
                        <td>
                            <button disabled>Add to Cart</button>
                        </td>
                        <% } %>
                    </tr>
                    <%
                            }
                        } %>
                </table>
            </div>
        </div>
    </form>
</div>