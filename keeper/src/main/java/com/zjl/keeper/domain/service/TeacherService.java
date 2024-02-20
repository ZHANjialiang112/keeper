package com.zjl.keeper.domain.service;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.zjl.keeper.core.common.SearchService;
import com.zjl.keeper.domain.entity.QSubject;
import com.zjl.keeper.domain.entity.QTeacher;
import com.zjl.keeper.domain.model.SubjectModel;
import com.zjl.keeper.domain.model.TeacherModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wenman
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherService {
    private final SearchService searchService;

    public List<TeacherModel> findAll() {
        QTeacher teacher = QTeacher.teacher;
        QSubject subject = QSubject.subject;

        return searchService.search(query ->
                query.select(Projections.fields(TeacherModel.class,
                        teacher.name,
                        GroupBy.list(Projections.bean(SubjectModel.class,subject.name)).as("subjectModels")))
                .from(teacher)
                .leftJoin(subject).on(subject.teacher.eq(teacher)).fetchJoin()
                .fetch());
    }
}
