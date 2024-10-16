package com.fc.fcserver.service;

import java.util.List;

import com.fc.fcserver.entity.V2BirthSt;

public interface BirthService {

    List<V2BirthSt> getAllBirths();

    boolean addBirth(V2BirthSt birth);

    boolean deleteBirth(int birthId);
    
}
