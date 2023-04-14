import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopItemDao {
    private final String url = "jdbc:mysql://localhost:3307/shop_management";
    private final String user = "root";
    private final String password = "admin";

    // Connect to the database
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Create (insert) a new shop item
    public void createItem(ShopItem item) {
        String query = "INSERT INTO items (name, description, price, quantity) VALUES (?, ?, ?, ?)";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setDouble(3, item.getPrice());
            statement.setInt(4, item.getQuantity());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Read (select) all shop items
    public List<ShopItem> readAllItems() {
        List<ShopItem> items = new ArrayList<>();
        String query = "SELECT * FROM items";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                items.add(new ShopItem(id, name, description, price, quantity));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return items;
    }

    // Update an existing shop item
    public void updateItem(ShopItem item) {
        String query = "UPDATE items SET name = ?, description = ?, price = ?, quantity = ? WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setDouble(3, item.getPrice());
            statement.setInt(4, item.getQuantity());
            statement.setInt(5, item.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete a shop item
    public void deleteItem(int id) {
        String query = "DELETE FROM items WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
