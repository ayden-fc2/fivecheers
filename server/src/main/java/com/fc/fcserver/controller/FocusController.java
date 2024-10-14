package com.fc.fcserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fc.fcserver.entity.V2FocusSt;
import com.fc.fcserver.service.FocusService;

@RestController
@RequestMapping("/focus")
public class FocusController {
    @Autowired
    FocusService fs;

    // 获取某用户某天所有目标
    @GetMapping("/getAllFocus")
    public List<V2FocusSt> getAllFocus(@RequestParam("userName") String userName, @RequestParam("date") java.sql.Date date) {
        return fs.getAllFocus(userName, date);
    }

    // 更改某条目标
    @PostMapping("/updateFocus")
    public boolean updateFocus(@RequestBody V2FocusSt v2FocusSt) {
        return fs.updateFocus(v2FocusSt);
    }

    // 某用户新增一条目标
    @PostMapping("/addFocus")
    public boolean addFocus(@RequestBody V2FocusSt v2FocusSt) {
        return fs.addFocus(v2FocusSt);
    }

    // 删除某条目标
    @GetMapping("/deleteFocus")
    public boolean deleteFocus(@RequestParam("focusId") int focusId) {
        return fs.deleteFocus(focusId);
    }
}
