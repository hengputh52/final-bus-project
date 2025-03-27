package busbus;

import java.util.Scanner;

public class Bus {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Customer loggedInCustomer = null;

        while (true) {
            if (loggedInCustomer == null) {
                System.out.println("1. Sign Up");
                System.out.println("2. Log In");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = input.nextInt();
                input.nextLine(); // Consume newline

                switch (choice) {
                    case 1: // Sign Up
                        // Customer newCustomer = new Customer();
                        // newCustomer.signUp();
                        break;

                    case 2: // Log In
                        System.out.print("Enter username: ");
                        String username = input.nextLine();
                        System.out.print("Enter password: ");
                        String password = input.nextLine();

                        for (Customer customer : Customer.getCustomer()) {
                            if (customer.login(username, password)) {
                                loggedInCustomer = customer;
                                System.out.println("Login successful! Welcome, " + username + "!");
                                break;
                            }
                        }

                        if (loggedInCustomer == null) {
                            System.out.println("Invalid username or password. Please try again.");
                        }
                        break;

                    case 3: // Exit
                        System.out.println("Exiting now...");
                        input.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("1. View Available Buses");
                System.out.println("2. Make a Booking");
                System.out.println("3. Logout");
                System.out.print("Enter your choice: ");

                int choice = input.nextInt();
                input.nextLine(); // Consume newline

                switch (choice) {
                    case 1: // View Available Buses
                        System.out.println("Available Buses:");
                        for (BusDetail bus : BusDetail.busDetailList) {
                            System.out.println(bus);
                        }
                        break;

                    case 2: // Make a Booking
                        System.out.println("Enter your source:");
                        String source = input.nextLine();

                        System.out.println("Enter your destination:");
                        String destination = input.nextLine();

                        System.out.println("Enter bus type (VIP/Luxury/Economy):");
                        String busType = input.nextLine();

                        BusDetail selectedBus = null;
                        for (BusDetail bus : BusDetail.busDetailList) {
                            if (bus.getSource().equalsIgnoreCase(source) &&
                                bus.getDestination().equalsIgnoreCase(destination) &&
                                bus.getBusType().equalsIgnoreCase(busType)) {
                                selectedBus = bus;
                                break;
                            }
                        }

                        if (selectedBus == null) {
                            System.out.println("No bus found for your selection.");
                            continue;
                        }

                        System.out.println("Enter number of seats to book:");
                        int seats = input.nextInt();

                        if (selectedBus.bookSeat(seats)) {
                            System.out.println("Booking Confirmed!");
                            System.out.println("----- RECEIPT -----");
                            System.out.println("Bus ID: " + selectedBus.getBusID());
                            System.out.println("Route: " + selectedBus.getSource() + " → " + selectedBus.getDestination());
                            System.out.println("Bus Type: " + selectedBus.getBusType());
                            System.out.println("Seats Booked: " + seats);
                            System.out.println("-------------------");
                        } else {
                            System.out.println("Not enough seats available.");
                        }
                        break;

                    case 3: // Logout
                        System.out.println("Logging out...");
                        loggedInCustomer = null;
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}
