package com.ticketbooking.ticketBooking;

import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private Vector<Ticket> tickets = new Vector<>();
    private int maxTickets;
    private static int ticketCounter = 0;

    public TicketPool(int maxTickets) {
        this.maxTickets = maxTickets;
    }

    // Method to add tickets
    public synchronized void addTicket(Ticket ticket) {
        while (tickets.size() >= maxTickets) {
            try {
                System.out.println(Thread.currentThread().getName() + ": Pool is full, waiting to add tickets.");
                wait();
            } catch (InterruptedException e) {
                System.err.println("Add operation interrupted.");
                return;
            }
        }
        ticketCounter++;
        ticket.setTicketId("Ticket-" + ticketCounter);
        tickets.add(ticket);
        System.out.println(Thread.currentThread().getName() + " added: " + ticket+" Total tickets left: "+tickets.size());
        notifyAll();
    }

    // Method to buy tickets
    public synchronized Ticket buyTicket() {
        while (tickets.isEmpty()) {
            try {
                System.out.println(Thread.currentThread().getName() + ": No tickets available, waiting to buy.");
                wait();
            } catch (InterruptedException e) {
                System.err.println("Buy operation interrupted.");
                return null;
            }
        }
        Ticket ticket = tickets.remove(0);
        System.out.println(Thread.currentThread().getName() + " bought: " + ticket+" Total tickets left: "+tickets.size());
        notifyAll();
        return ticket;
    }
}
