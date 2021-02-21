package com.marcosjordao.resiliencepoc.thirdparty.ibge.api.mapper

import com.marcosjordao.resiliencepoc.thirdparty.ibge.api.response.IbgeLocationStateResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class IbgeLocationStateResponseMapperTest {

    private val mapper = IbgeLocationStateResponseMapper()

    @Test
    fun `should map IbgeLocationStateResponse to LocationStateResponse successfully`() = runBlockingTest {
        val ibgeLocationStateResponse = IbgeLocationStateResponse(
            id = 1,
            sigla = "ST",
            nome = "State",
            regiao = IbgeLocationStateResponse.IbgeLocationRegion(
                id = 2,
                sigla = "RE",
                nome = "Region"
            )
        )

        val mappedLocationStateResponse = mapper.toLocationStateResponse(ibgeLocationStateResponse)

        assertAll(
            { assertNotNull(mappedLocationStateResponse) },
            { assertEquals(ibgeLocationStateResponse.id, mappedLocationStateResponse.id) },
            { assertEquals(ibgeLocationStateResponse.sigla, mappedLocationStateResponse.acronym) },
            { assertEquals(ibgeLocationStateResponse.nome, mappedLocationStateResponse.name) }
        )
    }
}
