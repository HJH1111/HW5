package com.hw5.hw5.domain.upvote.service

import com.hw5.hw5.domain.exception.ModelNotFoundException
import com.hw5.hw5.domain.exception.UpvoteAlreadyExistException
import com.hw5.hw5.domain.member.repository.MemberRepository
import com.hw5.hw5.domain.post.model.toUpvoteResponse
import com.hw5.hw5.domain.post.repository.PostRepository
import com.hw5.hw5.domain.upvote.dto.UpvoteResponse
import com.hw5.hw5.domain.upvote.model.Upvote
import com.hw5.hw5.domain.upvote.repository.UpvoteRepository
import com.hw5.hw5.infra.security.MemberPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpvoteService(
    private val upvoteRepository: UpvoteRepository,
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun createUpvote(
        postId: Long,
        member: MemberPrincipal
    ): UpvoteResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val members = memberRepository.findByIdOrNull(member.id) ?: throw ModelNotFoundException("Member", member.id)
        if (upvoteRepository.existsByMemberIdAndPostId(members.id!!, postId)) throw UpvoteAlreadyExistException()
        upvoteRepository.save(Upvote(post, members))
        post.addUpvote()

        return post.toUpvoteResponse(
            memberRepository.findByIdOrNull(members.id!!)!!,
            post.id!!
        )
    }

    @Transactional
    fun deleteUpvote(
        postId: Long,
        member: MemberPrincipal
    ): UpvoteResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        if (!memberRepository.existsById(member.id)) throw ModelNotFoundException("Member", member.id)
        val upvote = upvoteRepository.findByMemberIdAndPostId(member.id, postId)
            ?: throw IllegalArgumentException("좋아요를 삭제할 수 없습니다")

        post.removeUpvote()
        upvoteRepository.delete(upvote)

        return post.toUpvoteResponse(
            memberRepository.findByIdOrNull(member.id)!!,
            post.id!!
        )
    }
}