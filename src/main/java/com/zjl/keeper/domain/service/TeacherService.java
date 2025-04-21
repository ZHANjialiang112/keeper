package com.zjl.keeper.domain.service;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.zjl.keeper.core.annotation.TestLog;
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
                query.select(Projections.constructor(TeacherModel.class,
                        teacher.name,subject.name))
                .from(teacher)
                .leftJoin(subject).on(subject.teacher.eq(teacher)).fetchJoin()
                        .where(teacher.name.like("%王%"))
                .fetch());
    }

    @TestLog(tags = {"数组1","数组2"},value = "测试定义的注解")
    public String haveLog(){
        log.info("have log is over");
        return "OK";
    }
}
