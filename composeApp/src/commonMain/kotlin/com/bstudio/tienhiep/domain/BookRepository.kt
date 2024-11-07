package com.bstudio.tienhiep.domain

import com.bstudio.tienhiep.domain.model.BookDetailModel
import com.bstudio.tienhiep.domain.model.BookItem
import com.bstudio.tienhiep.domain.model.ChapterDetailModel
import com.bstudio.tienhiep.domain.model.HomePageResponse


interface BookRepository {
    suspend fun fetchHomePageData(): List<HomePageResponse>
    suspend fun fetchBookDetailData(url: String): List<BookDetailModel>
    suspend fun fetchChapterDetailData(url: String): List<ChapterDetailModel>
    suspend fun fetchListBookData(url: String): List<List<BookItem>>
}