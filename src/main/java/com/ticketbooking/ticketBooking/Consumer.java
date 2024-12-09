package com.ticketbooking.ticketBooking;

import java.util.Random;

public class Consumer implements Runnable {
    private TicketPool ticketPool;
    private int buyingFrequncy;
    private int maximumNumberOfTickets;

    public Consumer(TicketPool ticketPool, int buyingFrequncy, int maximumNumberOfTickets){
        this.ticketPool=ticketPool;
        this.buyingFrequncy=buyingFrequncy;
        this.maximumNumberOfTickets=maximumNumberOfTickets;
    }

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
