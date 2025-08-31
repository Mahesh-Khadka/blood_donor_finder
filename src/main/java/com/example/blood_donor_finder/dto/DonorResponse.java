package com.example.blood_donor_finder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DonorResponse {
    private Long id;
    private String name;
    private String email;
    private String bloodGroup;
    private String location;
    private String contact;
    private boolean approved;
    private String availability;
}
