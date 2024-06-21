package com.hw5.hw5.domain.post.repository

import com.hw5.hw5.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post,Long> {

}