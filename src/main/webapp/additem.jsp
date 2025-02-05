<style>
  <%@ include file="css/style.css"%>
</style>
<jsp:include page="navbar.jsp" />

<div class="add-item-panel">
  <h1>Add book</h1>
<form action="<%=request.getContextPath()%>/AddItemServlet" method="post">
  <table>
    <tr>
      <td><label for="bookTitle">Book Title:</label></td>
      <td><input type="text" id="bookTitle" name="bookTitle" required></td>
    </tr>
    <tr>
      <td><label for="bookAuthor">Book Author:</label></td>
      <td><input type="text" id="bookAuthor" name="bookAuthor" required></td>
    </tr>
    <tr>
      <td><label for="bookPrice">Book Price:</label></td>
      <td><input type="number" step="0.01" id="bookPrice" name="bookPrice" required></td>
    </tr>
    <tr>
      <td><label for="quantity">Quantity:</label></td>
      <td><input type="number" id="quantity" name="quantity" required></td>
    </tr>
    <tr>
      <td><label for="releaseDate">Release Date:</label></td>
      <td><input type="date" id="releaseDate" name="releaseDate" required></td>
    </tr>
  </table>
  <button type="submit">Add Book</button>
</form>
</div>