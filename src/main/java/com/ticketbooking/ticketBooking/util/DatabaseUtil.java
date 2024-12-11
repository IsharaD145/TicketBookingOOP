package com.ticketbooking.ticketBooking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String JDBC_URL = "jdbc:h2:./Ticketpool";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public static void initializeTables() {
        String dropConsumerTable = "DROP TABLE IF EXISTS Consumer";
        String dropVendorTable = "DROP TABLE IF EXISTS Vendor";
        String dropTicketTable = "DROP TABLE IF EXISTS Ticket";

        String createTicketTable = "CREATE TABLE IF NOT EXISTS Ticket (" +
                "ticket_ID VARCHAR(255) PRIMARY KEY, " +
                "event_name VARCHAR(255), " +
                "ticket_price DECIMAL(20, 2))";

        String createConsumerTable = "CREATE TABLE IF NOT EXISTS Consumer (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "consumer_name VARCHAR(255), " +
                "vip_status BOOLEAN,"+
                "ticket_ID VARCHAR(255), " +
                "FOREIGN KEY (ticket_ID) REFERENCES Ticket(ticket_ID) ON DELETE CASCADE)";

        String createVendorTable = "CREATE TABLE IF NOT EXISTS Vendor (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "vendor_name VARCHAR(255), " +
                "ticket_ID VARCHAR(255), " +
                "FOREIGN KEY (ticket_ID) REFERENCES Ticket(ticket_ID) ON DELETE CASCADE)";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(dropConsumerTable);
            stmt.execute(dropVendorTable);
            stmt.execute(dropTicketTable);

            stmt.execute(createTicketTable);
            stmt.execute(createConsumerTable);
            stmt.execute(createVendorTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
