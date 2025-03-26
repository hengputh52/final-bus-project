package busbus;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
// Customer class implementing Authenticates and BusBookingInterface
public class Customer implements Authenticates, Booking {
    protected int customerID;
    protected String username;
    protected String email;
    protected String password;
    protected int age;
    protected char gender;
    protected String phoneNumber;
    protected String address;
    
    private static int customerCount = 0;
    private static ArrayList<Customer> customerList = new ArrayList<>();
    private ArrayList<String> bookingHistory = new ArrayList<>();
    private static ArrayList<BusBooking> busBooking = new ArrayList<>();
    

    // Constructor
    public Customer(String username, int age, char gender, String phone, String email, String address, String password) {
        if (email == null || email.isEmpty()) {
            throw new CustomerAlreadyExistsException("Email cannot be empty");
        }
        for (Customer customer : customerList) {
            if (customer.email.equals(email)) {
                throw new CustomerAlreadyExistsException(email);
            }
        }
        this.customerID = ++customerCount;
        this.username = username;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phone;
        this.address = address;
        this.password = password;
        customerList.add(this);
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    public static ArrayList<Customer> getCustomer() {
        return customerList;
    }

    public static ArrayList<BusBooking> getBusBooking() {
        return busBooking;
    }

protected int getCustomerID()
{
    return customerID;
}

    @Override
public void signUp() {
    Scanner input = new Scanner(System.in);

    try {
        System.out.print("Enter your Username: ");
        String username = input.nextLine();
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty!");
        }

        int age;
        while (true) {
            System.out.print("Enter your Age: ");
            try {
                age = input.nextInt();
                input.nextLine(); // Consume the newline character
                if (age <= 0) {
                    System.out.println("Age must be a positive number. Please try again.");
                    continue;
                }
                break; // Valid age entered
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer for age.");
                input.nextLine(); // Clear the invalid input
            }
        }

        System.out.print("Enter your Gender (M/F): ");
        char gender = input.next().charAt(0);
        input.nextLine();

        System.out.print("Enter your Phone Number: ");
        String phoneNumber = input.nextLine();
        if (phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty!");
        }

        String email;
        while (true) {
            System.out.print("Enter your Email: ");
            email = input.nextLine();
            if (email.isEmpty()) {
                System.out.println("Email cannot be empty!");
                continue;
            }

            // Check if the email already exists in the customer list
            boolean emailExists = false;
            for (Customer customer : customerList) {
                if (customer.email.equals(email)) {
                    emailExists = true;
                    break;
                }
            }

            if (emailExists) {
                System.out.println("This email is already registered. Please enter a different email.");
            } else {
                break; // Email is valid and not already used
            }
        }

        System.out.print("Enter your Address: ");
        String address = input.nextLine();
        if (address.isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty!");
        }

        System.out.print("Enter your Password: ");
        String password = input.nextLine();
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }

        // Create a new Customer
        new Customer(username, age, gender, phoneNumber, email, address, password);

        System.out.println("Sign-up successful for " + username);
    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    } finally {
        System.out.println("Thank you");
    }
}

@Override
public boolean login(String email, String password) {
    try {
        for (Customer customer : customerList) {
            if (customer.email.equals(email) && customer.password.equals(password)) {
                System.out.println("Login successful for " + email);
                return true;
            }
        }
        throw new InvalidLoginException();
    } catch (InvalidLoginException e) {
        System.out.println("Error: " + e.getMessage());
        return false;
    } finally {
        System.out.println("Login attempt completed.");
    }
}

public boolean login(String username, String password) {
    return this.username.equals(username) && this.password.equals(password);
}

@Override
public void BookTicket() {
    Scanner input = new Scanner(System.in);

    try {
        System.out.print("Enter Bus ID: ");
        int busID = input.nextInt();
        input.nextLine(); // Consume the newline character

        System.out.print("Enter Route ID: ");
        int routeID = input.nextInt();
        input.nextLine();

        System.out.print("Enter Pickup Stop ID: ");
        int pickupStopID = input.nextInt();
        input.nextLine();

        System.out.print("Enter Drop Stop ID: ");
        int dropStopID = input.nextInt();
        input.nextLine();

        System.out.print("Enter Booking Time (e.g., 10:00 AM): ");
        String bookingTime = input.nextLine();

        System.out.print("Enter Travel Date (e.g., 2025-03-25): ");
        String travelDate = input.nextLine();

        System.out.print("Do you want to book a return ticket? (yes/no): ");
        String returnOption = input.nextLine().trim().toLowerCase();

        String returnDate = null;
        if (returnOption.equals("yes")) {
            System.out.print("Enter Return Date (e.g., 2025-03-30): ");
            returnDate = input.nextLine();
        }

        System.out.print("Enter Total Seats to Book: ");
        int totalSeats = input.nextInt();
        input.nextLine();

        // Create a new booking and add it to the BusBooking list
        BusBooking newBooking = new BusBooking(customerID, busID, routeID, pickupStopID, dropStopID, bookingTime, travelDate, returnDate, totalSeats);
        BusBooking.getBusBooking().add(newBooking); // Add to the BusBooking list

        System.out.println("Booking successful! Your Booking ID is: " + newBooking.getBookingID());
    } catch (Exception e) {
        System.out.println("Error: Invalid input. Please try again.");
        input.nextLine(); // Clear the invalid input
    }
}

    @Override
    public boolean CancelBooking(int customerID, int bookingID) {
        try {
            // Check if the booking ID is valid
            boolean bookingFound = false;
    
            for (BusBooking booking : BusBooking.getBusBooking()) {
                if (booking.getBookingID() == bookingID && booking.getCustomerID() == customerID) {
                    BusBooking.getBusBooking().remove(booking); // Remove the booking
                    System.out.println("Booking with ID " + bookingID + " has been canceled for customer " + username);
                    bookingFound = true;
                    break;
                }
            }
    
            if (!bookingFound) {
                System.out.println("Error: Booking ID not found or does not belong to the customer.");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please enter a valid Booking ID.");
        }
    }

    @Override
    public void ViewHistory() {
        try {
            System.out.println("Booking History for " + username + ":");
    
            boolean hasBookings = false;
    
            // Iterate through the BusBooking list to find bookings for this customer
            for (BusBooking booking : BusBooking.getBusBooking()) {
                if (booking.getCustomerID() == this.customerID) {
                    System.out.println(booking);
                    hasBookings = true;
                }
            }
    
            if (!hasBookings) {
                System.out.println("No bookings found for this customer.");
            }
        } catch (Exception e) {
            System.out.println("Error: Unable to retrieve booking history.");
        }
    }

    // Logout method
    public void logout() {
        System.out.println(username + " has logged out.");
    }

    // toString method
    @Override
    public String toString() {
        return "Customer{" +
                "ID=" + customerID +
                ", Username='" + username + '\'' +
                ", Email='" + email + '\'' +
                '}';
    }



    // equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return customerID == customer.customerID &&
                Objects.equals(username, customer.username) &&
                Objects.equals(email, customer.email);
    }
}
