package com.example.crypto.wrapper

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class ByteArrayWrapper : Wrapper() {

//    override fun formObject(obj: Any): ByteArray {
//        return Gson().toJson(obj).toByteArray()
//    }
//
//    override fun <T> toObject(byteArray: ByteArray, c: Class<T>): T {
//        return Gson().fromJson(String(byteArray), c)
//    }

//    fun Class<*>.isPrimitive():Boolean{
//
//        if (this.isPrimitive)
//
//        return when(this){
//            Byte::class.java , String::class.java,
//        }
//    }

//    fun d(){
//        val list = listOf(1,2,3)
//        fromCollection(list)
//    }

//    fun <E, T : Collection<E>> fromCollection(collection: T): T {
//        collection.forEach {
//
//        }
//    }

    override fun serialize(obj: Any): ByteArray {
        val byteArray = ByteArrayOutputStream()
        ObjectOutputStream(byteArray).apply {
            writeObject(obj)
            close()
        }
        return byteArray.toByteArray()
    }

    override fun deserialize(byteArray: ByteArray): Any? {
        val byteArrayInputStream = ByteArrayInputStream(byteArray)
        val ois = ObjectInputStream(byteArrayInputStream)
        return ois.readObject()
    }

}