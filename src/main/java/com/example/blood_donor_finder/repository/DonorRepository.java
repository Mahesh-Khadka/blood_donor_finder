package com.example.blood_donor_finder.repository;

import com.example.blood_donor_finder.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {

    List<Donor> findByBloodGroupContainingIgnoreCaseAndLocationContainingIgnoreCase(String bloodGroup, String location);

    List<Donor> findByApprovedFalse();

    boolean existsByEmail(String email);

    @Query("SELECT d FROM Donor d WHERE " +
            "d.approved = true AND " +
            "(:bloodGroup IS NULL OR d.bloodGroup = :bloodGroup) AND " +
            "(:location IS NULL OR LOWER(d.location) LIKE LOWER(CONCAT('%', :location, '%'))) ")
    List<Donor> searchDonors(@Param("bloodGroup") String bloodGroup,
                             @Param("location") String location);
}
