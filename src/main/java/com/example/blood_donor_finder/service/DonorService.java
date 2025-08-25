package com.example.blood_donor_finder.service;

import com.example.blood_donor_finder.dto.DonorFormDTO;
import com.example.blood_donor_finder.entity.Donor;
import com.example.blood_donor_finder.repository.DonorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class DonorService {
    private final DonorRepository donorRepository;
    private final ModelMapper modelMapper;
    private final Path uploadDir = Paths.get("uploads");

    @Autowired
    public DonorService(DonorRepository donorRepository, ModelMapper modelMapper){
        this.donorRepository = donorRepository;
        this.modelMapper = modelMapper;

        try{
            Files.createDirectories(uploadDir);
        }catch(IOException e){
            throw new RuntimeException("Could not create upload directory", e);
        }
    }


    public void saveDonor(DonorFormDTO donorFormDTO, MultipartFile citizenshipPhoto, String email) throws IOException {
        if (donorRepository.existsByEmail(email)) {
            throw new IllegalStateException("❌ You have already submitted the donor form with this Gmail.");
        }

        String filename = System.currentTimeMillis() + "_" + citizenshipPhoto.getOriginalFilename();
        Path filePath = uploadDir.resolve(filename);
        Files.copy(citizenshipPhoto.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Donor donor = modelMapper.map(donorFormDTO, Donor.class);
        donor.setCitizenshipPhotoPath(filename);
        donor.setEmail(email); // Save the logged-in Gmail
        donorRepository.save(donor);
    }


    public List<Donor> getDonors(String bloodGroup, String location){
        return donorRepository.findByBloodGroupContainingIgnoreCaseAndLocationContainingIgnoreCase(bloodGroup, location);
    }

    public boolean deleteDonor(Long id){
        if (donorRepository.existsById(id)) {
            donorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Donor> getPendingDonors() {
        return donorRepository.findByApprovedFalse();
    }

    public Donor approveDonor(Long id) {
        Donor donor = donorRepository.findById(id).orElseThrow();
        donor.setApproved(true);
        return donorRepository.save(donor);
    }

    public List<Donor> searchDonors(String bloodGroup, String location) {
        if (bloodGroup != null && bloodGroup.trim().isEmpty()) bloodGroup = null;
        if (location != null && location.trim().isEmpty()) location = null;
        return donorRepository.searchAvailableApprovedDonors(bloodGroup, location);
    }

    public Donor getDonorByEmail(String email) {
        return donorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Donor not found"));
    }

    public void updateProfile(String email, DonorFormDTO dto) {
        Donor donor = getDonorByEmail(email);
        donor.setContact(dto.getContact());
        donor.setAvailability(dto.getAvailability());
        donorRepository.save(donor);
    }

}

