package com.ticketbooking.ticketBooking;
import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.*;

@SpringBootApplication
public class TicketBookingApplication {
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args) {
        SpringApplication.run(TicketBookingApplication.class, args);
		System.out.println("***********************************************************");
		System.out.println("*             WELCOME TO TICKET BOOKING SIMULATION       *");
		System.out.println("***********************************************************");
		System.out.println("*  1. Start in GUI                                       *");
		System.out.println("*  2. Continue in CLI                                    *");
		System.out.println("***********************************************************");
		int runmethod = input.nextInt();

		switch (runmethod) {
			case 1:
				startReactApp();
				break;

			case 2:
				runCLI();
				break;
		}
	}

	private static void startReactApp() {
		// Start the React app from the Spring Boot application
		try {
			ProcessBuilder processBuilder = new ProcessBuilder("npm", "start");
			processBuilder.directory(new File("/Users/isharad/Desktop/IIT modules/L5/OOP prog /CW/springboot/cwfrontend"));
			processBuilder.inheritIO().start();
		} catch (IOException e) {
			System.err.println("Error starting React app: " + e.getMessage());
		}
	}

	private static void runCLI() {
		int choice = 0;

		System.out.println("***********************************************************");
		System.out.println("*             WELCOME TO TICKET BOOKING SIMULATION       *");
		System.out.println("***********************************************************");
		System.out.println("* SELECT AN OPTION TO PROCEED (1, 2)                     *");
		System.out.println("* 1. CHECK FOR CONFIGURATION FILE AND START SIMULATION   *");
		System.out.println("* 2. ENTER NEW CONFIGURATION INFORMATION START SIMULATION *");
		System.out.println("***********************************************************");

		while (true) {
			try {
				choice = input.nextInt();
				if ((choice > 2) || (choice < 1)) {
					System.out.println("Please enter a valid option, (1, 2)");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Please enter valid data type (1, 2)");
				input.nextLine();
			}
		}

		TicketConfig ticketConfig = null;
		switch (choice) {
			case 1:
				ticketConfig = ConfigurationManager.configurationLoad();
				if (ticketConfig == null) {
					System.out.println("Failed to load Configuration, Configuration does not exist");
					System.out.println("Enter the configuration details to make configuration");
					ticketConfig = new TicketConfig();
					makeConfiguration(ticketConfig);
				}
				break;

			case 2:
				System.out.println("Enter Configuration Details.");
				ticketConfig = new TicketConfig();
				makeConfiguration(ticketConfig);
				break;
		}

		printConfig(ticketConfig);
		startThreads(ticketConfig);


    }


	public static void printConfig(TicketConfig ticketConfig){
		System.out.println("\n*******************************************************");
		System.out.println("*        Ticket Configuration information             *");
		System.out.println("*******************************************************");
		System.out.println("*   Maximum tickets can be added by vendor: " + ticketConfig.getTotalTicketsByVendor()+"         *");
		System.out.println("*   Ticket Release Rate: " + ticketConfig.getTicketReleaseRate()+"                            *");
		System.out.println("*   Maximum tickets can be bought by consumer: " + ticketConfig.getTotalTicketsByConsumer()+"      *");
		System.out.println("*   Ticket Retrieval Rate: " + ticketConfig.getCustomerRetreivalRate()+"                          *");
		System.out.println("*   Maximum Ticket Capacity: " + ticketConfig.getMaxTicketCapacity()+"                      *");
		System.out.println("*******************************************************");

	}

	public static void startThreads(TicketConfig ticketConfig){
		// Run the simulation using the configuration data
		TicketPool ticketpool = new TicketPool(ticketConfig.getMaxTicketCapacity());
		Vendor[] vendors = new Vendor[10];
		for (int i = 0; i < vendors.length; i++) {
			vendors[i] = new Vendor(ticketpool, ticketConfig.getTicketReleaseRate(), ticketConfig.getTotalTicketsByVendor());
			Thread vendorThread = new Thread(vendors[i], "vendor-" + i);
			vendorThread.start();
		}

		Consumer[] cusotmer = new Consumer[10];
		for (int i = 0; i < cusotmer.length; i++) {
			cusotmer[i] = new Consumer(ticketpool, ticketConfig.getCustomerRetreivalRate(), ticketConfig.getTotalTicketsByConsumer());
			Thread customerThread = new Thread(cusotmer[i], "Customer-" + i);
			customerThread.start();
		}
	}

	public static void makeConfiguration(TicketConfig ticketConfig){

		int totalTicketsVendor,totalTicketsCustomer, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity = 0;

		// Loop for entering Maximum Ticket Capacity
		while (true) {
			try {
				System.out.print("Enter Maximum Ticket Capacity: ");
				maxTicketCapacity = input.nextInt();
				if (maxTicketCapacity < 1) {
					System.out.println("Maximum Ticket Capacity should be a positive number");
					continue;
				}
				ticketConfig.setMaxTicketCapacity(maxTicketCapacity);
				break;
			} catch (InputMismatchException e) {
				System.out.println("Enter a valid integer for Maximum Ticket Capacity");
				input.nextLine();
			}
		}


		// Loop for entering Total Tickets that could be added by a vendor at a time.
		while (true) {
			try {
				System.out.print("Enter Maximum number of Tickets that could be added by a vendor at a time: ");
				totalTicketsVendor = input.nextInt();
				if ((totalTicketsVendor < 1)|| (totalTicketsVendor>maxTicketCapacity)) {
					System.out.println("Total tickets cannot be less than 1 or more than the maximum ticket capacity");
					System.out.println("The maximum tickets are : "+maxTicketCapacity);
					continue;
				}
				ticketConfig.setTotalTicketsByVendor(totalTicketsVendor);
				break;
			} catch (InputMismatchException e) {
				System.out.println("Enter a valid integer for maximum tickets that could be released by a vendor at a time");
				input.nextLine();
			}
		}

		// Loop for entering Ticket Release frequency by the vendor
		while (true) {
			try {
				System.out.print("Enter Ticket Release frequency time by vendor in seconds: ");
				ticketReleaseRate = input.nextInt();
				if (ticketReleaseRate < 0 ) {
					System.out.println("Enter a valid Ticket Release Rate in seconds");
					continue;
				}
				ticketConfig.setTicketReleaseRate(ticketReleaseRate);
				break;
			} catch (InputMismatchException e) {
				System.out.println("Enter a valid integer for Ticket Release Rate");
				input.nextLine();
			}
		}

		// Loop for entering Total Tickets that could be bought by a consumer at a time.
		while (true) {
			try {
				System.out.print("Enter Maximum number of Tickets that could be bought by a consumer at a time: ");
				totalTicketsCustomer = input.nextInt();
				if ((totalTicketsCustomer < 1) || (totalTicketsCustomer>maxTicketCapacity)) {
					System.out.println("Maximum tickets cannot be less than 1 or greater than the maximum ticket capacity");
					System.out.println("The maximum tickets are : "+maxTicketCapacity);
					continue;
				}
				ticketConfig.setTotalTicketsByConsumer(totalTicketsCustomer);
				break;
			} catch (InputMismatchException e) {
				System.out.println("Enter a valid integer for maximum total tickets that could be bought by a consumer at a time");
				input.nextLine();
			}
		}

		// Loop for entering Customer Retrieval Rate
		while (true) {
			try {
				System.out.print("Enter Customer Retrieval frequency time by a customer in seconds: ");
				customerRetrievalRate = input.nextInt();
				if (customerRetrievalRate< 0) {
					System.out.println("Enter a valid Customer Retrieval Frequency in seconds.");
					continue;
				}
				ticketConfig.setCustomerRetreivalRate(customerRetrievalRate);
				break;
			} catch (InputMismatchException e) {
				System.out.println("Enter a valid integer for Customer Retrieval Frequency in seconds.");
				input.nextLine();
			}
		}
		ConfigurationManager.configurationSave(ticketConfig);
	}
	}

