package com.hw5.hw5.domain.comment.service

import com.hw5.hw5.domain.comment.dto.CommentRequest
import com.hw5.hw5.domain.comment.dto.CommentResponse
import com.hw5.hw5.domain.comment.model.Comment
import com.hw5.hw5.domain.comment.model.toCommentResponse
import com.hw5.hw5.domain.comment.repository.CommentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository
) {

    fun getCommentList(): List<CommentResponse> {
        return commentRepository.findAll().map { it.toCommentResponse() }
    }

    fun getComment(commentId: Long): CommentResponse {
        return commentRepository.findByIdOrNull(commentId)?.toCommentResponse()
            ?: throw RuntimeException("Comment not found")
    }

    @Transactional
    fun createComment(request: CommentRequest): CommentResponse {
        return commentRepository.save(
            Comment(
                content = request.content
            )
        ).toCommentResponse()
    }

    @Transactional
    fun updateComment(commentId: Long, request: CommentRequest): CommentResponse {
        val result = commentRepository.findByIdOrNull(commentId) ?: throw RuntimeException("Comment not found")

        result.content = request.content
        return commentRepository.save(result).toCommentResponse()
    }

    @Transactional
    fun deleteComment(commentId: Long) {
        val result = commentRepository.findByIdOrNull(commentId) ?: throw RuntimeException("Comment not found")
        return commentRepository.delete(result)
    }
}