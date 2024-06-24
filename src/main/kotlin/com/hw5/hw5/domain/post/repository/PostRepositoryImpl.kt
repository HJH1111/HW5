package com.hw5.hw5.domain.post.repository

import com.hw5.hw5.domain.post.model.Post
import com.hw5.hw5.domain.post.model.QPost
import com.hw5.hw5.infra.querydsl.QueryDslSupport
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.PathBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFrom
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl: QueryDslSupport(), PostRepositoryCustom {

    private val post = QPost.post


    override fun findByPostPage(pageable: Pageable, title: String?): Page<Post> {


        val whereClause = BooleanBuilder()
        title?. let { whereClause.and(post.title.like("%$title%"))}

        val totalCount = queryFactory
            .select(post.count())
            .from(post)
            .where(whereClause)
            .fetchOne() ?: 0L

        val content = queryFactory
            .selectFrom(post)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(*getOrderSpecifier(pageable,post))
            .fetch()

        return PageImpl(content, pageable, totalCount)
    }

    private fun getOrderSpecifier(
        pageable: Pageable,
        path: EntityPathBase<*>
    )
        : Array<OrderSpecifier<*>> {
        val pathBuilder = PathBuilder(path.type, path.metadata)

        return pageable.sort.toList().map { order ->
            OrderSpecifier(
                if (order.isAscending) Order.ASC else Order.DESC,
                pathBuilder.get(order.property)
                    as Expression<Comparable<*>>
            )
        }.toTypedArray()
    }
}