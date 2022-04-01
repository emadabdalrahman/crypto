package com.example.crypto_compiler

import com.example.crypto.EncryptedField
import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import java.util.*
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic

class AnnotationMapper {

    /**
     * validation will look at element that had been annotated with Encryption annotation
     * if element type is not supported it will show error massage
     */
    fun validate(element: MutableSet<out Element>, message: Messager): Boolean {
        element.forEach {
            if (it.kind != ElementKind.FIELD) {
                message.printMessage(
                    Diagnostic.Kind.ERROR,
                    "EncryptedField should use with fields only you used it with ${it.kind} package ${it.enclosingElement} at ${it.simpleName}"
                )
                return false
            }
        }
        return true
    }

    fun getDataClasses(element: MutableSet<out Element>): MutableList<DataClass> {
        val classes = mutableSetOf<Element>()
        element.forEach {
            if (it.enclosingElement.kind.isClass) {
                classes.add(it.enclosingElement)
            }
        }
        val dataclasses = mutableListOf<DataClass>()

        classes.forEach { clazz ->

            val dataClass = DataClass(clazz.simpleName.toString())
            clazz.enclosedElements.forEach {
                if (it.kind.isField) {
                    dataClass.parameters.add(getParameter(it, classes))
                }
            }
            dataclasses.add(dataClass)
        }

        return dataclasses
    }

    private fun getParameter(
        element: Element,
        classes: MutableSet<Element>
    ): Pair<String, TypeName> {
        val annotation = element.getAnnotation(EncryptedField::class.java)
        var name = element.simpleName.toString()
        var type: TypeName = TypeName.get(element.asType())

        if (annotation == null) {
            return name to type
        }

        if (annotation.name.isNotEmpty()) {
            name = annotation.name
        }

        type = if (element.isPrimitive()) {
            //primitive type
            ArrayTypeName.of(TypeName.BYTE)
        } else {
            //reference type
            getReferenceType(element, classes)
        }

        return name to type
    }

    private fun getReferenceType(element: Element, classes: MutableSet<Element>): TypeName {
        return if (element.isCollection()) {
            getCollectionType(element, classes)
        } else {
            if (element.isEncryptedReference(classes)) {
                //encrypted reference
                ClassName.get(
                    "com.example.crypto",
                    element.asType().simpleName() + "Encrypted"
                )
            } else {
                //not encrypted reference
                ArrayTypeName.of(TypeName.BYTE)
            }
        }
    }

    private fun getCollectionType(element: Element, classes: MutableSet<Element>): TypeName {
        when {
            element.isCollectionOfEncryptedReference(classes) -> {
                //collection with encrypted reference
                return ParameterizedTypeName.get(
                    element.collectionSimpleName(),
                    ClassName.get(
                        "com.example.crypto",
                        element.collectionOf().substringAfterLast(".") + "Encrypted"
                    )
                )
            }
            element.isCollectionOfReference() -> {
                //collection with not encrypted reference
                return collectionOfByteArray(element)
            }
            else -> {
                //collection with primitive
                return collectionOfByteArray(element)
            }
        }
    }

    private fun collectionOfByteArray(element: Element): TypeName {
        val collectionType = element.collectionSimpleName()
        val byteArray = ArrayTypeName.of(TypeName.BYTE)
        return ParameterizedTypeName.get(collectionType, byteArray)
    }

}


/**
 * get simple name for type for example
 * if the type is java.lang.String the return will be "String"
 */
private fun TypeMirror.simpleName(): String {
    return toString().substringAfterLast(".")
}

/**
 * if element is from type collection the return will be simple name for that collection
 * example if element from type java.util.ArrayList<String> the return will be ArrayList
 */
fun Element.collectionSimpleName(): ClassName {
    return ClassName.get(
        "java.util",
        collectionCanonicalName().replace("java.util.", "")
    )
}

/**
 * if element is from type collection the return will be canonical class name for that collection
 * example if element from type java.util.ArrayList<String> the return will be java.util.ArrayList
 */
fun Element.collectionCanonicalName(): String {
    return asType().toString().substringBefore("<")
}

/**
 * check if element type is encrypted class
 * @param classes set of encrypted classes
 */
private fun Element.isEncryptedReference(classes: Set<Element>): Boolean {
    classes.forEach {
        if (it.asType().toString() == this.asType().toString())
            return true
    }
    return false
}

/**
 * check if element type is collection of encrypted class
 * @param classes set of encrypted classes
 */
private fun Element.isCollectionOfEncryptedReference(classes: Set<Element>): Boolean {
    if (isCollection()) {
        val name = collectionOf()
        classes.forEach {
            if (it.asType().toString() == name) return true
        }
    }
    return false
}

/**
 * check if element type is collection of reference type
 */
private fun Element.isCollectionOfReference(): Boolean {
    if (isCollection()) {
        val name = collectionOf()
        return !isPrimitive(name)
    }
    return false
}

/**
 * check if element is primitive date type
 */
private fun Element.isPrimitive(): Boolean {
    return isPrimitive(asType().toString())
}

/**
 * check if given name is primitive data type or not
 * @param name canonicalName
 * @return true if is primitive
 */
fun isPrimitive(name: String): Boolean {
    return when (name) {
        TypeName.BOOLEAN.toString() -> true
        TypeName.CHAR.toString() -> true
        TypeName.BYTE.toString() -> true
        TypeName.FLOAT.toString() -> true
        TypeName.DOUBLE.toString() -> true
        TypeName.INT.toString() -> true
        TypeName.LONG.toString() -> true
        TypeName.SHORT.toString() -> true
        String::class.java.canonicalName -> true
        else -> false
    }
}

/**
 * check if element type is from collection
 * @see isList
 * @see isQueue
 * @see isSet
 * @return true if is collection
 */
private fun Element.isCollection(): Boolean {
    return isList() or isQueue() or isSet()
}

/**
 * if element is from type collection the return will be canonical name for the generic that's
 * collection made of example if element from type java.util.ArrayList<String> the return will be String
 */
private fun Element.collectionOf(): String {
    val type = asType().toString().substringAfter("<")
    return type.substring(0, type.length - 1)
}

/**
 * check if element type is collection from type list it's support
 *  ArrayList
 *  LinkedList
 *  Vector
 *  Stack
 *  Array
 */
private fun Element.isList(): Boolean {
    val canonicalName = asType().toString()
    return canonicalName.containsAtLestOne(
        ArrayList::class.java.canonicalName,
        LinkedList::class.java.canonicalName,
        Vector::class.java.canonicalName,
        Stack::class.java.canonicalName,
        Array::class.java.canonicalName
    )
}

/**
 * check if element type is collection from type set it's support
 *  HashSet
 *  LinkedHashSet
 *  TreeSet
 */
private fun Element.isSet(): Boolean {
    val canonicalName = asType().toString()
    return canonicalName.containsAtLestOne(
        HashSet::class.java.canonicalName,
        LinkedHashSet::class.java.canonicalName,
        TreeSet::class.java.canonicalName,
    )
}
/**
 * check if element type is collection from type queue it's support
 *  PriorityQueue
 *  ArrayDeque
 */
private fun Element.isQueue(): Boolean {
    val canonicalName = asType().toString()
    return canonicalName.containsAtLestOne(
        PriorityQueue::class.java.canonicalName,
        ArrayDeque::class.java.canonicalName,
    )
}


fun String.containsAtLestOne(vararg char: CharSequence): Boolean {
    char.forEach {
        return this.contains(it)
    }
    return false
}