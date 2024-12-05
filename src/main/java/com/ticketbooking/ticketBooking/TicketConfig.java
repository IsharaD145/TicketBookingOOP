package com.ticketbooking.ticketBooking;

import java.io.Serializable;

public class TicketConfig implements Serializable {
    private int totalTicketsByVendor;
    private int totalTicketsByConsumer;
    private int ticketReleaseRate;
    private int customerRetreivalRate;

    @Override
    public String toString() {
        return "TicketConfig{" +
                "totalTicketsByVendor=" + totalTicketsByVendor +
                ", totalTicketsByConsumer=" + totalTicketsByConsumer +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetreivalRate=" + customerRetreivalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                '}';
    }

    private int maxTicketCapacity;

public int getTotalTicketsByVendor(){
    return totalTicketsByVendor;
}

public void setTotalTicketsByVendor(int totalTicketsByVendor){
    this.totalTicketsByVendor=totalTicketsByVendor;
}

public int getTotalTicketsByConsumer(){
    return totalTicketsByConsumer;
}

public void setTotalTicketsByConsumer(int totalTicketsByConsumer){
    this.totalTicketsByConsumer=totalTicketsByConsumer;
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
