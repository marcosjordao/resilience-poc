package com.marcosjordao.resiliencepoc.thirdparty.ibge.exception

data class IbgeLocationException(override val message: String, override val cause: Throwable?) :
    RuntimeException(message, cause)