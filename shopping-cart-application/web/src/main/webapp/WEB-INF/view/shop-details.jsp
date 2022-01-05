<%-- 
    Document   : content
    Created on : Jan 1, 2021, 10:12:33 AM
    Author     : Theodore
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%

%>
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <H1>Shop Details</H1>

</form>
<div id="adminforms">
    <form action="./shop-details" method="post" id="urlform" >
        URL <input type="text" name="url" value="${url}"/> <br>

        <input type="hidden" name="action" value="submiturl">
        <button type="submit" id="submit" >Submit URL</button>

        <p id="erroroutput"></p>


    </form>

    <form action="./shop-details" method="post" id="adminform" onsubmit="return validate()">

        To CreditCard: <input type="text" name="cardto" maxlength="16" value="${cardto}"/> <br>
        Name on Card: <input type="text" name="cardtoname" value="${cardtoname}"/> <br>
        Expiry date: <input type="text" name="cardtoexpdate" value="${cardtoexpdate}"/> <br>
        CVV code: <input type="text" name="cardtocvv" maxlength="3" value="${cardtocvv}" /> <br>



        <input type="hidden" name="action" value="submitadmindetails">
        <button type="submit" id="submit" >Submit</button>

        <p id="erroroutput"></p>
    </form>

    <h1>Refund Details</h1>

    <form action="./shop-details" method="post" id="transactionform" onsubmit="return validate()">
        The Card Number: <input type="text" id="cardno2" name="cardno" maxlength="16"/> <br>

        Amount: <input type="text" name="amount"/>


        <input type="hidden" name="isloggedin" ">
        <input type="hidden" name="action" value="submitrefund">
        <button type="submit" id="submit" >Submit</button>

        <p id="erroroutput"></p>
        
        <p>${message}</p>
    </form>
    
</div>
<script>
    window.addEventListener("load", () => {

        // numpad
        numpad.attach({
            target: document.getElementById("cardno"),
            max: 16, // MAX 16 DIGITS
            decimal: false, // NO DECIMALS ALLOWED

        });
    });

</script>

<script>
    window.addEventListener("load", () => {

        // numpad
        numpad.attach({
            target: document.getElementById("cardno2"),
            max: 16, // MAX 16 DIGITS
            decimal: false, // NO DECIMALS ALLOWED

        });
    });

</script>



<script src="resources/js/adminlogin.js"></script> 
</main>


<jsp:include page="footer.jsp" />
