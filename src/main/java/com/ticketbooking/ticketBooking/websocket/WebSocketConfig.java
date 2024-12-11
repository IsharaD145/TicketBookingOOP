//https://www.geeksforgeeks.org/spring-boot-web-socket/
package com.ticketbooking.ticketBooking.websocket;

import com.ticketbooking.ticketBooking.websocket.TicketWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configuration class for setting up WebSocket endpoints.
 * Enables WebSocket support and maps WebSocket handlers to specific endpoints.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    /**
     * Registers WebSocket handlers and maps them to URL endpoints.
     *
     * @param registry the used to configure WebSocket handlers.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Registers the TicketWebSocketHandler to handle WebSocket connections at the "/ticketLogs" endpoint.
        registry.addHandler(new TicketWebSocketHandler(), "/ticketLogs").setAllowedOrigins("*");
    }
}
