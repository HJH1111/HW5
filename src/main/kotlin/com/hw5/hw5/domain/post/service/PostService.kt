package com.hw5.hw5.domain.post.service

import com.hw5.hw5.domain.post.dto.CreatePostRequest
import com.hw5.hw5.domain.post.dto.PostResponse
import com.hw5.hw5.domain.post.dto.UpdatePostRequest
import com.hw5.hw5.domain.post.model.Post
import com.hw5.hw5.domain.post.model.toPostResponse
import com.hw5.hw5.domain.post.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository
) {

    fun getPostList(): List<PostResponse> {
        return postRepository.findAll().map { it.toPostResponse() }
    }

    fun getPost(postId: Long): PostResponse {
        return postRepository.findByIdOrNull(postId)?.toPostResponse() ?: throw RuntimeException("Post not found")
    }

    @Transactional
    fun createPost(request: CreatePostRequest): PostResponse {
        return postRepository.save(
            Post(
               title = request.title,
                content = request.content
            )
        ).toPostResponse()
    }

    @Transactional
    fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        val result = postRepository.findByIdOrNull(postId) ?: throw RuntimeException("Post not found")

        result.title = request.title
        result.content = request.content
        return postRepository.save(result).toPostResponse()
    }

    @Transactional
    fun deletePost(postId: Long) {
        val result = postRepository.findByIdOrNull(postId) ?: throw RuntimeException("Post not found")
        return postRepository.delete(result)
    }
}