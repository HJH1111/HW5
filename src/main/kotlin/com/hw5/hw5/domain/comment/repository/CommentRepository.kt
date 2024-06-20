package com.hw5.hw5.domain.comment.repository

import com.hw5.hw5.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long>