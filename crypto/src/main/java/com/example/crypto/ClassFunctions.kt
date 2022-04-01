package com.example.crypto.wrapper

import com.example.crypto.EncryptedField
import java.lang.reflect.Field
import java.util.*


/**
 * check if given name is primitive data type or not
 * @return true if is primitive
 */
fun Class<*>.is_primitive(): Boolean {
    return when (this) {
        java.lang.Boolean::class.java, Boolean::class.java -> true
        java.lang.Character::class.java, Char::class.java -> true
        java.lang.Byte::class.java, Byte::class.java -> true
        java.lang.Float::class.java, Float::class.java -> true
        java.lang.Double::class.java, Double::class.java -> true
        java.lang.Integer::class.java, Int::class.java -> true
        java.lang.Long::class.java, Long::class.java -> true
        java.lang.Short::class.java, Short::class.java -> true
        java.lang.String::class.java, String::class.java -> true
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
fun Field.isCollection(): Boolean {
    return isList() or isQueue() or isSet()
}

/**
 * if Field is from type collection the return will be canonical name for the generic that's
 * collection made of example if element from type java.util.ArrayList<String> the return will be String
 */
fun Field.collectionOfName(): String {
    val type = type.canonicalName.toString().substringAfter("<")
    return type.substring(0, type.length - 1)
}

fun Field.collectionOfClass(): Class<*> {
    return Class.forName(collectionOfName())
}

/**
 * check if element type is collection from type list it's support
 *  ArrayList
 *  LinkedList
 *  Vector
 *  Stack
 *  Array
 */
fun Field.isList(): Boolean {
    return when (type) {
        ArrayList::class.java -> true
        LinkedList::class.java -> true
        Vector::class.java -> true
        Stack::class.java -> true
        Array::class.java -> true
        else -> false
    }
}

/**
 * check if element type is collection from type set it's support
 *  HashSet
 *  LinkedHashSet
 *  TreeSet
 */
fun Field.isSet(): Boolean {
    return when (type) {
        HashSet::class.java -> true
        LinkedHashSet::class.java -> true
        TreeSet::class.java -> true
        else -> false
    }
}

/**
 * check if element type is collection from type queue it's support
 *  PriorityQueue
 *  ArrayDeque
 */
fun Field.isQueue(): Boolean {
    return when (type) {
        PriorityQueue::class.java -> true
        ArrayDeque::class.java -> true
        else -> false
    }
}

fun Field.isIterable(): Boolean {
    val superTree = arrayListOf<Class<out Any>>()
    val interfaceTree = arrayListOf<Class<out Any>>()
    var parent = type.superclass
    while (parent != null) {
        superTree.add(parent)
        interfaceTree.addAll(parent.interfaces)
        parent = parent.superclass
    }
    return interfaceTree.contains(Collection::class.java)
}

fun Field.isAssignableFromAny(vararg c: Class<*>): Boolean {
    val type = type
    c.forEach {
        if (type.isAssignableFrom(it)) return true
    }
    return false
}

fun Field.newInstance(): Any {
    return Class.forName(type.canonicalName)
        .getConstructor()
        .newInstance()
}


fun Field.newInstanceOfPrimitiveCollection(list: List<ByteArray>): Any {
    var obj = newInstance()
    when {
        isAssignableFromAny(
            ArrayList::class.java,
            LinkedList::class.java,
            Vector::class.java,
            Stack::class.java,
            ArrayDeque::class.java,
            PriorityQueue::class.java
        ) -> {
            obj as AbstractCollection<ByteArray>
            obj.addAll(list)
        }

        type.isAssignableFrom(Array::class.java) -> {
            obj as Array<ByteArray>
            list.forEachIndexed{index,bytearray ->
                obj[index] = bytearray
            }
        }
        else -> {}
    }
    return obj
}

fun <T> Field.newInstanceOfCollection(list: List<T>): Any {
    var obj = newInstance()
    when {
        isAssignableFromAny(
            ArrayList::class.java,
            LinkedList::class.java,
            Vector::class.java,
            Stack::class.java,
            ArrayDeque::class.java,
            PriorityQueue::class.java
        ) -> {
            obj as AbstractCollection<T>
            obj.addAll(list)
        }

        type.isAssignableFrom(Array::class.java) -> {
            obj as Array<T>
            list.forEachIndexed{index,bytearray ->
                obj[index] = bytearray
            }
        }
        else -> {}
    }
    return obj
}

fun Field.newEncryptedInstance(): Any {
    return Class.forName("com.example.crypto." + type.simpleName + "Encrypted")
        .getConstructor()
        .newInstance()

}

fun Class<*>.newEncryptedInstance(): Any {
    return Class.forName("com.example.crypto." + simpleName + "Encrypted")
        .getConstructor()
        .newInstance()

}

fun Field.isCollectionOfPrimitive(): Boolean {
    if (isCollection()) {
        val className = genericType.toString().substringAfter("<").dropLast(1)
        return Class.forName(className).is_primitive()
    }
    return false
}


fun String.containsAtLestOne(vararg char: CharSequence): Boolean {
    char.forEach {
        return this.contains(it)
    }
    return false
}

fun Field.isEncrypted(): Boolean {
    return isAnnotationPresent(EncryptedField::class.java)
}