package Profinch.ipl.com.repositories;

import Profinch.ipl.com.entities.Seat;
import Profinch.ipl.com.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,String> {

//    List<Seat> findByVenueId(Integer venueId);
    @Transactional
    @Modifying
    @Query("UPDATE Seat s SET s.status = :status WHERE s.seatId = :seatId")
    public void updateSeatStatus(@Param("seatId") String seatId, @Param("status") String status);

    Integer countByVenueAndStatus(Venue venue, String status);
    Integer countByVenueAndStatusAndSeatType(Venue venue, String status, String seatType);

    @Query("SELECT s FROM Seat s JOIN FETCH s.venue WHERE s.venue.venueId = :venueId")
    List<Seat> findListOfSeatsByVenueId(@Param("venueId") String venueId);

    @Query("SELECT s.seatType FROM Seat s WHERE s.seatId=:seatId")
    String findSeatTypeBySeatId(@Param("seatId")String seatId);

}
