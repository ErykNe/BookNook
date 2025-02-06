<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>

<style>
    <%@ include file="css/style.css"%>
</style>
<jsp:include page="navbar.jsp" />
<%
    ArrayList<Map<String, Object>> orderItems = (ArrayList<Map<String, Object>>) request.getAttribute("orderItems");
%>

<main>
    <div>
        <h2 style="text-align: center">Order Details</h2>
        <%
            if (orderItems != null && !orderItems.isEmpty()) {
        %>
        <div class="tables" style="text-align: center; justify-content: center">
        <table style="text-align: center">
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
        </div>
        <%
        } else {
        %>
        <p>No items found for this order.</p>
        <%
            }
        %>
    </div>
</main>