package com.zjl.keeper.api.business;

import com.zjl.keeper.domain.model.TeacherModel;
import com.zjl.keeper.domain.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wenman
 */
@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    @GetMapping
    public List<TeacherModel> findAll(){
       return teacherService.findAll();
    }
}
