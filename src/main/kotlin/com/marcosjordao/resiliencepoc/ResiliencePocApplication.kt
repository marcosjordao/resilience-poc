package com.marcosjordao.resiliencepoc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ResiliencePocApplication

fun main(args: Array<String>) {
	runApplication<ResiliencePocApplication>(*args)
}
