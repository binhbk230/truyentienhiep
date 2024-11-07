package com.bstudio.tienhiep.data

import com.bstudio.tienhiep.domain.model.BookDetailModel
import com.bstudio.tienhiep.domain.model.BookItem
import com.bstudio.tienhiep.domain.model.ChapterDetailModel
import com.bstudio.tienhiep.domain.model.HomePageResponse
import com.bstudio.tienhiep.domain.BookRepository

class BookRepositoryImpl: BookRepository {
    override suspend fun fetchHomePageData(): List<HomePageResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchBookDetailData(url: String): List<BookDetailModel> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchChapterDetailData(url: String): List<ChapterDetailModel> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchListBookData(url: String): List<List<BookItem>> {
        TODO("Not yet implemented")
    }
}