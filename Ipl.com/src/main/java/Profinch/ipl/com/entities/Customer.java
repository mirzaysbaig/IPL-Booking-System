package Profinch.ipl.com.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long uId; // this is phone no.


    private String name;

    private String email;

    public Customer(Long uId, String name, String email) {
        this.uId = uId;
        this.name = name;
        this.email = email;
    }
}
