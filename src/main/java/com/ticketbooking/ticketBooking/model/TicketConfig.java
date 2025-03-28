package com.ticketbooking.ticketBooking.model;


public class TicketConfig{


    private int totalTicketsByVendor;
    private int totalTicketsByConsumer;
    private int ticketReleaseRate;
    private int customerRetreivalRate;

    private int noOfVendors;
    private int noOfConsumers;


    /**
     * Returns a string representation of the TicketConfig object.
     *
     * @return a string containing the details of the ticket configuration.
     */

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


    public int getNoOfVendors() {
        return noOfVendors;
    }

    public void setNoOfVendors(int noOfVendors) {
        this.noOfVendors = noOfVendors;
    }

    public int getNoOfConsumers() {
        return noOfConsumers;
    }

    public void setNoOfConsumers(int noOfConsumers) {
        this.noOfConsumers = noOfConsumers;
    }

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


