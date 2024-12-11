package com.ticketbooking.ticketBooking.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a ticket for an event in the ticket booking system.
 * Contains details such as the ticket ID, event name, price, and event date.
 */
public class Ticket {
    private String ticketId;
    private String eventName;
    private BigDecimal ticketPrice;
    private Date eventDate;


    /**
     * Constructs a Ticket with the specified event name, ticket price, and event date.
     *
     * @param eventName   the name of the event associated with the ticket.
     * @param ticketPrice the price of the ticket.
     * @param eventDate   the date of the event.
     */
    public Ticket(String eventName, BigDecimal ticketPrice, Date eventDate) {
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.eventDate = eventDate;
    }

    /**
     * Retrieves the ticket ID.
     *
     * @return the ID of the ticket.
     */
    public String getTicketId() {
        return ticketId;
    }

    /**
     * Sets the ticket ID.
     *
     * @param ticketId the ID to be assigned to the ticket.
     */
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * Retrieves the name of the event.
     *
     * @return the name of the event.
     */
    public String getEventName() {
        return eventName;
    }


    /**
     * Retrieves the price of the ticket.
     *
     * @return the price of the ticket.
     */
    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }


    /**
     * Returns a string representation of the Ticket object.
     *
     * @return a string containing the details of the ticket.
     */
    // Other getters and setters...
    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", eventDate=" + eventDate +
                '}';
    }
}
