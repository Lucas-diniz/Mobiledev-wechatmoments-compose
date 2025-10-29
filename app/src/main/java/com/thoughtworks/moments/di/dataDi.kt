package com.thoughtworks.moments.di

import com.thoughtworks.moments.data.remote.repository.MomentRepositoryDefault
import com.thoughtworks.moments.domain.repository.MomentRepository
import org.koin.dsl.module

val dataDi = module {
    single<MomentRepository> { MomentRepositoryDefault() }
}