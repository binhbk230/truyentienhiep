package com.bstudio.tienhiep.data

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import com.fleeksoft.ksoup.nodes.Document

interface TangThuVienApi {
    suspend fun getHomePage(): Document
}

class TangThuVienApiIml : TangThuVienApi {
    companion object {
        private const val HOME_PAGE_URL = "https://truyen.tangthuvien.vn/"
    }

    override suspend fun getHomePage(): Document {
        return Ksoup.parseGetRequest(url = HOME_PAGE_URL)
    }
}
