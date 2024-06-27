package com.hw5.hw5.domain.post.controller

import com.hw5.hw5.domain.post.dto.CreatePostRequest
import com.hw5.hw5.domain.post.dto.PostResponse
import com.hw5.hw5.domain.post.dto.PostResponseWithComment
import com.hw5.hw5.domain.post.dto.UpdatePostRequest
import com.hw5.hw5.domain.post.service.PostService
import com.hw5.hw5.infra.security.MemberPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {

    @GetMapping("/search")
    fun getPostPage(
        @PageableDefault(size = 10, sort = ["createdAt"]) pageable: Pageable,
        @RequestParam(value = "title", required = false) title: String?,
    ): ResponseEntity<Page<PostResponse>> {
        return ResponseEntity.ok(postService.getPostPage(pageable, title))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GENERAL')")
    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable postId: Long
    ): ResponseEntity<PostResponseWithComment> {
        return ResponseEntity.ok(postService.getPost(postId))
    }

    @PreAuthorize("hasRole('GENERAL')")
    @PostMapping
    fun createPost(
        @RequestBody request: CreatePostRequest,
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal
    ): ResponseEntity<PostResponse> {
        return ResponseEntity.ok(postService.createPost(request, memberPrincipal))
    }

    @PreAuthorize("hasRole('GENERAL')")
    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody request: UpdatePostRequest,
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal
    ): ResponseEntity<PostResponse> {
        return ResponseEntity.ok(postService.updatePost(postId, request, memberPrincipal))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GENERAL')")
    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId: Long,
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(postService.deletePost(postId, memberPrincipal))
    }
}