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
    <H1>Orders</H1>
    <div style="color:red;">${errorMessage}</div>

    <table class="table">
        <tr>
            <th scope="col">Time/Date of Purchase</th>
            <th scope="col">Buyer</th>
            <th scope="col">Order Number</th>
            <th scope="col">Purchase information</th>

            <th></th>
        </tr>

        <c:forEach var="invoice" items="${userOrders}">
            <tr>
                <td>${invoice.dateOfPurchase}</td>
                <td>${invoice.purchaser.username}</td>
                <td>${invoice.invoiceNumber}</td>
                <td>${invoice.purchasedItems}</td>



            </tr>
        </c:forEach>


</main>
<jsp:include page="footer.jsp" />
