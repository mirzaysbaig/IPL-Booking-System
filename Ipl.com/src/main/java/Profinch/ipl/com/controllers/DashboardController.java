package Profinch.ipl.com.controllers;

import Profinch.ipl.com.DTO.DashboardDTO;
import Profinch.ipl.com.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {
    @Autowired
    DashboardService dashboardService = new DashboardService();

    @GetMapping("/api/ticket/dashboard")
    public List<DashboardDTO> getDashboard(){
        return dashboardService.getDashboard();
    }


}
