import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShopItemApp {
    private ShopItemDao dao;
    private JFrame frame;
    private JTextField idField;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextArea outputArea;
    private DefaultTableModel tableModel;
    private JTable itemTable;

    public ShopItemApp() {
        dao = new ShopItemDao();
        initComponents();
    }
   

    private void initComponents() {
        frame = new JFrame("Shop Item Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.getContentPane().add(mainPanel);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        JPanel buttonPanel = new JPanel();
        inputPanel.add(buttonPanel);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(new CreateButtonListener());
        buttonPanel.add(createButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new UpdateButtonListener());
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new DeleteButtonListener());
        buttonPanel.add(deleteButton);

        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

       // frame.setVisible(true);

       // Create the table model and configure the JTable
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Description", "Price", "Quantity"}, 0);
        itemTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(itemTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Add the "Refresh" button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new RefreshButtonListener());
        buttonPanel.add(refreshButton);

        frame.setVisible(true);
    }

  // RefreshButtonListener class
    private class RefreshButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                List<ShopItem> items = dao.readAllItems();

                // Clear the table model
                tableModel.setRowCount(0);

                // Add items to the table model
                for (ShopItem item : items) {
                    tableModel.addRow(new Object[]{
                            item.getId(),
                            item.getName(),
                            item.getDescription(),
                            item.getPrice(),
                            item.getQuantity()
                    });
                }

                outputArea.setText("Table refreshed.\n");
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage() + "\n");
            }
        }
}
    private class CreateButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String name = nameField.getText().trim();
                String description = descriptionField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int quantity = Integer.parseInt(quantityField.getText().trim());

                ShopItem newItem = new ShopItem(name, description, price, quantity);
                dao.createItem(newItem);
                outputArea.setText("Item created successfully.\n");
            } catch (NumberFormatException ex) {
                outputArea.setText("Error: Invalid price or quantity.\n");
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage() + "\n");
            }
        }
    }

    private class UpdateButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                String description = descriptionField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int quantity = Integer.parseInt(quantityField.getText().trim());

                ShopItem itemToUpdate = new ShopItem(id, name, description, price, quantity);
                dao.updateItem(itemToUpdate);
                outputArea.setText("Item updated successfully.\n");
            } catch (NumberFormatException ex) {
                outputArea.setText("Error: Invalid ID, price, or quantity.\n");
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage() + "\n");
}
}
}

private class DeleteButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText().trim());

            dao.deleteItem(id);
            outputArea.setText("Item deleted successfully.\n");
        } catch (NumberFormatException ex) {
            outputArea.setText("Error: Invalid ID.\n");
        } catch (Exception ex) {
            outputArea.setText("Error: " + ex.getMessage() + "\n");
        }
    }
}

public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ShopItemApp();
            }
        });
    }
}
