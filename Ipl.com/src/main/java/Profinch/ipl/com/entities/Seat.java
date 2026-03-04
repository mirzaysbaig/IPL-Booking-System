package Profinch.ipl.com.entities;
import Profinch.ipl.com.entities.enums.SeatType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    private String seatId;


    private String seatType;  // Platinum, Gold, Silver

    private String status; // Available / Booked

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venueId")
    @JsonIgnore
    private Venue venue;
}