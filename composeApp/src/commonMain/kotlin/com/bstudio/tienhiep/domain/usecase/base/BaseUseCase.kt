package com.bstudio.tienhiep.domain.usecase.base

abstract class BaseUseCase<Input, Output> {
    abstract suspend fun execute(input: Input): Output
}