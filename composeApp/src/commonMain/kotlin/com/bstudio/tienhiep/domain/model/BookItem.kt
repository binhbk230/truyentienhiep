package com.bstudio.tienhiep.domain.model

data class BookItem(
    val bookName: String?,
    val bookLink: String?,
    val bookIcon: String?,
    val bookView: String?,
    val score: String?,
    val bookIntro: String? = null,
    val totalChap: Int? = 0,
)
