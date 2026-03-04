package Profinch.ipl.com.entities;

import jakarta.persistence.*;

import lombok.*;
import java.util.List;

@Entity
@Data
public class Venue {

    @Id
    private String venueId;

    private String venueName;


    @OneToMany(mappedBy = "venue", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Seat> seats;

}