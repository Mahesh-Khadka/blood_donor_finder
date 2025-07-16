package com.example.blood_donor_finder.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DonorFormDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 18, message = "Minimum age is 18")
    @Max(value = 65, message = "Maximum age is 65")
    private int age;

    @NotBlank(message = "Gender is required")
    private String gender;

    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood group")
    private String bloodGroup;

    @NotBlank(message = "Location is required")
    private String location;

    @Pattern(regexp = "^98\\d{8}$", message = "Invalid Nepali contact number")
    private String contact;
}
