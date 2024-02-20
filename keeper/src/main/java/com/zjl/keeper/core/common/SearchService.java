package com.zjl.keeper.core.common;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToLongFunction;

/**
 * 用于查询的类;只读
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {

    @PersistenceContext
    private final EntityManager entityManager;
    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;

    public <R> R search(BiFunction<EntityManager, EntityManagerFactory, R> searchFunction) {
        return searchFunction.apply(entityManager, entityManagerFactory);
    }

    public <R> R search(Function<JPAQueryFactory, R> searchFunction) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return searchFunction.apply(queryFactory);
    }

//    public <R> R searchBlaze(Function<BlazeJPAQuery<?>, R> searchFunction) {
//        CriteriaBuilderFactory criteriaBuilderFactory = Criteria.getDefault().createCriteriaBuilderFactory(this.entityManagerFactory);
//        BlazeJPAQuery<?> queryFactory = new BlazeJPAQuery<>(this.entityManager, criteriaBuilderFactory);
//        return searchFunction.apply(queryFactory);
//    }

    /**
     * 直接获取EntityManager 进行查询
     */
    public <R> R searchNative(Function<EntityManager, R> searchFunction) {
        return searchFunction.apply(entityManager);
    }

    /**
     * 用于执行更新和删除
     * @param	executeFunction 执行的内容
     * @return   java.lang.Long
     */
    @Transactional(rollbackFor = Exception.class)
    public Long execute(ToLongFunction<JPAQueryFactory> executeFunction) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return executeFunction.applyAsLong(queryFactory);
    }
}


