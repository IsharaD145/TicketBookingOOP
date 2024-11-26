package com.ticketbooking.ticketBooking;

import java.io.Serializable;

public class TicketConfig implements Serializable {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetreivalRate;
    private int maxTicketCapacity;

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetreivalRate() {
        return customerRetreivalRate;
    }

    public void setCustomerRetreivalRate(int customerRetreivalRate) {
        this.customerRetreivalRate = customerRetreivalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }
}
