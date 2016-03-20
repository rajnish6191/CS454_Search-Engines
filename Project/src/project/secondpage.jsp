<%-- 
    Document   : secondpage
    Created on : Mar 18, 2016, 8:46:00 AM
    Author     : RajnishKumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
       <script
src="http://maps.googleapis.com/maps/api/js">
</script>

<script>
    
    var geocoder = new google.maps.Geocoder();
    var map;
function initialize() {
  var mapProp = {
    center:new google.maps.LatLng(51.508742,-0.120850),
    zoom:5,
    mapTypeId:google.maps.MapTypeId.ROADMAP
  };
   map=new google.maps.Map(document.getElementById("googleMap"), mapProp);
}
function codeAddress() {
    var address = document.getElementById("address").value;
    geocoder.geocode( { 'address': address}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        map.setCenter(results[0].geometry.location);
        
        document.getElementById("location").innerText = results[0].geometry.location;
        var marker = new google.maps.Marker({
            map: map,
            position: results[0].geometry.location
        });
      } else {
        alert("Geocode was not successful for the following reason: " + status);
      }
    });
  }

google.maps.event.addDomListener(window, 'load', initialize);
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
<center><input type="button" class="btn btn-primary" value="Search" onclick="codeAddress()" /></center>
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
    
    <div id="googleMap" style="width:500px;height:380px;margin:0 auto;"></div>
    <br/>
    <div id="location" style="width:500px;height:100px;margin:0 auto;"></div>
</div>
</div>
</div>
</div>
    </body>
</html>
