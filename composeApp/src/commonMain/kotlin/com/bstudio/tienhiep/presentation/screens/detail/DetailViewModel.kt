package com.bstudio.tienhiep.presentation.screens.detail

import androidx.lifecycle.ViewModel
import com.bstudio.tienhiep.data.MuseumObject
import com.bstudio.tienhiep.data.MuseumRepository
import kotlinx.coroutines.flow.Flow

class DetailViewModel(private val museumRepository: MuseumRepository) : ViewModel() {
    fun getObject(objectId: Int): Flow<MuseumObject?> =
        museumRepository.getObjectById(objectId)
}
