package com.ticketbooking.ticketBooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private Vector<Ticket> tickets = new Vector<>();
    private int maxTickets;
    private static int ticketCounter = 0;

    private static final Lock dbLock = new ReentrantLock();

    public TicketPool(int maxTickets) {
        this.maxTickets = maxTickets;
    }

    // Method to add tickets
    public synchronized void addTicket(Ticket ticket) throws SQLException {
        while (tickets.size() >= maxTickets) {
            try {
                System.out.println(Thread.currentThread().getName() + ": Pool is full, waiting to buy tickets.");
                TicketWebSocketHandler.broadcast(Thread.currentThread().getName() + ": Pool is full, waiting to buy tickets.");
                wait();
            } catch (InterruptedException e) {
                System.err.println("Add operation interrupted.");
                TicketWebSocketHandler.broadcast("Add operation interrupted.");

            }
        }
        ticketCounter++;
        ticket.setTicketId("Ticket-" + ticketCounter);
        tickets.add(ticket);
        try{
        Connection conn = DatabaseUtil.getConnection();
        // Insert Ticket
        String insertTicket = "INSERT INTO Ticket (ticket_ID, event_name, ticket_price) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertTicket)) {
            ps.setString(1, ticket.getTicketId());
            ps.setString(2, ticket.getEventName());
            ps.setBigDecimal(3, ticket.getTicketPrice());
            ps.executeUpdate();
        }}catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            Connection conn = DatabaseUtil.getConnection();
            // Insert Ticket
            String insertVendor = "INSERT INTO Vendor(vendor_name,ticket_ID) VALUES(?,?)";
            try(PreparedStatement vend = conn.prepareStatement(insertVendor)){
                vend.setString(1,Thread.currentThread().getName());
                vend.setString(2, ticket.getTicketId());
                vend.executeUpdate();
            }}catch (SQLException e) {
            e.printStackTrace();
        }

        notifyAll();
        System.out.println(Thread.currentThread().getName() + " added: " + ticket+" Total tickets left: "+tickets.size());
        TicketWebSocketHandler.broadcast(Thread.currentThread().getName() + " added: " + ticket+" Total tickets left: "+tickets.size());

    }

    // Method to buy tickets
    public synchronized void buyTicket() {
        while (tickets.isEmpty()) {
            try {
                System.out.println(Thread.currentThread().getName() + ": No tickets available, waiting to buy.");
                TicketWebSocketHandler.broadcast(Thread.currentThread().getName() + ": No tickets available, waiting to buy.");
                wait();
            } catch (InterruptedException e) {
                System.err.println("Buy operation interrupted.");
            }
        }

        Ticket ticket = tickets.remove(0);
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " bought: " + ticket+" Total tickets left: "+tickets.size());
        TicketWebSocketHandler.broadcast(Thread.currentThread().getName() + " bought: " + ticket+" Total tickets left: "+tickets.size());

        try{
            Connection connection = DatabaseUtil.getConnection();
            String insertConsumer = "INSERT INTO Consumer (consumer_name,ticket_ID) VALUES(?,?)";
            try(PreparedStatement consumer = connection.prepareStatement(insertConsumer)){
                consumer.setString(1,Thread.currentThread().getName());
                consumer.setString(2,ticket.getTicketId());
                consumer.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
