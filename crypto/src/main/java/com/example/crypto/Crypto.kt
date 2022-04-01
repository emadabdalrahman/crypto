package com.example.crypto

import com.example.crypto.wrapper.*
import java.lang.reflect.Field
import java.util.ArrayList


class Crypto(
    private val secretKey: String,
    private val engine: CryptographyEngine = AES(secretKey),
    private val wrapper: Wrapper = ByteArrayWrapper()
) : PrimitiveEncryption(wrapper, engine) {

    override fun encrypt(byteArray: ByteArray): ByteArray {
        return engine.encrypt(byteArray)
    }

    override fun decrypt(byteArray: ByteArray): ByteArray {
        return engine.decrypt(byteArray)
    }

    override fun encryptSerializable(obj: Any): ByteArray {
        return encrypt(wrapper.serialize(obj))
    }

    override fun decryptSerializable(byteArray: ByteArray): Any {
        return wrapper.deserialize(decrypt(byteArray))!!
    }

    private fun encryptPrimitiveFiled(field: Field, obj: Any, container: Any) {
        field.isAccessible = true
        val name = field.name
        var content = field[obj]

        val eField = container::class.java.getField(name)
        eField.isAccessible = true

        val eContent = engine.encrypt(wrapper.serialize(content))
        eField.set(container, eContent)
    }

    //  weather:Weather
    private fun encryptReferenceFiled(field: Field, obj: Any, container: Any) {
        field.isAccessible = true
        val name = field.name
        val content = field[obj]

        val eField = container::class.java.getField(name)
        eField.isAccessible = true

        val eInstance = field.newEncryptedInstance()
        eField.set(container, eInstance)

        encryptObj(content, eInstance)
    }
     private fun encryptCollectionOfReferenceField(field: Field, obj: Any, container: Any) {
         field.isAccessible = true
         val  collection = field.get(obj) as Collection<Any>

         val eContentList =  ArrayList<Any>(collection.size)
         collection.forEach {
             val eContainer = it::class.java.newEncryptedInstance()
             encryptObj(it,eContainer)
             eContentList.add(eContainer)
         }

         val eContent = field.newInstanceOfCollection(eContentList)
         val eField = container::class.java.getField(field.name)
         eField.isAccessible = true
         eField.set(container,eContent)

    }

    private fun encryptCollectionOfPrimitiveField(field: Field, obj: Any, container: Any) {
        field.isAccessible = true
        val  collection = field.get(obj) as Collection<Any>
        val eContentList =  ArrayList<ByteArray>(collection.size)
        collection.forEach {
            eContentList.add(engine.encrypt(wrapper.serialize(it)))
        }
        val eContent = field.newInstanceOfCollection(eContentList)

        val eField = container::class.java.getField(field.name)
        eField.isAccessible = true

        eField.set(container,eContent)
    }

    private fun encryptCollectionField(field: Field, obj: Any, container: Any) {
        if (field.isCollectionOfPrimitive()) {
            encryptCollectionOfPrimitiveField(field,obj, container)
        } else {
            encryptCollectionOfReferenceField(field, obj, container)
        }
    }

    fun encryptObj(obj: Any, container: Any) {
        val fields = obj::class.java.declaredFields
        fields.forEach {
            if (it.isEncrypted()) {
                if (it.type.is_primitive()) {
                    encryptPrimitiveFiled(it, obj, container)
                } else {
                    if (it.isCollection()) {
                        encryptCollectionField(it, obj, container)
                    } else {
                        encryptReferenceFiled(it, obj, container)
                    }
                }
            } else {
                it.isAccessible = true
                container::class.java.getField(it.name).apply {
                    isAccessible = true
                    set(container, it[obj])
                }
            }
        }
    }

}