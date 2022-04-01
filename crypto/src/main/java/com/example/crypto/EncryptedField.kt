package com.example.crypto


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD,AnnotationTarget.CLASS)
annotation class EncryptedField(val name: String = "")
