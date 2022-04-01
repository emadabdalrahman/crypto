package com.example.crypto_compiler

import com.squareup.javapoet.*
import javax.lang.model.element.Modifier


data class DataClass(val className: String) {

    var packageName: String = "com.example.crypto"
    var parameters: ArrayList<Pair<String, TypeName>> = arrayListOf()

    fun toFileSpac(): JavaFile {
        return JavaFile.builder(packageName,classSpac()).build()
    }

    private fun classSpac(): TypeSpec {
        val classBuilder = TypeSpec.classBuilder("${className}Encrypted")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)

        for (parameter in parameters) {
            classBuilder.addField(parameter.second, parameter.first, Modifier.PUBLIC)
        }
        return classBuilder.build()
    }

}


