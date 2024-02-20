package com.zjl.keeper.domain.model;

import com.zjl.keeper.domain.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectModel {
    private String name;

    public static SubjectModel from(Subject subject){
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setName(subject.getName());
        return subjectModel;
    }
}
