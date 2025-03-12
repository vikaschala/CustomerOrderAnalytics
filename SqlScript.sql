-- ================================
-- 1. Create Tables
-- ================================

-- Create a table for customers
CREATE TABLE customers (
    id INT PRIMARY KEY AUTO_INCREMENT,         -- Customer ID (Auto Incremented)
    name VARCHAR(255) NOT NULL,                -- Customer's Name
    email VARCHAR(255) NOT NULL UNIQUE         -- Customer's Email (Unique)
);

-- Create a table for orders
CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,         -- Order ID (Auto Incremented)
    customer_id INT,                           -- Reference to the customer making the order
    status VARCHAR(50) DEFAULT 'PENDING',      -- Order Status (default is 'PENDING')
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp when the order was placed
    FOREIGN KEY (customer_id) REFERENCES customers(id)  -- Foreign Key: Customer ID
);

-- Create a table for order items (Products in the order)
CREATE TABLE order_items (
    id INT PRIMARY KEY AUTO_INCREMENT,         -- Order Item ID (Auto Incremented)
    order_id INT,                              -- Reference to the associated order
    product_name VARCHAR(255) NOT NULL,        -- Product Name
    category VARCHAR(100),                     -- Product Category
    quantity INT DEFAULT 1,                    -- Quantity of the product (default is 1)
    price DECIMAL(10, 2) NOT NULL,             -- Price of a single product
    FOREIGN KEY (order_id) REFERENCES orders(id) -- Foreign Key: Order ID
);

-- ================================
-- 2. SQL for Registering a Customer
-- ================================
INSERT INTO customers (name, email)
VALUES ('John Doe', 'john.doe@example.com');  -- Register a new customer (John Doe)

-- ================================
-- 3. SQL for Placing an Order
-- ================================

-- Insert a new order for customer with ID 1
INSERT INTO orders (customer_id, status)
VALUES (1, 'PENDING');   -- Order status set to 'PENDING'

-- Get the order id of the newly placed order (Assuming the last inserted order)
SET @order_id = LAST_INSERT_ID();  

-- Insert order items (product in the order)
INSERT INTO order_items (order_id, product_name, category, quantity, price)
VALUES (@order_id, 'Laptop', 'Electronics', 1, 999.99);   -- Product: Laptop with quantity 1 and price 999.99

-- ================================
-- 4. SQL to Retrieve Orders for a Specific Customer
-- ================================
SELECT * FROM orders
WHERE customer_id = 1;   -- Retrieve all orders for customer with ID 1

-- ================================
-- 5. SQL to Retrieve a Specific Order
-- ================================
SELECT * FROM orders
WHERE id = 1;   -- Retrieve the order with ID 1

-- ================================
-- 6. SQL to Cancel an Order
-- ================================
-- Delete all order items for order ID 1
DELETE FROM order_items WHERE order_id = 1;

-- Delete the order with ID 1
DELETE FROM orders WHERE id = 1;

-- ================================
-- 7. SQL to Get All Orders (Admin View)
-- ================================
SELECT * FROM orders;  -- Retrieve all orders for admin view

-- ================================
-- 8. SQL to Update the Status of an Order (Admin)
-- ================================
UPDATE orders
SET status = 'SHIPPED'  -- Update order status to 'SHIPPED'
WHERE id = 1;            -- Update order with ID 1

-- ================================
-- 9. SQL for Analytics Queries
-- ================================

-- SQL to get the most profitable customers (by total order value)
SELECT c.id, c.name, SUM(oi.quantity * oi.price) AS total_spent
FROM customers c
JOIN orders o ON c.id = o.customer_id
JOIN order_items oi ON o.id = oi.order_id
GROUP BY c.id
ORDER BY total_spent DESC   -- Order by total amount spent in descending order
LIMIT 10;  -- Top 10 most profitable customers

-- ================================
-- 10. SQL to Calculate Monthly Retention Rate
-- ================================
SELECT 
    COUNT(DISTINCT CASE WHEN order_date >= CURDATE() - INTERVAL 1 MONTH THEN o.customer_id END) AS retained_customers,
    COUNT(DISTINCT o.customer_id) AS total_customers_last_month
FROM orders o
WHERE order_date >= CURDATE() - INTERVAL 2 MONTH  -- Orders within the last 2 months
GROUP BY YEAR(order_date), MONTH(order_date);     -- Group by month and year

-- ================================
-- 11. SQL to Find the Longest Order Processing Times
-- ================================
SELECT o.id, o.order_date, MAX(oi.created_at) AS last_update,
       TIMESTAMPDIFF(SECOND, o.order_date, MAX(oi.created_at)) AS processing_time
FROM orders o
JOIN order_items oi ON o.id = oi.order_id
GROUP BY o.id
ORDER BY processing_time DESC   -- Order by processing time in descending order
LIMIT 10;  -- Top 10 longest processing times

-- ================================
-- 12. SQL to Get the Best and Worst Performing Product Categories
-- ================================
SELECT category, SUM(quantity * price) AS total_sales
FROM order_items
GROUP BY category
ORDER BY total_sales DESC;   -- Order by total sales in descending order

-- ================================
-- 13. SQL to Get Seasonal Revenue (Orders in Holiday Months)
-- ================================
SELECT MONTH(order_date) AS month, SUM(oi.quantity * oi.price) AS seasonal_revenue
FROM orders o
JOIN order_items oi ON o.id = oi.order_id
WHERE MONTH(order_date) IN (11, 12)  -- For November and December (holiday season)
GROUP BY MONTH(order_date)
ORDER BY seasonal_revenue DESC;  -- Order by seasonal revenue in descending order

-- ================================
-- 14. SQL to Get the Most Common Order Sizes
-- ================================
SELECT oi.order_id, SUM(oi.quantity) AS total_items
FROM order_items oi
GROUP BY oi.order_id
ORDER BY total_items DESC   -- Order by total quantity per order in descending order
LIMIT 10;  -- Top 10 most common order sizes
