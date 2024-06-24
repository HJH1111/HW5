package com.hw5.hw5.domain.member.model

import com.hw5.hw5.domain.member.dto.MemberResponse
import com.hw5.hw5.domain.member.dto.SignUpResponse
import com.hw5.hw5.domain.post.model.Post
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "member")
class Member(

    @Column(name = "email")
    var email: String,

    @Column(name = "name")
    var name: String,

    @Column(name = "password")
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: Role,

    @OneToMany(mappedBy = "member", cascade = [(CascadeType.ALL)])
    var post: MutableList<Post> = mutableListOf(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Member.toMemberResponse(): MemberResponse = MemberResponse(
    id = id!!,
    email = email,
    name = name,
    role = role.toString()

)

fun Member.toSignUpResponse(): SignUpResponse = SignUpResponse(
    id = id!!,
    name = name,
    role = role.toString()
)