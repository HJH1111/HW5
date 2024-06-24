package com.hw5.hw5.domain.post.service

import com.hw5.hw5.domain.comment.model.toCommentResponse
import com.hw5.hw5.domain.comment.repository.CommentRepository
import com.hw5.hw5.domain.exception.ModelNotFoundException
import com.hw5.hw5.domain.member.repository.MemberRepository
import com.hw5.hw5.domain.post.dto.CreatePostRequest
import com.hw5.hw5.domain.post.dto.PostResponse
import com.hw5.hw5.domain.post.dto.PostResponseWithComment
import com.hw5.hw5.domain.post.dto.UpdatePostRequest
import com.hw5.hw5.domain.post.model.Post
import com.hw5.hw5.domain.post.model.toPostResponse
import com.hw5.hw5.domain.post.repository.PostRepository
import com.hw5.hw5.infra.security.MemberPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val memberRepository: MemberRepository,

    ) {

    fun getPostPage(pageable: Pageable, title: String): Page<PostResponse> {
        return postRepository.findByPostPage(pageable, title).map { it.toPostResponse() }
    }

    fun getPost(postId: Long): PostResponseWithComment {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByPostId(postId).map { it.toCommentResponse() }
        return PostResponseWithComment(
            id = post.id!!,
            title = post.title,
            content = post.content,
            createdAt = post.createdAt,
            commentList = comment
        )
    }

    @Transactional
    fun createPost(request: CreatePostRequest, memberPrincipal: MemberPrincipal): PostResponse {
        val member = memberRepository.findByIdOrNull(memberPrincipal.id)
            ?: throw ModelNotFoundException("Member", memberPrincipal.id)
        return postRepository.save(
            Post(
                title = request.title,
                content = request.content,
                member = member
            )
        ).toPostResponse()
    }

    @Transactional
    fun updatePost(postId: Long, request: UpdatePostRequest, memberPrincipal: MemberPrincipal): PostResponse {
        val result = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        if (result.member.id != memberPrincipal.id) throw IllegalArgumentException("회원 ID가 인증된 사용자 ID와 일치하지 않습니다.")

        result.title = request.title
        result.content = request.content
        return postRepository.save(result).toPostResponse()
    }

    @Transactional
    fun deletePost(postId: Long, memberPrincipal: MemberPrincipal) {
        val result = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        if (result.member.id != memberPrincipal.id) throw IllegalArgumentException("권한이 없는 사용자입니다.")
        return postRepository.delete(result)
    }
}