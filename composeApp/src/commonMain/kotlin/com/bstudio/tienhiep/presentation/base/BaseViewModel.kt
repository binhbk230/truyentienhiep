package com.bstudio.tienhiep.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<UiState, Intent>(initState: UiState): ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(initState)
    internal val uiState = _uiState.asStateFlow()

    fun updateUiState(newUiState: UiState) {
        _uiState.value = newUiState
    }

    abstract fun processIntent(intent: Intent)
}