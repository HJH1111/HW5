package com.hw5.hw5.domain.comment.controller

import com.hw5.hw5.domain.comment.dto.CommentRequest
import com.hw5.hw5.domain.comment.dto.CommentResponse
import com.hw5.hw5.domain.comment.service.CommentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts/{postId}/comments")
class CommentController(
    private val commentService: CommentService
) {

    @GetMapping
    fun getCommentList(): ResponseEntity<List<CommentResponse>> {
        return ResponseEntity.ok(commentService.getCommentList())
    }

    @GetMapping("/{commentId}")
    fun getComment(commentId: Long): ResponseEntity<CommentResponse> {
        return ResponseEntity.ok(commentService.getComment(commentId))
    }

    @PostMapping
    fun createComment(
        @RequestBody request: CommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity.ok(commentService.createComment(request))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody request: CommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity.ok(commentService.updateComment(commentId, request))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(commentService.deleteComment(commentId))
    }
}