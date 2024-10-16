package com.fc.fcserver.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.fcserver.dao.BirthMapper;
import com.fc.fcserver.entity.V2BirthSt;
import com.fc.fcserver.service.BirthService;

@Service
public class BirthServiceImpl implements BirthService {

    @Autowired
    BirthMapper bm;

    @Override
    public List<V2BirthSt> getAllBirths() {
        return bm.selectAll();
    }

    @Override
    public boolean addBirth(V2BirthSt birth) {
        return bm.insert(birth);
    }

    @Override
    public boolean deleteBirth(int birthId) {
        return bm.deleteByPrimaryKey(birthId);
    }
    
}
