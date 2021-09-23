package net.javaguides.shop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.javaguides.shop.model.Product;

public class ProductDAO {
	
	String jdbcURL = "jdbc:mysql://localhost:3306/java_shop?useSSL=false";
    String jdbcUsername = "root";
    String jdbcPassword = "";
	
	String insert_product="insert into product (product_name, product_price, product_description) VALUES  (?, ?, ?);";
	String select_product_by_id = "select product_id,product_name, product_price, product_description from product where product_id =?";
	String select_all_product = "select * from product";
	String delete_product = "delete from product where product_id = ?;";
  	String update_product = "update product set product_name = ?,product_price= ?, product_description =? where product_id = ?;";
  	
  	public ProductDAO() {}
  	
  	protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
  	public void insertProduct(Product product) throws SQLException {
        System.out.println(insert_product);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insert_product)) {
            preparedStatement.setString(1, product.getProduct_name());
            preparedStatement.setInt(2, product.getProduct_price());
            preparedStatement.setString(3, product.getProduct_description());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Product selectProduct(int product_id) {
        Product product = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(select_product_by_id);) {
            preparedStatement.setInt(1, product_id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String product_name = rs.getString("product_name");
                int product_price = rs.getInt("product_price");
                String product_description = rs.getString("product_description");
                product = new Product(product_id,product_name, product_price, product_description);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;
    }

    public List < Product > selectAllProducts() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < Product > products = new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(select_all_product);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int product_id = rs.getInt("product_id");
                String product_name = rs.getString("product_name");
                int product_price = rs.getInt("product_price");
                String product_description = rs.getString("product_description");
                products.add(new Product(product_id, product_name, product_price, product_description));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }

    public boolean deleteProduct(int product_id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(delete_product);) {
            statement.setInt(1, product_id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateProduct(Product product) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(update_product);) {
            statement.setString(1, product.getProduct_name());
            statement.setInt(2, product.getProduct_price());
            statement.setString(3, product.getProduct_description());
            statement.setInt(4, product.getProduct_id());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
