<h1 align="center" style="font-size: 36px;">🚀 Customer Order Analytics API</h1>

<p align="center" style="font-size: 20px;">
  A <strong>Spring Boot-based API</strong> to manage customer data and orders. Includes endpoints for registration, order placement, analytics, and admin functionality. Powered by an <strong>H2 database</strong>.
</p>
<p align="center">
  <img src="https://img.shields.io/badge/Java-17-blue" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen" alt="Spring Boot">
  <img src="https://img.shields.io/badge/H2-Database-orange" alt="H2 Database">
</p>

---

<h2 style="font-size: 28px;">✨ Features</h2>

<ul style="font-size: 18px;">
  <li><strong>Customer Management</strong>:
    <ul>
      <li>Register a new customer.</li>
      <li>Retrieve orders for a customer.</li>
    </ul>
  </li>
  <li><strong>Order Management</strong>:
    <ul>
      <li>Place an order.</li>
      <li>Cancel an order.</li>
    </ul>
  </li>
  <li><strong>Admin Functionality</strong>:
    <ul>
      <li>View and manage all orders.</li>
      <li>Update order statuses.</li>
    </ul>
  </li>
  <li><strong>Analytics</strong>:
    <ul>
      <li>Most profitable customers.</li>
      <li>Seasonal revenue trends.</li>
      <li>Retention rates.</li>
      <li>Product category performance.</li>
      <li>Common order sizes.</li>
    </ul>
  </li>
</ul>

---

<h2 style="font-size: 28px;">📋 Prerequisites</h2>

<ul style="font-size: 18px;">
  <li><strong>Java 17+</strong> (Spring Boot or similar Java-based frameworks).</li>
  <li><strong>Maven</strong> (for dependency management and building the project).</li>
  <li><strong>H2 Database</strong> (embedded in-memory database for development or file-based for persistence).</li>
  <li><strong>Postman</strong> (for testing API endpoints).</li>
</ul>

---

<h2 style="font-size: 28px;">🛠️ Setup</h2>

<h3 style="font-size: 24px;">1. Clone the Repository</h3>

<pre style="font-size: 18px;">
<code>git clone https://github.com/your_username/customer-order-analytics.git
cd customer-order-analytics</code>
</pre>

<h3 style="font-size: 24px;">2. Install Dependencies</h3>

<p style="font-size: 18px;">
  Use <strong>Maven</strong> to install dependencies:
</p>

<pre style="font-size: 18px;">
<code>mvn clean install</code>
</pre>

<h3 style="font-size: 24px;">3. Database Setup</h3>

<p style="font-size: 18px;">
  <strong>In-Memory H2</strong>: No setup needed. The database is created automatically when the application runs.
</p>

<p style="font-size: 18px;">
  <strong>Persistent H2</strong>: Update <code>application.properties</code>:
</p>

<pre style="font-size: 18px;">
<code>spring.datasource.url=jdbc:h2:file:/path/to/your/database/customer_order_analytics;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect</code>
</pre>

<h3 style="font-size: 24px;">4. Run the API</h3>

<p style="font-size: 18px;">
  Use <strong>Maven</strong> to run the API:
</p>

<pre style="font-size: 18px;">
<code>mvn spring-boot:run</code>
</pre>

<h3 style="font-size: 24px;">5. Access the API</h3>

<p style="font-size: 18px;">
  Once the application is running, you can access the API at:
</p>

<pre style="font-size: 18px;">
<code>http://localhost:8088</code>
</pre>

---

<h2 style="font-size: 28px;">🧪 Testing with Postman</h2>

<p style="font-size: 18px;">
  1. Import the <code>customer_order_analytics.postman_collection.json</code> file into Postman.
</p>

<p style="font-size: 18px;">
  2. Test endpoints for:
</p>

<ul style="font-size: 18px;">
  <li>Registering a new customer.</li>
  <li>Placing an order.</li>
  <li>Viewing orders for a customer.</li>
  <li>Canceling an order.</li>
  <li>Updating order statuses (admin only).</li>
  <li>Viewing analytics (e.g., most profitable customers, monthly retention rates).</li>
</ul>

---

<h2 style="font-size: 28px;">🌐 API Endpoints</h2>

<h3 style="font-size: 24px;">Customer Operations</h3>

<table style="font-size: 18px;">
  <tr>
    <th>Endpoint</th>
    <th>Method</th>
    <th>Description</th>
  </tr>
  <tr>
    <td><code>/customers</code></td>
    <td>POST</td>
    <td>Register a new customer.</td>
  </tr>
  <tr>
    <td><code>/customers/:customer_id/orders</code></td>
    <td>POST</td>
    <td>Place an order for a customer.</td>
  </tr>
  <tr>
    <td><code>/customers/:customer_id/orders</code></td>
    <td>GET</td>
    <td>Retrieve orders for a customer.</td>
  </tr>
  <tr>
    <td><code>/customers/:customer_id/orders/:order_id</code></td>
    <td>DELETE</td>
    <td>Cancel an order.</td>
  </tr>
</table>

<h3 style="font-size: 24px;">Admin Operations</h3>

<table style="font-size: 18px;">
  <tr>
    <th>Endpoint</th>
    <th>Method</th>
    <th>Description</th>
  </tr>
  <tr>
    <td><code>/admin/orders</code></td>
    <td>GET</td>
    <td>View all orders.</td>
  </tr>
  <tr>
    <td><code>/admin/orders/:order_id/status</code></td>
    <td>PUT</td>
    <td>Update order status.</td>
  </tr>
</table>

<h3 style="font-size: 24px;">Analytics</h3>

<table style="font-size: 18px;">
  <tr>
    <th>Endpoint</th>
    <th>Method</th>
    <th>Description</th>
  </tr>
  <tr>
    <td><code>/analytics/top-customers</code></td>
    <td>GET</td>
    <td>Top 10 profitable customers.</td>
  </tr>
  <tr>
    <td><code>/analytics/retention</code></td>
    <td>GET</td>
    <td>Monthly retention rate.</td>
  </tr>
  <tr>
    <td><code>/analytics/seasonal-revenue</code></td>
    <td>GET</td>
    <td>Seasonal revenue trends.</td>
  </tr>
  <tr>
    <td><code>/analytics/order-sizes</code></td>
    <td>GET</td>
    <td>Most common order sizes.</td>
  </tr>
</table>
