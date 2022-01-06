
<h1>COM528 AE2 Documentation</h1>

<h2 style="text-decoration: underline">Important Information:</h2>
<p>Admin username: globaladmin<br>
Admin password: globaladmin</p>

---

<h2 style="text-decoration: underline">Introduction:</h2>
<p>This is the documentation that I have done for my software. It highlights the different interactions many actions and actors have in the system while they navigate and work their way through the app. </p>


<h2 style="text-decoration: underline">Use Case Diagram:</h2>
<p>A simple use case diagram showing the different actions users can take while using the application:</p>
<img src="documentation-images/usecase-diagram.png">

---

<h2 style="text-decoration: underline">Robust Diagram:</h2>
<p>A robust diagram of the project:</p>
<img src="images/robust_diagram.png">

---



<h2 style="text-decoration: underline">Test Plan:</h2><br>

<h2>Test 1:</h2>

|          Test           |                            Expected Result                             |                            Actual Result                             |          Success or Failure           | Action needed |
| :---------------------: | :--------------------------------------------------------------------: | :------------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Web pages and bootstrap | All web pages to load correctly with bootstrap working within them all | Every page loads as intended and bootstrap is used within every page | Success  |     none      |

<br>

<h2>Test 2:</h2>

|  Test  |                                         Expected Result                                         |                         Actual Result                          |          Success or Failure           | Action needed |
| :----: | :---------------------------------------------------------------------------------------------: | :------------------------------------------------------------: | :---------------------------------: | :-----------: |
| User and Admin logins | User and Admin can succesfully log in the aplication with the correct account details, otherwise they get an error | The admin and users can log in succesfully while using the correct account details only | Success  |     none      |

<br>

<h2>Test 3:</h2>

|     Test     |                                       Expected Result                                        |             Actual Result              |          Success or Failure           | Action needed |
| :----------: | :------------------------------------------------------------------------------------------: | :------------------------------------: | :---------------------------------: | :-----------: |
| User shopping | The user should be able to view the available items and add them to a cart | The user can succesfully add items to the shopping cart, viewing quantity and getting a total amount | Success |     none      |

<br>

<h2>Test 4:</h2>

|         Test         |                                Expected Result                                 |         Actual Result          |         Success or Failure          | Action needed |
| :------------------: | :----------------------------------------------------------------------------: | :----------------------------: | :---------------------------------: | :-----------: |
| Check out/Transaction | When the user is done shopping they can checkout using their card details and make a transaction happen with the total amount | The transaction happens with no errors to the user's account taking in account the total amount | Success |     none      |

<br>

<h2>Test 5:</h2>

|            Test             |                                      Expected Result                                      |                          Actual Result                          |          Success or Failure           | Action needed |
| :-------------------------: | :---------------------------------------------------------------------------------------: | :-------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Logger tracks transactions | The logger should track all transactions made regardless of correct or incorrect information has been given | The logger tracks the card number of the user and the amount paid while also logging erros | Success |     none      |

<br>

<h2>Test 6:</h2>

|    Test     |                                       Expected Result                                       |                      Actual Result                      |          Success or Failure           | Action needed |
| :---------: | :-----------------------------------------------------------------------------------------: | :-----------------------------------------------------: | :---------------------------------: | :-----------: |
| User orders | Logged in users should be able to view their orders | X | X |    X      |

<br>

<h2>Test 7:</h2>

|             Test              |                                                      Expected Result                                                      |                       Actual Result                        |          Success or Failure           | Action needed |
| :---------------------------: | :-----------------------------------------------------------------------------------------------------------------------: | :--------------------------------------------------------: | :---------------------------------: | :-----------: |
| Admin modify users | The admin should be able to modify users such as changing their status | The admin can succesfully view the modify user jsp and modify users | Success |     none      |

<br>

<h2>Test 8:</h2>

|  Test   |                                 Expected Result                                 |                           Actual Result                            |         Success or Failure           | Action needed |
| :-----: | :-----------------------------------------------------------------------------: | :----------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Admin catalog | Admin should be able to add and remove items from the list of available items | X | X |    X      |

<br>

<h2>Test 9:</h2>

|        Test         |                    Expected Result                     |                         Actual Result                          |          Success or Failure           | Action needed |
| :-----------------: | :----------------------------------------------------: | :------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Admin shop details | The admin should be able to set the shop details such as url and card details that will be used during the transaction | The admin can succesfully view the shop details page and add the url and card details to be used during a transaction | Success |     none      |

<br>

<h2>Test 10:</h2>

|  Test   |                                 Expected Result                                 |                           Actual Result                            |         Success or Failure           | Action needed |
| :-----: | :-----------------------------------------------------------------------------: | :----------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Admin viewing orders | Admin should be able to view all the orders made from all users | X | X |    X      |

<br>

<h2>Test 11:</h2>

|  Test   |                                 Expected Result                                 |                           Actual Result                            |         Success or Failure           | Action needed |
| :-----: | :-----------------------------------------------------------------------------: | :----------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Incorrect Transaction | If any information is wrong during the transaction, the user should be notified and brough back to the checkout page | The user is succesfully brought back to the checkout page if any error is wrong and it is also logged | Success |    none      |

<br>
