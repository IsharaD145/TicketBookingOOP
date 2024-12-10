package com.ticketbooking.ticketBooking;

import java.math.BigDecimal;
import java.util.Date;

public class Ticket {
    private String ticketId;
    private String eventName;
    private BigDecimal ticketPrice;
    private Date eventDate;

    public Ticket(String eventName, BigDecimal ticketPrice, Date eventDate) {
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.eventDate = eventDate;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

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
