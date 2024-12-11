package com.ticketbooking.ticketBooking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class for managing database connections and initializing tables.
 * Provides methods to connect to the H2 database and create or reset tables.
 */
public class DatabaseUtil {
    /**
     * The JDBC URL for connecting to the H2 database.
     */
    private static final String JDBC_URL = "jdbc:h2:./Ticketpool";

    /**
     * The username for the H2 database connection.
     */
    private static final String USER = "sa";

    /**
     * The password for the H2 database connection.
     */
    private static final String PASSWORD = "";


    /**
     * Establishes and returns a connection to the database.
     *
     * @return a {@link Connection} object representing the database connection.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    /**
     * Initializes the database tables by creating them if they do not exist.
     * Drops existing tables before creating new ones to ensure a clean state.
     */
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
            // Drop existing tables
            stmt.execute(dropConsumerTable);
            stmt.execute(dropVendorTable);
            stmt.execute(dropTicketTable);

            // Create new tables
            stmt.execute(createTicketTable);
            stmt.execute(createConsumerTable);
            stmt.execute(createVendorTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
