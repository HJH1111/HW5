package com.hw5.hw5.domain.comment.service

import com.hw5.hw5.domain.comment.dto.CommentRequest
import com.hw5.hw5.domain.comment.dto.CommentResponse
import com.hw5.hw5.domain.comment.model.Comment
import com.hw5.hw5.domain.comment.model.toCommentResponse
import com.hw5.hw5.domain.comment.repository.CommentRepository
import com.hw5.hw5.domain.exception.ModelNotFoundException
import com.hw5.hw5.domain.member.repository.MemberRepository
import com.hw5.hw5.domain.post.repository.PostRepository
import com.hw5.hw5.infra.security.MemberPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository,
) {

    fun getCommentList(postId: Long): List<CommentResponse> {
        return commentRepository.findAll().map { it.toCommentResponse() }
    }

    fun getComment(postId: Long, commentId: Long): CommentResponse {
        return commentRepository.findByPostIdAndId(postId,commentId)?.toCommentResponse()
            ?: throw ModelNotFoundException("Comment", commentId)
    }

    @Transactional
    fun createComment(postId: Long, request: CommentRequest, memberPrincipal: MemberPrincipal): CommentResponse {
        val member = memberRepository.findByIdOrNull(memberPrincipal.id) ?: throw ModelNotFoundException("Member", memberPrincipal.id)
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        return commentRepository.save(
            Comment(
                content = request.content,
                member = member,
                post = post


            )
        ).toCommentResponse()
    }

    @Transactional
    fun updateComment(postId: Long, commentId: Long, request: CommentRequest, memberPrincipal: MemberPrincipal): CommentResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if(comment.post.id != post.id) throw ModelNotFoundException("Post", post.id)
        if(comment.member.id != memberPrincipal.id) throw ModelNotFoundException("Member", memberPrincipal.id)

        comment.content = request.content
        return commentRepository.save(comment).toCommentResponse()
    }

    @Transactional
    fun deleteComment(postId: Long, commentId: Long, memberPrincipal: MemberPrincipal) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if(comment.post.id != post.id) throw ModelNotFoundException("Post", post.id)
        if(comment.member.id != memberPrincipal.id) throw ModelNotFoundException("Member", memberPrincipal.id)
        return commentRepository.delete(comment)
    }
}