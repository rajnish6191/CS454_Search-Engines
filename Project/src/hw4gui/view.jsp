
<!doctype html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<title>Search Engine</title>
<%@page import="controller.*"%>

<%@page import="controller.*"%>

<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<center><h1>Results of Pages in Decreasing order of Rank</h1></center>
</head>





<body>


<table class="table" border="1">
<thead>
<th>Document Ids</th>  
<th>Location </th>
<th>Link Score </th>
<th>tfidf Score </th>
<th>Normalized Score</th>
</thead>
<tbody>


<c:forEach items="${query}" var="product">

<tr class="info">
<c:set var="data" value="${fn:split(product.key,'~')}" />
<td>${data[0]}

</td>
<td>
${data[1]}


</td>
<td>
${data[2]}


</td>
<td>
${data[3]}


</td>


<td>
${product.value}
</td>


</tr>
</c:forEach>



</tbody>
</table>









</table>
</td>
</tr>
</table>
</form>
</div>
</div>
</div>
</div>
</body>
</html>