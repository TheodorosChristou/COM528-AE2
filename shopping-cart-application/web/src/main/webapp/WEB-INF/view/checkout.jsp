<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
// request set in controller
//request.setAttribute("selectedPage", "checkout");a
%>
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <H1>Checkout Details</H1>
    <div style="color:red;">${errorMessage}</div>

    <form action="./checkout" method="post" id="transactionform" onsubmit="return validate()">
        Your Card Number: <input type="text" id="cardno" name="cardno" maxlength="16"/> <br>
        Name on Card: <input type="text" name="cardfromname"/> <br>
        Expiry date: <input type="text" name="cardfromexpdate"/> <br>
        CVV code: <input type="text" name="cardfromcvv" maxlength="3"/> <br> <br>



        <input type="hidden" name="action" value="transaction">
        <button type="submit" id="submit" >Submit</button>
        
        <p>${transMessage}</p>
        <p id="erroroutput"></p>
    </form>

    <script src="resources/js/validation.js"></script> 


    <H1>Shopping cart</H1>
    <table class="table">

        <tr>
            <th>Item Name</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>

        <c:forEach var="item" items="${shoppingCartItems}">

            <tr>
                <td>${item.name}</td>
                <td>${item.price}</td>
                <td>${item.quantity}</td>
                <td>
                    <!-- post avoids url encoded parameters -->
                    <form action="./checkout" method="post">
                        <input type="hidden" name="itemUUID" value="${item.uuid}">
                        <input type="hidden" name="itemName" value="${item.name}">
                        <input type="hidden" name="action" value="removeItemFromCart">
                        <button type="submit" >Remove Item</button>
                    </form> 
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td>TOTAL</td>
            <td>${shoppingcartTotal}</td>
        </tr>
    </table>



</main>
<jsp:include page="footer.jsp" />
