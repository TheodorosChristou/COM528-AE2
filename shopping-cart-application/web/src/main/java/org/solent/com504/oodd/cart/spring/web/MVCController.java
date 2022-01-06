package org.solent.com504.oodd.cart.spring.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.solent.com504.oodd.cart.model.service.ShoppingCart;
import org.solent.com504.oodd.cart.model.service.ShoppingService;
import org.solent.com504.oodd.cart.web.WebObjectFactory;
import org.solent.com504.oodd.bank.client.impl.BankRestClientImpl;
import org.solent.com504.oodd.bank.model.dto.CreditCard;
import org.solent.com504.oodd.bank.model.dto.TransactionReplyMessage;
import org.solent.com504.oodd.cart.service.ShoppingServiceImpl;
import org.solent.com504.oodd.cart.web.PropertiesDaoFile;
import org.solent.com504.oodd.cart.web.PropertiesFileFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.solent.com504.oodd.cart.dao.impl.InvoiceRepository;
import org.solent.com504.oodd.cart.model.dto.Invoice;
import org.solent.com504.oodd.cart.web.transactionLog.transactionLogger;

@Controller
@RequestMapping("/")
public class MVCController {

    final static Logger LOG = LogManager.getLogger(MVCController.class);

    // this could be done with an autowired bean
    //private ShoppingService shoppingService = WebObjectFactory.getShoppingService();
    @Autowired
    ShoppingService shoppingService = null;

    // note that scope is session in configuration
    // so the shopping cart is unique for each web session
    @Autowired
    ShoppingCart shoppingCart = null;

    private User getSessionUser(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            sessionUser = new User();
            sessionUser.setUsername("anonymous");
            sessionUser.setUserRole(UserRole.ANONYMOUS);
            session.setAttribute("sessionUser", sessionUser);
        }
        return sessionUser;
    }

    // this redirects calls to the root of our application to index.html
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        return "redirect:/index.html";
    }

    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewCart(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "itemUUID", required = false) String itemUuid,
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "home");

        String message = "";
        String errorMessage = "";

        // note that the shopping cart is is stored in the sessionUser's session
        // so there is one cart per sessionUser
//        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
//        if (shoppingCart == null) synchronized (this) {
//            if (shoppingCart == null) {
//                shoppingCart = WebObjectFactory.getNewShoppingCart();
//                session.setAttribute("shoppingCart", shoppingCart);
//            }
//        }
        if (action == null) {
            // do nothing but show page
        } else if ("addItemToCart".equals(action)) {
            ShoppingItem shoppingItem = shoppingService.getNewItemByName(itemName);
            if (shoppingItem == null) {
                message = "cannot add unknown " + itemName + " to cart";
            } else {
                message = "adding " + itemName + " to cart price= " + shoppingItem.getPrice();
                shoppingCart.addItemToCart(shoppingItem);
            }
        } else if ("removeItemFromCart".equals(action)) {
            message = "removed " + itemName + " from cart";
            shoppingCart.removeItemFromCart(itemUuid);
        } else {
            message = "unknown action=" + action;
        }

        List<ShoppingItem> availableItems = shoppingService.getAvailableItems();

        List<ShoppingItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

        Double shoppingcartTotal = shoppingCart.getTotal();

        // populate model with values
        model.addAttribute("availableItems", availableItems);
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);

        return "home";
    }

    @RequestMapping(value = "/about", method = {RequestMethod.GET, RequestMethod.POST})
    public String aboutCart(Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "about");
        return "about";
    }

    @RequestMapping(value = "/contact", method = {RequestMethod.GET, RequestMethod.POST})
    public String contactCart(Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "contact");
        return "contact";
    }

    @RequestMapping(value = "/checkout", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewCartCheckout(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "itemUUID", required = false) String itemUuid,
            @RequestParam(name = "cardno", required = false) String cardno,
            @RequestParam(name = "cardnoname", required = false) String cardnoname,
            @RequestParam(name = "cardnoexpdate", required = false) String cardnoexpdate,
            @RequestParam(name = "cardnocvv", required = false) String cardnocvv,
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "checkout");

        String message = "";
        String transMessage = "";
        String errorMessage = "";
        PropertiesDaoFile propertiesDaoFile = PropertiesFileFactory.getPropertiesDaoFile();
        String cardto = propertiesDaoFile.getProperty("org.solent.com504.oodd.web.cardto");
        String cardtoname = propertiesDaoFile.getProperty("org.solent.com504.oodd.web.cardtoname");
        String cardtoexpdate = propertiesDaoFile.getProperty("org.solent.com504.oodd.web.cardtoexpdate");
        String cardtocvv = propertiesDaoFile.getProperty("org.solent.com504.oodd.web.cardtocvv");
        String url = propertiesDaoFile.getProperty("org.solent.com504.oodd.web.url");

        List<ShoppingItem> availableItems = shoppingService.getAvailableItems();

        List<ShoppingItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

        Double shoppingcartTotal = shoppingCart.getTotal();
        // note that the shopping cart is is stored in the sessionUser's session
        // so there is one cart per sessionUser
//        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
//        if (shoppingCart == null) synchronized (this) {
//            if (shoppingCart == null) {
//                shoppingCart = WebObjectFactory.getNewShoppingCart();
//                session.setAttribute("shoppingCart", shoppingCart);
//            }
//        }

        try {
            BankRestClientImpl rester = new BankRestClientImpl(url);
            CreditCard fromCard = new CreditCard();
            CreditCard toCard = new CreditCard();
            Double totalAmount = shoppingCart.getTotal();

            if (action == null) {
                // do nothing but show page
            } else if ("transaction".equals(action)) {
                fromCard.setCardnumber(cardno);
                fromCard.setName(cardnoname);
                fromCard.setEndDate(cardnoexpdate);
                fromCard.setCvv(cardnocvv);

                toCard.setCardnumber(cardto);
                toCard.setName(cardtoname);
                toCard.setEndDate(cardtoexpdate);
                toCard.setCvv(cardtocvv);

                TransactionReplyMessage query = rester.transferMoney(fromCard, toCard, totalAmount);

                String errormessage = "";
                errormessage = query.getMessage();
                if (errormessage == null && totalAmount > 0) {
                    String logmsg = "Transaction was completed with card" + " " + cardno + " " + "for the items" + " " + shoppingCartItems + "for the total amount of" + " " + totalAmount + "." + "Full report: " + query;
                    transactionLogger.Logger(logmsg);
                    transMessage = "order has been placed successfully";
                } else {
                    String logmsg = "Transaction was unsuccessful with card" + " " + cardno + " " + "for the amount of" + " " + totalAmount + "." + "Full report: " + query;
                    transactionLogger.Logger(logmsg);
                    transMessage = "order failed, please check your card details, shopping cart or contact admin";
                }
            }
        } catch (Exception e) {
            transMessage = "Error connecting to website, please contact admin";
        }
        if ("removeItemFromCart".equals(action)) {
            message = "removed " + itemName + " from cart";
            shoppingCart.removeItemFromCart(itemUuid);
        } else {
            message = "unknown action=" + action;
        }

        // populate model with values
        model.addAttribute("availableItems", availableItems);
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("transMessage", transMessage);
        model.addAttribute("errorMessage", errorMessage);

        return "checkout";
    }

    @RequestMapping(value = "/userOrders", method = {RequestMethod.GET, RequestMethod.POST})
    public String userOrders(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "itemUUID", required = false) String itemUuid,
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "userOrders");
        

        List<ShoppingItem> availableItems = shoppingService.getAvailableItems();

        List<ShoppingItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

        Double shoppingcartTotal = shoppingCart.getTotal();

        model.addAttribute("availableItems", availableItems);
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        return "userOrders";
    }

    @RequestMapping(value = "/catalog", method = {RequestMethod.GET, RequestMethod.POST})
    public String catalog(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "itemUUID", required = false) String itemUuid,
            @RequestParam(name = "newitemname", required = false) String newitemname,
            @RequestParam(name = "newitemprice", required = false) Double newitemprice,
            Model model,
            HttpSession session,
            HttpServletRequest request) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "catalog");

        String message = "";
        String errorMessage = "";

        List<ShoppingItem> availableItems = shoppingService.getAvailableItems();

        List<ShoppingItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

        Double shoppingcartTotal = shoppingCart.getTotal();

        if (action == null) {
            // do nothing but show page
        } else if ("addItemToList".equals(action)) {
            ShoppingItem newshoppingitem = new ShoppingItem(newitemname, newitemprice);
            ShoppingServiceImpl shoppingserviceimpl = new ShoppingServiceImpl();
            System.out.println(availableItems);
            System.out.println(newshoppingitem);
        } else if ("removeItemFromList".equals(action)) {
            message = "removed " + itemName + " from cart";
            shoppingCart.removeItemFromCart(itemUuid);
        } else {
            message = "unknown action=" + action;
        }

        // populate model with values
        model.addAttribute("availableItems", availableItems);
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);

        return "catalog";
    }


    /*
     * Default exception handler, catches all exceptions, redirects to friendly
     * error page. Does not catch request mapping errors
     */
    @ExceptionHandler(Exception.class)
    public String myExceptionHandler(final Exception e, Model model, HttpServletRequest request) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        final String strStackTrace = sw.toString(); // stack trace as a string
        String urlStr = "not defined";
        if (request != null) {
            StringBuffer url = request.getRequestURL();
            urlStr = url.toString();
        }
        model.addAttribute("requestUrl", urlStr);
        model.addAttribute("strStackTrace", strStackTrace);
        model.addAttribute("exception", e);
        //logger.error(strStackTrace); // send to logger first
        return "error"; // default friendly exception message for sessionUser
    }

    @RequestMapping(value = "/shop-details", method = {RequestMethod.GET, RequestMethod.POST})
    public String shopD(Model model, HttpSession session, HttpServletRequest request) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "shop-details");

        PropertiesDaoFile propertiesDaoFile = PropertiesFileFactory.getPropertiesDaoFile();

        String cardto = propertiesDaoFile.getProperty("org.solent.com504.oodd.web.cardto");
        String cardtoname = propertiesDaoFile.getProperty("org.solent.com504.oodd.web.cardtoname");
        String cardtoexpdate = propertiesDaoFile.getProperty("org.solent.com504.oodd.web.cardtoexpdate");
        String cardtocvv = propertiesDaoFile.getProperty("org.solent.com504.oodd.web.cardtocvv");
        String url = propertiesDaoFile.getProperty("org.solent.com504.oodd.web.url");
        String message = "";

        CreditCard toCard = new CreditCard();
        CreditCard fromCard = new CreditCard();
        BankRestClientImpl rester = new BankRestClientImpl(url);

        // get the action
        String action = (String) request.getParameter("action");
        if ("submitadmindetails".equals(action)) {
            cardto = (String) request.getParameter("cardto");
            cardtoname = (String) request.getParameter("cardtoname");
            cardtoexpdate = (String) request.getParameter("cardtoexpdate");
            cardtocvv = (String) request.getParameter("cardtocvv");

            propertiesDaoFile.setProperty("org.solent.com504.oodd.web.cardto", cardto);
            propertiesDaoFile.setProperty("org.solent.com504.oodd.web.cardtoname", cardtoname);
            propertiesDaoFile.setProperty("org.solent.com504.oodd.web.cardtoexpdate", cardtoexpdate);
            propertiesDaoFile.setProperty("org.solent.com504.oodd.web.cardtocvv", cardtocvv);
            // set numbers
            toCard.setCardnumber(cardto);
            toCard.setName(cardtoname);
            toCard.setEndDate(cardtoexpdate);
            toCard.setCvv(cardtocvv);

            message = "bank details are set";
            // do the transfer
        }

        if ("submiturl".equals(action)) {
            message = "url for the bank is set";
            url = (String) request.getParameter("url");
            propertiesDaoFile.setProperty("org.solent.com504.oodd.web.url", url);
        }

        model.addAttribute("message", message);
        model.addAttribute("cardto", cardto);
        model.addAttribute("cardtoname", cardtoname);
        model.addAttribute("cardtoexpdate", cardtoexpdate);
        model.addAttribute("cardtocvv", cardtocvv);
        model.addAttribute("url", url);

        return "shop-details";

    }

}
