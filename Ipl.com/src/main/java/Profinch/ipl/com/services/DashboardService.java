package Profinch.ipl.com.services;

import Profinch.ipl.com.DTO.DashboardDTO;
import Profinch.ipl.com.entities.Venue;
import Profinch.ipl.com.entities.enums.StatusType;
import Profinch.ipl.com.repositories.BookingRepository;
import Profinch.ipl.com.repositories.PaymentRepository;
import Profinch.ipl.com.repositories.SeatRepository;
import Profinch.ipl.com.repositories.VenueRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    VenueRepository venueRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Transactional
    public List<DashboardDTO> getDashboard(){
        List<DashboardDTO> dashboardDataList = new ArrayList<>();
        List<Venue> venues = venueRepository.findAll();
        Map<String,String> map = new HashMap<>();
        map.putAll(Map.of("BOM", "MI vs DC", "BLR", "RCB vs CSK",
                "CCU", "KKR vs PBKS", "AMD", "GT vs RR",
                "HYD", "SRH vs LSG"));
        for (Venue venue : venues) {
            DashboardDTO dto = new DashboardDTO();
            dto.setVenueName(venue.getVenueName());
            String Match= map.get(venue.getVenueId());
            dto.setMatch(Match);

            Integer availableSeats = seatRepository.countByVenueAndStatus(venue, "AVAILABLE");
            dto.setTotalAvailableSeats(availableSeats);

            Integer availablePlatinum = seatRepository.countByVenueAndStatusAndSeatType(venue, "AVAILABLE","PLATINUM");
            dto.setTotalAvailblePlatinum(availablePlatinum);

            Integer availableGold = seatRepository.countByVenueAndStatusAndSeatType(venue, "AVAILABLE","GOLD");
            dto.setTotalAvailableGold(availableGold);

            Integer availablesilver = seatRepository.countByVenueAndStatusAndSeatType(venue, "AVAILABLE","SILVER");
            dto.setTotalAvailableSilver(availablesilver);

           Integer totalcostfromPlatinum=paymentRepository.sumOfTotalAmountByVenueIdAndSeatType(venue.getVenueId(),"PLATINUM");
           Integer  totalcostfromGold=paymentRepository.sumOfTotalAmountByVenueIdAndSeatType(venue.getVenueId(),"GOLD");
           Integer  totalcostfromSilver=paymentRepository.sumOfTotalAmountByVenueIdAndSeatType(venue.getVenueId(),"SILVER");
           Integer  totalCostGenerated=totalcostfromSilver+totalcostfromGold+totalcostfromPlatinum;

           dto.setTotalCostfromPlatinum(totalcostfromPlatinum);
           dto.setTotalCostfromGold(totalcostfromGold);
           dto.setTotalCostfromSilver(totalcostfromSilver);
           dto.setTotalCostGenerated(totalCostGenerated);


            Integer bookedSeats = bookingRepository.countSeatsByVenueAndStatus(venue.getVenueId(), StatusType.BOOKED);
            Integer partialcancelled = bookingRepository.countSeatsByVenueAndStatus(venue.getVenueId(), StatusType.PARTIALLY_CANCELLED);
            dto.setTotalBookedSeats(bookedSeats+partialcancelled);

            Integer cancelledSeats = bookingRepository.countSeatsByVenueAndStatus(venue.getVenueId(), StatusType.CANCELLED);
            dto.setTotalCancelledSeats(cancelledSeats);

            dashboardDataList.add(dto);
        }

        return dashboardDataList;
    }
}
