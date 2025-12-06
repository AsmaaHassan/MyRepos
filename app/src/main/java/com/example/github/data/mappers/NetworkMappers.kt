package com.example.github.data.mappers

import com.example.github.data.models.dto.BranchDto
import com.example.github.data.models.dto.RepoDto
import com.example.github.domain.model.Branch
import com.example.github.domain.model.Repo
/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */



fun RepoDto.toDomain() = Repo(
    name = name,
    isPrivate = private,
    stars = stars,
    language = language,
    updatedAt = updatedAt,
    owner = owner.login
)

fun BranchDto.toDomain() = Branch(name = name)
