<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
// request set in controller
//request.setAttribute("selectedPage", "home");
%>
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <H1>Catalogue</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

    <form action="./catalog" method="post" id="addtocatalogform"">
        Item Name: <input type="text" id="newitemname" name="newitemname"/> <br>
        Item Stock: <input type="text" id="newitemstock" name="newitemstock"/> <br>
        Item Price: <input type="text" id="newitemprice" name="newitemprice"/> <br>



        <input type="hidden" name="action" value="addItemToList">
        <button type="submit" id="submit" >Add Item</button>

        <p id="erroroutput"></p>
    </form>

    <H1>Available Items</H1>
    <table class="table">

        <tr>
            <th>Item Name</th>
            <th>Price</th>
            <th></th>
        </tr>

        <c:forEach var="item" items="${availableItems}">

            <tr>
                <td>${item.name}</td>
                <td>${item.price}</td>
                <td></td>
                <td>
                    <!-- post avoids url encoded parameters -->
                    <form action="./catalog" method="get">
                        <input type="hidden" name="itemName" value="${item.name}">
                        <input type="hidden" name="action" value="removeItemFromList">
                        <button type="submit" >Delete Item</button>
                    </form> 
                </td>
            </tr>

        </c:forEach>
    </table>



</main>
<jsp:include page="footer.jsp" />
