package com.bstudio.tienhiep.domain.model

data class BookDetailModel(
    val bookTitle: String,
    val bookBanner: String,
    val totalChapter: Int,
    val totalChapterPerWeek: String,
    val totalView: String,
    val totalLike: String,
    val totalFollow: String,
    val bookDescription: String,
    val bookLink: String,
    val listChapter: List<String>,
)
