package com.zjl.keeper.domain.model;

import com.zjl.keeper.domain.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wenman
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherModel {
    private String name;
    private List<SubjectModel> subjectModels;

    public static TeacherModel from(Teacher teacher) {
        TeacherModel teacherModel = new TeacherModel();
        if (Objects.isNull(teacher)) {
            return teacherModel;
        }
        teacherModel.setName(teacher.getName());
        teacherModel.setSubjectModels(teacher.getSubjects().stream().map(SubjectModel::from).collect(Collectors.toList()));
        return teacherModel;
    }
}
