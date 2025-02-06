<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.Utils.Utils" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.models.Book" %>
<%@ page import="com.models.Accessory" %>

<style>
    <%@ include file="css/style.css"%>
</style>

<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("index.jsp?page=login");
        return;
    }

    ArrayList<Map<String, Object>> orders = new ArrayList<>();
    try (Connection conn = Utils.getConnection(application)) {
        String sql = "SELECT * FROM Orders WHERE Username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> order = new HashMap<>();
                    order.put("OrderID", rs.getInt("OrderID"));
                    order.put("OrderDate", rs.getString("OrderDate"));
                    orders.add(order);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<main>
    <div>
        <h2 style="text-align: center">Your Orders</h2>

        <%
            if (!orders.isEmpty()) {
        %>
        <div class="tables" style="text-align: center; justify-content: center">
            <table>
                <tr>
                    <th>Order ID</th>
                    <th>Order Date</th>
                    <th>Details</th>
                </tr>
                <%
                    for (Map<String, Object> order : orders) {
                %>
                <tr>
                    <td><%= order.get("OrderID") %></td>
                    <td><%= order.get("OrderDate") %></td>
                    <td>
                        <form action="<%=request.getContextPath()%>/OrderDetailsServlet" method="post">
                            <input type="text" name="orderId" value="<%= order.get("OrderID") %>" style="display: none">
                            <button type="submit">View Details</button>
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
        <%
        } else {
        %>
        <p>No orders found.</p>
        <%
            }
        %>
    </div>
</main>
