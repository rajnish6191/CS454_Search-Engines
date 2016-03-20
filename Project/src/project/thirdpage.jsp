<%-- 
    Document   : secondpage
    Created on : Mar 18, 2016, 8:46:00 AM
    Author     : RajnishKumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script>
            
            $(document).ready(function(){
                $("#search").click(function(){
                    
                    var address = $("#address").val(); 
                    alert(address);
                     $.get("http://api.openweathermap.org/data/2.5/weather?q="+address+"&appid=8ee50a4b829df343d01ccb0139ffb248", function(data, status){
                       alert("Data: " + JSON.stringify(data) + "\nStatus: " + status);
                       $("#weatherInfo").text("Country:" + data["sys"]["country"]+ " City:" +data["name"]+ "-Temperature:" + data["main"]["temp"] + "-Humidity:" + data["main"]["humidity"]+ "-Weather:" + data["weather"][0]["description"] )
                    });
                });
            });
            
        </script>
    
        
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <div class="container">
<div class="page-header">
    <%-- //  <h1> Homework No.: 3 & 4 </h1>
<h1> Group Name: Jay & Rajnish </h1> --%>
</div>

<div class="row">
<div class="col-xs-12">
<div class="form-group">
<form>
<table width="50%">
<tr>
<td width="30%">
</td>
<td width="30%">
 </br>
 </br>
<input type="text" class="form-control" id="address" name="txtSearch" placeholder="Search">
</td>
<td width="10%" align="right">
 </br>
 </br>   
<center><input type="button" class="btn btn-primary" value="Search" id="search"/></center>
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
    
    <p id="weatherInfo" style="width:500px;height:380px;margin:0 auto;"></p>
   
    
</div>
</div>
</div>
</div>
    </body>
</html>
