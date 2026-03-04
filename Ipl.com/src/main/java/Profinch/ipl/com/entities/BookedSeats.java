package Profinch.ipl.com.entities;

import Profinch.ipl.com.entities.enums.SeatType;
import Profinch.ipl.com.entities.enums.StatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookedSeats{
    private String seatId;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;
}

