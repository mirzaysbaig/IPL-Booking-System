package Profinch.ipl.com.controllers;

import Profinch.ipl.com.DTO.BookingRequest;
import Profinch.ipl.com.entities.Booking;
import Profinch.ipl.com.entities.Customer;
import Profinch.ipl.com.entities.Seat;
import Profinch.ipl.com.entities.Venue;
import Profinch.ipl.com.services.BookingService;
//import Profinch.ipl.com.services.QrGeneratorService;
//import Profinch.ipl.com.services.QrGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/venue")
public class BookingController {
    @Autowired
    BookingService bookingService = new BookingService();
//    @Autowired
//    private QrGeneratorService qrGeneratorService;

//    @Autowired
//    QrGeneratorService qrGeneratorService=new QrGeneratorService();

    //venue dropdown
    @GetMapping("/")
    public List<Venue> getallvenues(){
        List<Venue> venues= bookingService.getAllVenues();
        return venues;
    }
    // venue page submit // seat api need to check
    //available check , and for particular venue id
    @GetMapping("/{venue_id}/seats")
    public List<Seat> getvenueseats(@PathVariable String venue_id){
        List<Seat> seats=bookingService.getAllSeats(venue_id);
        return seats;
    }

    // need only available tickets


//    @PostMapping("/book")
//    public ResponseEntity<String> bookBookingAndGenerateQrCode(@RequestBody BookingRequest bookingRequest){
//        String name=bookingRequest.getName();
//        String email=bookingRequest.getEmail();
//        Long uId=bookingRequest.getPhone();
//        String seatId=bookingRequest.getSeatId();
//        String venueId=bookingRequest.getVenueId();
//        Customer customer=new Customer(uId,name,email);
//        String bookingId=bookingService.bookTheSeat(customer,seatId,venueId);
//
//
//        byte[] qrCode = qrGeneratorService.generateQr(bookingId);
//        String base64Image = java.util.Base64.getEncoder().encodeToString(qrCode);
//        return ResponseEntity.ok("data:image/png;base64," + base64Image);
//
//    }

    @PostMapping("/book")
    public ResponseEntity<Booking> bookBooking(@RequestBody BookingRequest bookingRequest){
        String name = bookingRequest.getName();
        String email = bookingRequest.getEmail();
        Long uId = bookingRequest.getPhone();
        List<String> seatIds = bookingRequest.getAllSeatId();
//        List<String> seatType = bookingRequest.getAllseatType();
        String venueId = bookingRequest.getVenueId();
        Customer customer = new Customer(uId, name, email);
        Booking booking = bookingService.bookTheSeat(customer,seatIds, venueId);
        return ResponseEntity.ok(booking);
    }



}
