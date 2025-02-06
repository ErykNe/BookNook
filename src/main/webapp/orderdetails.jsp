<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>

<style>
    <%@ include file="css/style.css"%>
</style>
<jsp:include page="navbar.jsp" />
<%
    ArrayList<Map<String, Object>> orderItems = (ArrayList<Map<String, Object>>) request.getAttribute("orderItems");
%>

<div>
    <h1>Order Details</h1>
    <%
        if (orderItems != null && !orderItems.isEmpty()) {
    %>
    <table border="1">
        <tr>
            <th>Item Type</th>
            <th>Item Name</th>
            <th>Quantity</th>
            <th>Price</th>
        </tr>
        <%
            for (Map<String, Object> item : orderItems) {
        %>
        <tr>
            <td><%= item.get("ItemType") %></td>
            <td><%= item.get("ItemName") %></td>
            <td><%= item.get("Quantity") %></td>
            <td>$<%= String.format("%.2f", item.get("ItemPrice")) %></td>
        </tr>
        <%
            }
        %>
    </table>
    <%
    } else {
    %>
    <p>No items found for this order.</p>
    <%
        }
    %>
</div>