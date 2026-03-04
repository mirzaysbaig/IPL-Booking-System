package Profinch.ipl.com.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private String  venueName;
    private String  Match;
    private Integer totalAvailableSeats;
    private Integer totalAvailblePlatinum;
    private Integer totalAvailableGold;
    private Integer totalAvailableSilver;
    private Integer totalBookedSeats;

    private Integer totalCostGenerated;
    private Integer totalCostfromPlatinum;
    private Integer totalCostfromSilver;
    private Integer totalCostfromGold;
    private Integer totalCancelledSeats;
}
