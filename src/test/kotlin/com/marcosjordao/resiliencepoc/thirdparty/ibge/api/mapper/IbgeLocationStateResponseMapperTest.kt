package com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper

import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.fixture.IbgeLocationStateResponseFixture
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class IbgeLocationStateResponseMapperTest {

    private val mapper = IbgeLocationStateResponseMapper()

    @Test
    fun `should map IbgeLocationStateResponse to LocationStateResponse successfully`() = runBlockingTest {
        val ibgeLocationStateResponse = IbgeLocationStateResponseFixture.defaultIbgeLocationStateResponse()

        val mappedLocationStateResponse = mapper.toLocationStateResponse(ibgeLocationStateResponse)

        assertAll(
            { assertNotNull(mappedLocationStateResponse) },
            { assertEquals(ibgeLocationStateResponse.id, mappedLocationStateResponse.id) },
            { assertEquals(ibgeLocationStateResponse.sigla, mappedLocationStateResponse.acronym) },
            { assertEquals(ibgeLocationStateResponse.nome, mappedLocationStateResponse.name) }
        )
    }
}
