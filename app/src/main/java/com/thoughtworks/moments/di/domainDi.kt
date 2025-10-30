package com.thoughtworks.moments.di

import com.thoughtworks.moments.domain.repository.MomentRepository
import com.thoughtworks.moments.domain.useCase.GetInitialTweetsUseCase
import com.thoughtworks.moments.domain.useCase.LoadMoreTweetsUseCase
import org.koin.dsl.module

val domainDi = module {
    factory { GetInitialTweetsUseCase(get<MomentRepository>()) }
    factory { LoadMoreTweetsUseCase(get<MomentRepository>()) }
}