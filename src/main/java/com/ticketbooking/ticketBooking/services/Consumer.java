package com.ticketbooking.ticketBooking.services;

import com.ticketbooking.ticketBooking.services.TicketPool;

public class Consumer implements Runnable {
    private TicketPool ticketPool;
    private int buyingFrequncy;
    private int maximumNumberOfTickets;
    private boolean vipstatus;

    public Consumer(TicketPool ticketPool, int buyingFrequncy, int maximumNumberOfTickets, boolean vipstatus){
        this.ticketPool=ticketPool;
        this.buyingFrequncy=buyingFrequncy;
        this.maximumNumberOfTickets=maximumNumberOfTickets;
        this.vipstatus=vipstatus;
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
