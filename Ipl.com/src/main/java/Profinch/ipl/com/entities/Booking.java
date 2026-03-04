package Profinch.ipl.com.entities;

import Profinch.ipl.com.entities.enums.StatusType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOOKINGMULTIPLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Booking {
    @NonNull
    @Id
    @Column(name = "BOOKING_ID")
    private String bookingId;

    @NonNull
    @Enumerated(EnumType.STRING)
    private StatusType status; // Booked / Cancelled
//    private String qrCode;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "Id")
    private Customer customer;

    @NonNull
    private String venueId;

    @NonNull
    @ElementCollection
    @CollectionTable(name = "BOOKING_SEATS", joinColumns = @JoinColumn(name = "BOOKING_ID"))
    private List<BookedSeats> seats;
     //upperone without default initialization
    //private List<BookedSeats> seats = new ArrayList<>();

    @NonNull
    private Integer amountPaid;

    @CreationTimestamp
    private LocalDateTime bookedAt;

//    public Booking(String bookingId, String bookingStatus, Customer customer, String venueId, List<String> seatIds, Integer amountPaid) {
//
//    }

}




