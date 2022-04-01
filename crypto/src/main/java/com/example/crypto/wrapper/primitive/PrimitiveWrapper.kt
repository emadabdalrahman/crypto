package com.example.crypto.wrapper

import com.example.crypto.wrapper.primitive.*

interface PrimitiveWrapper :
    ByteWrapper,
    CharWrapper,
    StringWrapper,
    BooleanWrapper,
    IntWrapper,
    LongWrapper,
    ShortWrapper,
    DoubleWrapper,
    FloatWrapper {
    fun fromPrimitive(obj: Any, type: Class<*>): ByteArray
    fun toPrimitive(byteArray: ByteArray, type: Class<*>): Any

}




