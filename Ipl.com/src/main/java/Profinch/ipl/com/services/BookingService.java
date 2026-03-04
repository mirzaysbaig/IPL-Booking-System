package Profinch.ipl.com.services;

import Profinch.ipl.com.entities.*;
import Profinch.ipl.com.entities.enums.SeatType;
import Profinch.ipl.com.entities.enums.StatusType;
import Profinch.ipl.com.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public List<Seat> getAllSeats(String venueId) {
        List<Seat> seats = seatRepository.findListOfSeatsByVenueId(venueId);
        return seats;
    }


    @Transactional
    public Booking bookTheSeat(Customer customer, List<String> seatIds, String venueId) {
        customerRepository.save(customer);
        // gnerating booking id nd status
        StringBuilder bookingId=new StringBuilder();
        Long phoneno=customer.getUId();
        Long id=customer.getId();

//        bookingId.append(venueId);
        bookingId.append("VEN").append(venueId).append("-PH");
        bookingId.append(phoneno);
        bookingId.append("-CRN").append(id);
        String BookingId=bookingId.toString();
//        String bookingStatus="BOOKED";

        Integer amount_paid=0;
        List<String> seattype=new ArrayList<>();
        List<BookedSeats> bookedSeatsList=new ArrayList<>();
        for(int i=0;i<seatIds.size();i++){
            String seatid=seatIds.get(i);
            String seatType=seatRepository.findSeatTypeBySeatId(seatid);
            System.out.println("payment process started");
            Integer response=Paymenthandler(seatid,seatType,BookingId,venueId);
            System.out.println(response);
            if (response==-1) {
                return null;
            }
            seattype.add(seatType);
            seatRepository.updateSeatStatus(seatid,"BOOKED");
            amount_paid=response+amount_paid;
            SeatType seatTypeEnum= SeatType.valueOf(seatType);
            BookedSeats bookedSeats=new BookedSeats(seatid,StatusType.BOOKED,seatTypeEnum);
            bookedSeatsList.add(bookedSeats);
        }

        Integer amountPaid=amount_paid;
        Booking booking=new Booking(BookingId, StatusType.BOOKED,customer,venueId,bookedSeatsList,amountPaid);
        bookingRepository.save(booking);
        // return booking;
      ///  fetch booking with booking id
        Optional<Booking> fetchbooking=bookingRepository.findByBookingId(BookingId);
        return fetchbooking.get();

    }

    public Integer Paymenthandler(String seatId,String seatType,String bookingId,String venueId) {
        Payment payment=new Payment();
        payment.setBookingId(bookingId);
        payment.setSeatId(seatId);
        payment.setVenueId(venueId);
        payment.setSeatType(seatType);

        Integer amount;
        if("PLATINUM".equals(seatType)) amount=3000;
        else if ("GOLD".equals(seatType)) amount=2000;
        else if ("SILVER".equals(seatType)) amount=1000;
        else amount=0;
        payment.setAmount(amount);

        Payment savedPayment=paymentRepository.save(payment);

        if (savedPayment != null && savedPayment.getId() != null) {
            return amount;
        }
/// / if payment failed
        return -1;
    }

    public Booking checkTicket(String bookingId){
        Optional<Booking> booking =bookingRepository.findById(bookingId);
        return booking.orElse(null);
    }

    ///  cancel ticekt
    @Transactional
    public Pair<String,Integer> cancelTicket(String BookingId, List<String> seatIds){

        for(String seatid:seatIds){
            seatRepository.updateSeatStatus(seatid,"AVAILABLE");
        }
        Optional<Booking> BookingDetails =bookingRepository.findById(BookingId);
        Booking bookingDetails = BookingDetails.get();
        updateSeatStatus(BookingId,seatIds,StatusType.CANCELLED,bookingDetails);

        //if all booked seat cancelled then update boking status cancelled or else partially Cancelled
        // using stream as list can be treated as stream and then we can use lamda expresssion
        boolean anybooked=bookingDetails
                .getSeats()
                .stream()
                .anyMatch(seat->{ return (seat.getStatus()==StatusType.BOOKED);});

        if(anybooked)
        {bookingRepository.updateBookingStatus(BookingId,StatusType.PARTIALLY_CANCELLED);}
        else {
            bookingRepository.updateBookingStatus(BookingId,StatusType.CANCELLED);
        }

        // now genrating the refund process
        Integer refundamount=refund(BookingId,seatIds);

        return Pair.of(BookingId,refundamount);
    }

    public Integer refund(String BookingId,List<String> seatIds){
        Integer refundamount=paymentRepository.SumOfAmountByBookingIdAndListofSeatIds(BookingId,seatIds);
        paymentRepository.updateByBookingIdandSeatid(BookingId, seatIds);
        return refundamount;
    }
    @Transactional
    public  void updateSeatStatus(String BookingId,List<String> seatIds,StatusType status,Booking bookingDetails) {
        bookingDetails.getSeats().stream()
                .filter(seat -> seatIds.contains(seat.getSeatId()))
                .forEach(seat -> seat.setStatus(status));

        ///  now manually save it in db like bookingrepository.save(booking)
        ///  or use transactional it will auto persist

    }


//    @Transactional
//    public Booking createBooking(Booking booking){
//        Seat seat = booking.getSeat();
//        if(seat==null || !"Available".equals(seat.getStatus()));
//
//    }


}
