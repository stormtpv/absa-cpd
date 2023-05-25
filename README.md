# ABSA Assessment

### Create REST API, using Java Spring Boot, that provides CRUD capabilities on a database containing tables with the following schemas:

1. Customers
- Name, birth date, address, registration date
2. Products 
- Name, description, price, units available
3. Purchases
- Customer, Product, units bought, date

### System requirements:
1. If there are no products in stock, a sale cannot occur.
2. Neither products nor customers should be duplicated.
3. Create a stats endpoint that can be queried to find: the number of purchases made by customer, and the average expenditure per customer in a given time frame

### Optional Requirements:
1. Identify duplicate purchases and prevent them from occurring.