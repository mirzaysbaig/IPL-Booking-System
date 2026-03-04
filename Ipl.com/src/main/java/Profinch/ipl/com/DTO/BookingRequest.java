package Profinch.ipl.com.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private String name;
    private String email;
    private Long phone;
    private List<String> allSeatId; // list of seat id for multiple seat at one booking id only
//    private List<String> allseatType;
    private String venueId;

}
