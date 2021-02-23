# POC - Resilience with Kotlin
## Motivation
This project has been created for personal tests and proofs-of-concept of building fault-tolerant/resilient services.

Resilience is a real need in an application, especially in a microservices architecture, where there is a huge amount of communication between services.

## Tech Stack
- [Kotlin](https://kotlinlang.org/)
- [Resilience4J](https://resilience4j.readme.io/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Reactive WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
- [Project Reactor](https://projectreactor.io/)
- [JUnit 5](https://junit.org/junit5/)
- [Mockk](https://mockk.io/)

## Fault-Tolerance
### - Retry
Used in the [IbgeLocationStateClient](https://github.com/marcosjordao/resilience-poc/blob/main/src/main/kotlin/com/marcosjordao/resiliencepoc/thirdparty/ibge/client/IbgeLocationStateClient.kt).

Can be parametrized in the [application.yml](https://github.com/marcosjordao/resilience-poc/blob/main/src/main/resources/application.yml) file:
```yaml
resilience:
  retry:
    maxAttempts: 3
    waitDuration: 500
```

### - Circuit Breaker
Used in the [IbgeLocationCityClient](https://github.com/marcosjordao/resilience-poc/blob/main/src/main/kotlin/com/marcosjordao/resiliencepoc/thirdparty/ibge/client/IbgeLocationCityClient.kt).

Can be parametrized in the [application.yml](https://github.com/marcosjordao/resilience-poc/blob/main/src/main/resources/application.yml) file:
```yaml
resilience:
  circuit-breaker:
    slidingWindowSize: 10
    failureRateThreshold: 70.0
    slowCallRateThreshold: 70.0
    slowCallDurationThreshold: 500
    waitDurationInOpenState: 60000
```

### - TO-DO
- Bulkhead
- RateLimiter
- TimeLimiter


- Combined strategies
