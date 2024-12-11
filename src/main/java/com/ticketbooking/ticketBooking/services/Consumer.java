package com.ticketbooking.ticketBooking.services;

import com.ticketbooking.ticketBooking.services.TicketPool;
/**
 * Represents a consumer in the ticket booking simulation.
 * Consumers buy tickets from the ticket pool at a specified frequency and quantity.
 */
public class Consumer implements Runnable {
    private TicketPool ticketPool;
    private int buyingFrequncy;
    private int maximumNumberOfTickets;
    private boolean vipstatus;



    /**
     * Constructs a new Consumer instance.
     *
     * @param ticketPool           the ticket pool from which tickets are bought.
     * @param buyingFrequncy      the frequency at which tickets are bought, in seconds.
     * @param maximumNumberOfTickets the maximum number of tickets the consumer can buy at one time.
     * @param vipstatus            the VIP status of the consumer (if true, the consumer has priority).
     */
    public Consumer(TicketPool ticketPool, int buyingFrequncy, int maximumNumberOfTickets, boolean vipstatus){
        this.ticketPool=ticketPool;
        this.buyingFrequncy=buyingFrequncy;
        this.maximumNumberOfTickets=maximumNumberOfTickets;
        this.vipstatus=vipstatus;
    }

    /**
     * The main logic for the consumer thread.
     * Continuously attempts to buy tickets from the pool at the specified frequency until interrupted.
     */
    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            int numberOfBuyingTickets = (int) (Math.random() * maximumNumberOfTickets) + 1;
            for (int i = 0;i<numberOfBuyingTickets;i++) {
                ticketPool.buyTicket();

            }
            try {
                Thread.sleep(buyingFrequncy * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(Thread.currentThread().getName() + "Is interrupted");
    }
}
