package com.bstudio.tienhiep.domain.model

data class HomePageResponse(
    val data: String,
    val topViewOfTheWeekList: List<BookItem>,
    val topFavoriteOfTheWeekList: List<BookItem>,
    val topRecommendOfTheWeekList: List<BookItem>,
    val topFollowOfTheWeekList: List<BookItem>,
    val topRateList: List<BookItem>,
    val topRecommendList: List<BookItem>,
    val newChapList: List<BookItem>,
)