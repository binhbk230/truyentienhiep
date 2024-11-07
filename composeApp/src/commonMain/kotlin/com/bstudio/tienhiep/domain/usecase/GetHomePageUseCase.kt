package com.bstudio.tienhiep.domain.usecase

import com.bstudio.tienhiep.data.TangThuVienApi
import com.bstudio.tienhiep.domain.config.ParserConfig
import com.bstudio.tienhiep.domain.model.BookItem
import com.bstudio.tienhiep.domain.model.HomePageResponse
import com.bstudio.tienhiep.domain.usecase.base.BaseUseCase
import com.fleeksoft.ksoup.nodes.Document
import com.fleeksoft.ksoup.nodes.Element
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GetHomePageUseCase(private val tangThuVienApi: TangThuVienApi) : BaseUseCase<Any, HomePageResponse>() {
    override suspend fun execute(input: Any): HomePageResponse {
        val homeDocument = tangThuVienApi.getHomePage()
        return parseResponseData(homeDocument)
    }

    private suspend fun parseResponseData(document: Document): HomePageResponse = withContext(Dispatchers.IO) {
        val topViewOfTheWeekList: MutableList<BookItem> = ArrayList()
        val topFavoriteOfTheWeekList: MutableList<BookItem> = ArrayList()
        val topRecommendOfTheWeekList: MutableList<BookItem> = ArrayList()
        val topFollowOfTheWeekList: MutableList<BookItem> = ArrayList()
        val topRateList: MutableList<BookItem> = ArrayList()
        val topRecommendList: MutableList<BookItem> = ArrayList()
        val newChapList: MutableList<BookItem> = ArrayList()

        // Parse 3 first items of 3 lists
        val firstBookDocumentList = document.select(ParserConfig.FIRST_BOOK_OF_THE_TOP_WEEK)
        val firstBookViewList = document.select(ParserConfig.PARSE_VIEW_FIRST_BOOK)

        if (firstBookDocumentList.size >= 4 && firstBookViewList.size >= 4) {
            topViewOfTheWeekList.add(mapElementToBookItem(firstBookDocumentList[1], firstBookViewList[1]))
            topFavoriteOfTheWeekList.add(mapElementToBookItem(firstBookDocumentList[2], firstBookViewList[2]))
            topRecommendOfTheWeekList.add(mapElementToBookItem(firstBookDocumentList[0], firstBookViewList[0]))
            topFollowOfTheWeekList.add(mapElementToBookItem(firstBookDocumentList[3], firstBookViewList[3]))
        }

        // Parse other items of top list
        val otherBookDocumentList = document.select(ParserConfig.OTHER_BOOK_OF_THE_TOP_WEEK)
        val otherBookViewList = document.select(ParserConfig.PARSE_VIEW_OTHER_BOOK)

        if (otherBookDocumentList.size >= 46 && otherBookViewList.size >= 46) {
            for (i in 0 until 9) {
                topRecommendOfTheWeekList.add(mapElementToBookItem(otherBookDocumentList[i], otherBookViewList[i]))
            }
            for (i in 19 until 28) {
                topViewOfTheWeekList.add(mapElementToBookItem(otherBookDocumentList[i], otherBookViewList[i]))
            }
            for (i in 28 until 37) {
                topFavoriteOfTheWeekList.add(mapElementToBookItem(otherBookDocumentList[i], otherBookViewList[i]))
            }
            for (i in 37 until 46) {
                topFollowOfTheWeekList.add(mapElementToBookItem(otherBookDocumentList[i], otherBookViewList[i]))
            }
        }

        // Parse top rated books
        val listTopRateBookElement = document.select(ParserConfig.TOP_RATE_BOOK)
        listTopRateBookElement.forEach { element ->
            val bookTitle =
                element.select(ParserConfig.PARSE_A_TAG).select(ParserConfig.PARSE_IMG_TAG).attr(ParserConfig.TAG_ALT)
            val bookLink = element.select(ParserConfig.PARSE_A_TAG).attr(ParserConfig.HREF_ATTRIBUTE)
            val bookImage = element.select(ParserConfig.PARSE_A_TAG).select(ParserConfig.PARSE_IMG_TAG)
            val bookScore = element.select(ParserConfig.PARSE_A_TAG).select(ParserConfig.TAG_SPAN).text()

            if (bookTitle.isNotEmpty() && bookScore.isNotEmpty()) {
                if (newChapList.size < 9) {
                    newChapList.add(
                        BookItem(
                            bookTitle,
                            bookLink,
                            bookImage.attr(ParserConfig.SRC_ATTRIBUTE),
                            "",
                            bookScore
                        )
                    )
                } else {
                    topRateList.add(
                        BookItem(
                            bookTitle,
                            bookLink,
                            bookImage.attr(ParserConfig.SRC_ATTRIBUTE),
                            "",
                            bookScore
                        )
                    )
                }
            }
        }

        val data = document.outerHtml()

        // Returning the result as a coroutine with `withContext` ensures it runs in the appropriate dispatcher
        HomePageResponse(
            data,
            topViewOfTheWeekList,
            topFavoriteOfTheWeekList,
            topRecommendOfTheWeekList,
            topFollowOfTheWeekList,
            topRateList,
            topRecommendList,
            newChapList
        )
    }

    private fun mapElementToBookItem(bookElement: Element, bookViewElement: Element): BookItem {
        return try {
            // Extracting the link, book name, and view from the provided elements
            val bookLink = bookElement.select(ParserConfig.PARSE_A_TAG).attr(ParserConfig.HREF_ATTRIBUTE)
            val bookName = bookElement.select(ParserConfig.PARSE_A_TAG).first()
                ?.text() // Assuming book name is the first text node
            val bookView = bookViewElement.text() // Assuming bookView is the text inside the bookViewElement

            // Return a BookItem with the extracted data
            BookItem(bookName ?: "", bookLink, "", bookView, null)
        } catch (e: Exception) {
            // Return a default BookItem in case of error
            BookItem("", "", "", "", "")
        }
    }

}