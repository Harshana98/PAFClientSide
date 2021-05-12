<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
     <%@page import = "com.Review" %>
      
     
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Review handling</title>


<link rel = "stylesheet" href = "Views/bootstrap.min.css">
<script src = "Components/jquery-3.6.0.min.js"></script>
<script src = "Components/items.js"></script>



</head>
<body>

<div class = "container"> 
	<div class="row">
		<div class="col">

		<h1>Reviews Management</h1>
		
	<form id="formItem" name="formItem"  >
		Project ID:
		<input id="itemCode" name="itemCode" type="text" class="form-control form-control-sm"><br>
		 Admin ID:
		<input id="itemName" name="itemName" type="text" class="form-control form-control-sm"><br> 
		Review:
		<input id="itemPrice" name="itemPrice" type="text" class="form-control form-control-sm"><br>
		 Acceptance:
		<input id="itemDesc" name="itemDesc" type="text" class="form-control form-control-sm"><br>
		<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
		<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
	</form>
    
    <div id="alertSuccess" class="alert alert-success"></div>
     <div id="alertError" class="alert alert-danger"></div>
    
    <br>
	<div id="divItemsGrid">
	<%
	Review itemObj = new Review();
		out.print(itemObj.readReviews());
	%>
	</div>

<br>


</div>
</div>
</div>


</body>
</html>