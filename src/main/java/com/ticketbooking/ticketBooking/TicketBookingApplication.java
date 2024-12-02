package com.ticketbooking.ticketBooking;
import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.*;

@SpringBootApplication
public class TicketBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketBookingApplication.class, args);

		TicketConfig ticketConfig = ConfigurationManager.configurationLoad();

if(ticketConfig == null){
	Scanner input = new Scanner(System.in);
	ticketConfig = new TicketConfig();
	int totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity = 0;


	// Loop for entering Total Tickets
	while (true) {
		try {
			System.out.println("Enter Total number of Tickets ");
			totalTickets = input.nextInt();
			if (totalTickets < 1) {
				System.out.println("Total tickets cannot be less than 1");
				continue;
			}
			ticketConfig.setTotalTickets(totalTickets);
			break;
		} catch (InputMismatchException e) {
			System.out.println("Enter a valid integer for Total Tickets");
			input.nextLine();
		}
	}

	// Loop for entering Ticket Release Rate
	while (true) {
		try {
			System.out.println("Enter Ticket Release Rate ");
			ticketReleaseRate = input.nextInt();
			if (ticketReleaseRate > totalTickets || ticketReleaseRate < 0) {
				System.out.println("Enter a valid Ticket Release Rate (0 to Total Tickets)");
				continue;
			}
			ticketConfig.setTicketReleaseRate(ticketReleaseRate);
			break;
		} catch (InputMismatchException e) {
			System.out.println("Enter a valid integer for Ticket Release Rate");
			input.nextLine();
		}
	}

	// Loop for entering Customer Retrieval Rate
	while (true) {
		try {
			System.out.println("Enter Customer Retrieval Rate ");
			customerRetrievalRate = input.nextInt();
			if (customerRetrievalRate > totalTickets || customerRetrievalRate < 0) {
				System.out.println("Enter a valid Customer Retrieval Rate (0 to Total Tickets)");
				continue;
			}
			ticketConfig.setCustomerRetreivalRate(customerRetrievalRate);
			break;
		} catch (InputMismatchException e) {
			System.out.println("Enter a valid integer for Customer Retrieval Rate");
			input.nextLine();
		}
	}

	// Loop for entering Maximum Ticket Capacity
	while (true) {
		try {
			System.out.println("Enter Maximum Ticket Capacity ");
			maxTicketCapacity = input.nextInt();
			if (maxTicketCapacity < totalTickets) {
				System.out.println("Maximum Ticket Capacity should be greater than or equal to Total Tickets");
				continue;
			}
			ticketConfig.setMaxTicketCapacity(maxTicketCapacity);
			break;
		} catch (InputMismatchException e) {
			System.out.println("Enter a valid integer for Maximum Ticket Capacity");
			input.nextLine();
		}
	}
	ConfigurationManager.configurationSave(ticketConfig);
}
	System.out.println("\nTicket Configuration information");
	System.out.println("Total Tickets: " + ticketConfig.getTotalTickets());
	System.out.println("Ticket Release Rate: " + ticketConfig.getTicketReleaseRate());
	System.out.println("Ticket Retrieval Rate: " + ticketConfig.getCustomerRetreivalRate());
	System.out.println("Maximum Ticket Capacity: " + ticketConfig.getMaxTicketCapacity());

	}
	}

