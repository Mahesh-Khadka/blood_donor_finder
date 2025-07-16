package com.example.blood_donor_finder.repository;

import com.example.blood_donor_finder.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    List<Donor>findByBloodGroupContainingIgnoreCaseAndLocationContainingIgnoreCase(String bloodGroup, String location);
    List<Donor>findByApprovedFalse();
}
