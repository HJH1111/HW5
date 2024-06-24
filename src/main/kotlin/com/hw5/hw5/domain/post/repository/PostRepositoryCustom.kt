package com.hw5.hw5.domain.post.repository

import com.hw5.hw5.domain.post.model.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostRepositoryCustom {

    fun findByPostPage(pageable: Pageable, title: String?): Page<Post>
}