package Profinch.ipl.com.repositories;

import Profinch.ipl.com.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.venueId = :venueId AND p.seatType = :seatType")
    Integer sumOfTotalAmountByVenueIdAndSeatType(@Param("venueId") String venueId, @Param("seatType") String seatType);

    @Query("SElECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.bookingId = :bookingId AND p.seatId IN :seatIds")
    Integer SumOfAmountByBookingIdAndListofSeatIds(@Param("bookingId") String bookingId,
                                                   @Param("seatIds") List<String> seatIds);

    @Modifying
    @Transactional
    @Query("UPDATE Payment p SET p.amount=0 WHERE p.bookingId =:bookingId AND p.seatId IN :seatIds")
    void updateByBookingIdandSeatid(@Param("bookingId") String bookingId,
                                    @Param("seatIds")  List<String> seatIds);



}
