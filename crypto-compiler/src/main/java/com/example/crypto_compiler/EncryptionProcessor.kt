package com.example.crypto_compiler

import com.example.crypto.EncryptedField
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

class EncryptionProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf<String>().apply {
            add(EncryptedField::class.java.canonicalName)
        }
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.RELEASE_8
    }

    override fun process(set: Set<TypeElement?>, roundEnvironment: RoundEnvironment): Boolean {
        val annotatedElement = roundEnvironment
            .getElementsAnnotatedWith(EncryptedField::class.java)
        val mapper = AnnotationMapper()

        return if (mapper.validate(annotatedElement,processingEnv.messager)){
            mapper.getDataClasses(annotatedElement).forEach { clazz ->
                clazz.toFileSpac().runCatching { writeTo(processingEnv.filer) }
            }
            true
        }else false
    }
}