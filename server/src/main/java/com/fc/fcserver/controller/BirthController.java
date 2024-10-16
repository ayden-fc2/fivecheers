package com.fc.fcserver.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fc.fcserver.entity.V2BirthSt;
import com.fc.fcserver.service.BirthService;

@RestController
@RequestMapping("/birth")
public class BirthController {

    @Autowired
    BirthService bs;

    @GetMapping("/getAllBirths")
    public List<V2BirthSt> getAllBirths() {
        return bs.getAllBirths();
    }

    @PostMapping("/addBirth")
    public boolean addBirth(@RequestBody V2BirthSt birth) {
        return bs.addBirth(birth);
    }

    @PostMapping("/deleteBirth")
    public boolean deleteBirth(@Param("birthId") int birthId) {
        return bs.deleteBirth(birthId);
    }

}
