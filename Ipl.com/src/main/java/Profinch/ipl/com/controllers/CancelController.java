package Profinch.ipl.com.controllers;

import Profinch.ipl.com.entities.Booking;
import Profinch.ipl.com.services.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CancelController {
    @Autowired
    BookingService bookingService = new BookingService();

    @GetMapping("/api/checkticket/{bookingId}")
    public Booking checkTicket(@PathVariable String bookingId) {
        Booking booking=bookingService.checkTicket(bookingId);
        return booking;
    }
    @PutMapping("/api/cancelticket/{bookingId}")
    public Pair<String,Integer> cancelTicket(@PathVariable String bookingId, @RequestBody List<String> seatId) {
        Pair<String,Integer> response=bookingService.cancelTicket(bookingId,seatId);
        return response;
    }
}
