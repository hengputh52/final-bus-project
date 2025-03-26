package busbus;
import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

public class BusBookingGUI extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JComboBox<String> sourceBox, destinationBox, busTypeBox;
    private JTextField seatsField, bookingIdField;
    private JTextArea resultArea, historyArea;
    private Customer loggedInCustomer = null;
    private ArrayList<String> bookingHistory = new ArrayList<>(); // Store booking history

    public BusBookingGUI() {
        setTitle("Bus Booking System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels to CardLayout
        mainPanel.add(createAuthPanel(), "AuthPanel");
        mainPanel.add(createMainMenuPanel(), "MainMenuPanel");
        mainPanel.add(createBookingPanel(), "BookingPanel");
        mainPanel.add(createCancelPanel(), "CancelPanel");
        mainPanel.add(createHistoryPanel(), "HistoryPanel");

        add(mainPanel);
        cardLayout.show(mainPanel, "AuthPanel"); // Show the login/sign-up panel initially

        setVisible(true);
    }

    // Create the authentication panel (Log In and Sign Up)
    private JPanel createAuthPanel() {
        JPanel authPanel = new JPanel();
        authPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton loginButton = new JButton("Log In");
        JButton signUpButton = new JButton("Sign Up");

        authPanel.add(new JLabel("Welcome to the Bus Booking System!", SwingConstants.CENTER));
        authPanel.add(loginButton);
        authPanel.add(signUpButton);

        // Log In Action
        loginButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog("Enter Username:");
            String password = JOptionPane.showInputDialog("Enter Password:");

            for (Customer customer : Customer.getCustomer()) {
                if (customer.login(username, password)) {
                    loggedInCustomer = customer; // Set the logged-in customer
                    JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + username + "!");
                    cardLayout.show(mainPanel, "MainMenuPanel"); // Switch to the main menu panel
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        });

        // Sign Up Action
        signUpButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog("Enter Username:");
            String password = JOptionPane.showInputDialog("Enter Password:");
            String email = JOptionPane.showInputDialog("Enter Email:");

            try {
                Customer newCustomer = new Customer(username, 25, 'M', "1234567890", email, "123 Main St", password);
                loggedInCustomer = newCustomer; // Set the logged-in customer
                JOptionPane.showMessageDialog(null, "Sign-up successful! Welcome, " + username + "!");
                cardLayout.show(mainPanel, "MainMenuPanel"); // Switch to the main menu panel
            } catch (CustomerAlreadyExistsException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        return authPanel;
    }

    // Create the main menu panel (Booking, Cancel, View History)
    private JPanel createMainMenuPanel() {
        JPanel mainMenuPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton bookButton = new JButton("Booking");
        JButton cancelButton = new JButton("Cancel Booking");
        JButton viewHistoryButton = new JButton("View History");

        mainMenuPanel.add(new JLabel("Main Menu", SwingConstants.CENTER));
        mainMenuPanel.add(bookButton);
        mainMenuPanel.add(cancelButton);
        mainMenuPanel.add(viewHistoryButton);

        // Action Listeners
        bookButton.addActionListener(e -> {
            displayAvailableBuses(); // Populate available buses before showing the booking panel
            cardLayout.show(mainPanel, "BookingPanel");
        });
        cancelButton.addActionListener(e -> cardLayout.show(mainPanel, "CancelPanel"));
        viewHistoryButton.addActionListener(e -> {
            displayBookingHistory(); // Populate booking history before showing the history panel
            cardLayout.show(mainPanel, "HistoryPanel");
        });

        return mainMenuPanel;
    }

    // Create the booking panel
    private JPanel createBookingPanel() {
        JPanel bookingPanel = new JPanel(new BorderLayout());

        // Top Panel for Buttons
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back to Main Menu");
        buttonPanel.add(backButton);

        // Center Panel for Booking Details
        JPanel detailsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Booking Details"));

        detailsPanel.add(new JLabel("Source:"));
        sourceBox = new JComboBox<>(new String[]{"New York", "Los Angeles", "Chicago"});
        detailsPanel.add(sourceBox);

        detailsPanel.add(new JLabel("Destination:"));
        destinationBox = new JComboBox<>(new String[]{"Washington", "San Francisco", "Houston"});
        detailsPanel.add(destinationBox);

        detailsPanel.add(new JLabel("Bus Type:"));
        busTypeBox = new JComboBox<>(new String[]{"VIP", "Luxury", "Economy"});
        detailsPanel.add(busTypeBox);

        detailsPanel.add(new JLabel("Seats:"));
        seatsField = new JTextField();
        detailsPanel.add(seatsField);

        JButton confirmBookingButton = new JButton("Confirm Booking");
        detailsPanel.add(confirmBookingButton);

        // Bottom Panel for Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createTitledBorder("Available Buses"));

        bookingPanel.add(buttonPanel, BorderLayout.NORTH);
        bookingPanel.add(detailsPanel, BorderLayout.CENTER);
        bookingPanel.add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        // Action Listeners
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenuPanel"));
        confirmBookingButton.addActionListener(e -> handleBooking());

        return bookingPanel;
    }

    // Create the cancel panel
    private JPanel createCancelPanel() {
        JPanel cancelPanel = new JPanel(new BorderLayout());

        // Top Panel for Buttons
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back to Main Menu");
        buttonPanel.add(backButton);

        // Center Panel for Cancel Booking
        bookingIdField = new JTextField();
        JButton confirmCancelButton = new JButton("Confirm Cancel");

        JPanel cancelDetailsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        cancelDetailsPanel.setBorder(BorderFactory.createTitledBorder("Cancel Booking"));
        cancelDetailsPanel.add(new JLabel("Booking ID:"));
        cancelDetailsPanel.add(bookingIdField);
        cancelDetailsPanel.add(confirmCancelButton);

        cancelPanel.add(buttonPanel, BorderLayout.NORTH);
        cancelPanel.add(cancelDetailsPanel, BorderLayout.CENTER);

        // Action Listeners
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenuPanel"));
        confirmCancelButton.addActionListener(e -> handleCancelBooking());

        return cancelPanel;
    }

    // Create the history panel
    private JPanel createHistoryPanel() {
        JPanel historyPanel = new JPanel(new BorderLayout());

        // Top Panel for Buttons
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back to Main Menu");
        buttonPanel.add(backButton);

        // Center Panel for Booking History
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setBorder(BorderFactory.createTitledBorder("Booking History"));

        historyPanel.add(buttonPanel, BorderLayout.NORTH);
        historyPanel.add(new JScrollPane(historyArea), BorderLayout.CENTER);

        // Action Listeners
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenuPanel"));

        return historyPanel;
    }

    // Display available buses in the booking panel
    private void displayAvailableBuses() {
        StringBuilder busesInfo = new StringBuilder();
        for (BusDetail bus : BusDetail.busDetailList) {
            busesInfo.append("Bus ID: ").append(bus.getBusID())
                    .append(", Source: ").append(bus.getSource())
                    .append(", Destination: ").append(bus.getDestination())
                    .append(", Type: ").append(bus.getBusType())
                    .append(", Available Seats: ").append(bus.getAvailableSeat())
                    .append(", Total Seats: ").append(bus.getTotalSeat())
                    .append("\n");
        }
        resultArea.setText(busesInfo.toString());
    }

    // Display booking history in the history panel
    private void displayBookingHistory() {
        if (loggedInCustomer == null) {
            historyArea.setText("You must log in to view your booking history.");
            return;
        }

        if (bookingHistory.isEmpty()) {
            historyArea.setText("No bookings found.");
        } else {
            StringBuilder history = new StringBuilder("Booking History:\n");
            for (String record : bookingHistory) {
                history.append(record).append("\n");
            }
            historyArea.setText(history.toString());
        }
    }

    // Handle booking logic
    private void handleBooking() {
        if (loggedInCustomer == null) {
            JOptionPane.showMessageDialog(null, "You must log in or sign up to make a booking.");
            return;
        }

        String source = (String) sourceBox.getSelectedItem();
        String destination = (String) destinationBox.getSelectedItem();
        String busType = (String) busTypeBox.getSelectedItem();
        String seatsStr = seatsField.getText().trim();

        int seats;
        try {
            seats = Integer.parseInt(seatsStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid number of seats. Please enter a valid number.");
            return;
        }

        BusDetail selectedBus = null;

        // Match the bus based on user input
        for (BusDetail bus : BusDetail.busDetailList) {
            if (bus.getSource().equalsIgnoreCase(source) &&
                bus.getDestination().equalsIgnoreCase(destination) &&
                bus.getBusType().equalsIgnoreCase(busType)) {
                selectedBus = bus;
                break;
            }
        }

        if (selectedBus == null) {
            JOptionPane.showMessageDialog(null, "No bus found for your selection.");
            return;
        }

        // Attempt to book the seats
        if (selectedBus.bookSeat(seats)) {
            String bookingRecord = "Booking ID: " + (bookingHistory.size() + 1) +
                    ", Bus ID: " + selectedBus.getBusID() +
                    ", Seats: " + seats;
            bookingHistory.add(bookingRecord);

            JOptionPane.showMessageDialog(null, "Booking Confirmed!\n" +
                    "Bus ID: " + selectedBus.getBusID() + "\n" +
                    "Route: " + selectedBus.getSource() + " → " + selectedBus.getDestination() + "\n" +
                    "Bus Type: " + selectedBus.getBusType() + "\n" +
                    "Departure Time: " + selectedBus.getDepartureTime() + "\n" +
                    "Arrival Time: " + selectedBus.getArrivalTime() + "\n" +
                    "Fare: $" + selectedBus.getFare() + "\n" +
                    "Seats Booked: " + seats);

            // Reset input fields for the next booking
            sourceBox.setSelectedIndex(0);
            destinationBox.setSelectedIndex(0);
            busTypeBox.setSelectedIndex(0);
            seatsField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Not enough seats available.");
        }
    }

    // Handle cancel booking logic
    private void handleCancelBooking() {
        if (loggedInCustomer == null) {
            JOptionPane.showMessageDialog(null, "You must log in to cancel a booking.");
            return;
        }

        String bookingIdStr = bookingIdField.getText().trim();
        try {
            int bookingId = Integer.parseInt(bookingIdStr);
            if (bookingId > 0 && bookingId <= bookingHistory.size()) {
                bookingHistory.remove(bookingId - 1);
                JOptionPane.showMessageDialog(null, "Booking canceled successfully.");
                bookingIdField.setText(""); // Reset the input field
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Booking ID.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Booking ID.");
        }
    }

    public static void main(String[] args) {
        // Initialize some sample buses
        new BusDetail("New York", "Washington", "VIP", 40, "10:00 AM", "2:00 PM", 50.0);
        new BusDetail("Los Angeles", "San Francisco", "Luxury", 35, "8:00 AM", "12:00 PM", 40.0);
        new BusDetail("Chicago", "Houston", "Economy", 50, "6:00 AM", "4:00 PM", 30.0);

        // Launch the GUI
        new BusBookingGUI();
    }
}
