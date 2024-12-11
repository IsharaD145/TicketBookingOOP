//https://www.geeksforgeeks.org/spring-boot-web-socket/
package com.ticketbooking.ticketBooking.websocket;

import com.ticketbooking.ticketBooking.websocket.TicketWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new TicketWebSocketHandler(), "/ticketLogs").setAllowedOrigins("*");
    }
}
