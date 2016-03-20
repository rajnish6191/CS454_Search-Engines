
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


<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>




</head>
<body>
<div class="container">
<div class="page-header">
<h1> Project </h1>
</div>

<div class="row">
<div class="col-xs-12">
<div class="form-group">
<form action="webSearch" >
<table width="50%">
<tr>
<td width="30%">
</td>
<td width="30%">
 </br>
 </br>
<input type="text" class="form-control" name="txtSearch" placeholder="Search">
</td>
<td width="10%" align="right">
 </br>
 </br>   
<center><input type="submit" class="btn btn-primary" value="Search" /></center>
</td>
</tr>
<tr>
<td colspan="3">
    <a href="/page.jsp"></a>
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