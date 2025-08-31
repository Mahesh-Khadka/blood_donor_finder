package com.example.blood_donor_finder.controller;

import com.example.blood_donor_finder.dto.DonorFormDTO;
import com.example.blood_donor_finder.dto.DonorResponse;
import com.example.blood_donor_finder.entity.Donor;
import com.example.blood_donor_finder.service.DonorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/donors")
public class DonorController {
    private final DonorService donorService;

    @Autowired
    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> saveDonor(@ModelAttribute @Valid DonorFormDTO donorFormDTO,
                                            @RequestParam("citizenshipPhoto") MultipartFile citizenshipPhoto) {
        if (citizenshipPhoto.isEmpty()) {
            return ResponseEntity.badRequest().body("Citizenship photo is required.");
        }

        // Get logged-in user's email from Spring Security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email;
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();  // username is usually email
        } else {
            email = principal.toString();
        }

        try {
            donorService.saveDonor(donorFormDTO, citizenshipPhoto, email);
            return ResponseEntity.ok("✅ Donor information submitted successfully!");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Duplicate email
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Error saving donor: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Donor> getDonors(@RequestParam(defaultValue = "") String bloodGroup,
                                 @RequestParam(defaultValue = "") String location){
        return donorService.getDonors(bloodGroup, location);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDonor(@PathVariable Long id) {
        boolean deleted = donorService.deleteDonor(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/pending")
    public List<Donor> getPendingDonors() {
        return donorService.getPendingDonors();
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Donor> approveDonor(@PathVariable Long id) {
        try {
            Donor updatedDonor = donorService.approveDonor(id);
            return ResponseEntity.ok(updatedDonor);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Donor> searchDonors(@RequestParam(required = false) String bloodGroup,
                                    @RequestParam(required = false) String location) {
        return donorService.searchDonors(bloodGroup, location);
    }
    @GetMapping("/profile")
    public ResponseEntity<DonorResponse> getProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) auth.getPrincipal()).getUsername();

        Donor donor = donorService.getDonorByEmail(email);
        DonorResponse response = new DonorResponse(
                donor.getId(),
                donor.getName(),
                donor.getEmail(),
                donor.getBloodGroup(),
                donor.getLocation(),
                donor.getContact(),
                donor.isApproved(),
                donor.getAvailability()
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody DonorFormDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) auth.getPrincipal()).getUsername();

        donorService.updateProfile(email, dto);
        return ResponseEntity.ok().build();
    }


}
