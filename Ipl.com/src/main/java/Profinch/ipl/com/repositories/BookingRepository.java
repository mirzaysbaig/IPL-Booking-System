package Profinch.ipl.com.repositories;

import Profinch.ipl.com.entities.Booking;
import Profinch.ipl.com.entities.enums.SeatType;
import Profinch.ipl.com.entities.enums.StatusType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,String> {

    @Transactional
    @Modifying
    @Query("UPDATE Booking b SET b.status = :status WHERE b.bookingId = :BookingId")
    public void updateBookingStatus(@Param("BookingId") String BookingId, @Param("status") StatusType status);

    @Query("SELECT COUNT(bs) " +
            "FROM Booking b JOIN b.seats bs " +
            "WHERE b.venueId = :venueId AND bs.status = :status")
    Integer countSeatsByVenueAndStatus(@Param("venueId") String venueId,
                                       @Param("status") StatusType status);


    Optional<Booking> findByBookingId(String BookingId);

//    @Modifying(clearAutomatically = true)
//    @Transactional  // in JPQL wec antnot use , embbedablable uodate or bulk updates
//    @Query("UPDATE Booking b JOIN b.seats s SET s.status = :status WHERE b.bookingId = :bookingId AND s.seatId IN :seatIds")
//    void updateSeatStatus(@Param("bookingId") String bookingId,
//                          @Param("seatIds") List<String> seatIds,
//                          @Param("status") StatusType status);


    //Optional<Booking> findByBookingId(String bookingId);

}
