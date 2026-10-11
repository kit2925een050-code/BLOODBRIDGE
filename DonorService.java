package com.bloodbridge.service;

import com.bloodbridge.model.Donor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DonorService {

    private final List<Donor> donors = new ArrayList<>();

    public synchronized void addDonor(Donor donor) {

        if (donor != null) {
            donors.add(donor);
        }
    }

    public synchronized List<Donor> getAllDonors() {

        return new ArrayList<>(donors);
    }

    public synchronized List<Donor> findMatchingDonors(
            String bloodGroup,
            String city) {

        List<Donor> matches = new ArrayList<>();

        if (bloodGroup == null || city == null) {
            return matches;
        }

        for (Donor donor : donors) {

            if (donor.getBloodGroup() == null
                    || donor.getCity() == null
                    || donor.getAvailability() == null) {
                continue;
            }

            boolean sameBloodGroup =
                    donor.getBloodGroup().trim()
                            .equalsIgnoreCase(bloodGroup.trim());

            boolean sameCity =
                    donor.getCity().trim()
                            .equalsIgnoreCase(city.trim());

            boolean available =
                    donor.getAvailability().trim()
                            .equalsIgnoreCase("Available");

            if (sameBloodGroup && sameCity && available) {
                matches.add(donor);
            }
        }

        return matches;
    }

    public synchronized int getTotalDonors() {
        return donors.size();
    }

    public synchronized int getAvailableDonors() {

        int count = 0;

        for (Donor donor : donors) {

            if (donor.getAvailability() != null
                    && donor.getAvailability().trim()
                            .equalsIgnoreCase("Available")) {
                count++;
            }
        }

        return count;
    }
}