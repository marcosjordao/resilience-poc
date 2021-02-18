package com.marcosjordao.resiliencepoc.thirdparty.ibge.api.response

data class IbgeLocationStateResponse(
    val id: Long,
    val sigla: String,
    val nome: String,
    val regiao: IbgeRegiao
) {

    data class IbgeRegiao(
        val id: Long,
        val sigla: String,
        val nome: String
    )

}