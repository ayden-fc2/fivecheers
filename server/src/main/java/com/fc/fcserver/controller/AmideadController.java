package com.fc.fcserver.controller;

import com.fc.fcserver.entity.V2AmideadSt;
import com.fc.fcserver.service.AmideadService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/amidead")
public class AmideadController {

    @Autowired
    AmideadService as;

    @GetMapping("/getAmidead")
    public V2AmideadSt getAmidead(){
        return as.getAmidead();
    }

    @PostMapping("/updateTime")
    public boolean updateTime(){
        return as.updateTime();
    }

    @PostMapping("/updateGiftStill")
    public String updateGiftStill(@Param("secret") int secret){return as.updateGiftStill(secret);}

    // 管理，获取全部&更改全部
    @GetMapping("/getAmideadAll")
    public V2AmideadSt getAmideadAll(){
        return as.getAmideadAll();
    }

    @PostMapping("/updateAmideadAll")
    public boolean updateAmideadAll(@RequestBody V2AmideadSt amideadSt){
        return as.updateAmideadAll(amideadSt);
    }
}
