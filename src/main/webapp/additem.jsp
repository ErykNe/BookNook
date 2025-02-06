<style>
  <%@ include file="css/style.css"%>
</style>
<jsp:include page="navbar.jsp" />
<script>
  function toggleFields() {
    var itemType = document.getElementById("itemType").value;
    var bookFields = document.getElementById("bookFields");
    var accessoryFields = document.getElementById("accessoryFields");

    if (itemType === "book") {
      bookFields.style.display = "block";
      accessoryFields.style.display = "none";
    } else if (itemType === "accessory") {
      bookFields.style.display = "none";
      accessoryFields.style.display = "block";
    } else {
      bookFields.style.display = "none";
      accessoryFields.style.display = "none";
    }
  }
</script>
<div class="add-item-panel">
  <form action="<%=request.getContextPath()%>/AddItemServlet" method="post">
    <label for="itemType">Item Type:</label>
    <select id="itemType" name="itemType" onchange="toggleFields()" required>
      <option value="">Select Item Type</option>
      <option value="book">Book</option>
      <option value="accessory">Accessory</option>
    </select>

    <div id="bookFields" style="display:none;">
      <h3>Book Details</h3>
      <table>
        <tr>
          <td><label for="bookTitle">Book Title:</label></td>
          <td><input type="text" id="bookTitle" name="bookTitle"></td>
        </tr>
        <tr>
          <td><label for="bookAuthor">Book Author:</label></td>
          <td><input type="text" id="bookAuthor" name="bookAuthor"></td>
        </tr>
        <tr>
          <td><label for="bookPrice">Book Price:</label></td>
          <td><input type="number" step="0.01" id="bookPrice" name="bookPrice"></td>
        </tr>
        <tr>
          <td><label for="quantity">Quantity:</label></td>
          <td><input type="number" id="quantity" name="quantity"></td>
        </tr>
      </table>
    </div>

    <div id="accessoryFields" style="display:none;">
      <h3>Accessory Details</h3>
      <table>
        <tr>
          <td><label for="accessoryName">Accessory Name:</label></td>
          <td><input type="text" id="accessoryName" name="accessoryName"></td>
        </tr>
        <tr>
          <td><label for="accessoryPrice">Accessory Price:</label></td>
          <td><input type="number" step="0.01" id="accessoryPrice" name="accessoryPrice"></td>
        </tr>
        <tr>
          <td><label for="quantity">Quantity:</label></td>
          <td><input type="number" id="accessoryQuantity" name="accessoryQuantity"></td>
        </tr>
      </table>
    </div>

    <button type="submit">Add Item</button>
  </form>
</div>