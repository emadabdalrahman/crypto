package com.example.cryptography

import com.example.crypto.Crypto
import com.example.crypto.PersonEncrypted
import com.example.crypto.wrapper.newInstanceOfPrimitiveCollection
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val crypto = Crypto("ksdnvsdklvm")
        val person = Person()
        val container = PersonEncrypted()
        crypto.encryptObj(person,container)

//        val p = person.javaClass.declaredFields.forEach {


            val df = 0
//        }

    }
}