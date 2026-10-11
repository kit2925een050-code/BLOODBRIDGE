package com.bloodbridge.service;

import com.bloodbridge.model.BloodRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BloodRequestService {

    private final List<BloodRequest> requests = new ArrayList<>();

    public synchronized void addRequest(BloodRequest request) {

        if (request != null) {
            requests.add(request);
        }
    }

    public synchronized List<BloodRequest> getAllRequests() {

        return new ArrayList<>(requests);
    }
}