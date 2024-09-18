package com.learning.oauth.annotations

import org.springframework.security.core.annotation.CurrentSecurityContext

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@CurrentSecurityContext(expression = "authentication.name")
annotation class CurrentOwner 