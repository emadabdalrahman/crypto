package com.example.cryptography

import com.example.crypto.EncryptedField


data class Person(
    @EncryptedField var Persontitle: String = "jhngdfbfnrmi7ryukdyjhd",
    var personint: Int = 541561,
    @EncryptedField var personlong: Long = 51561,
    @EncryptedField var Persondouble: Double = 5640.56460,
    @EncryptedField var Personchar: Char = 'c',
    @EncryptedField var Personbyte: Byte = 120,
    @EncryptedField var Personboolean: Boolean = true,
    @EncryptedField var Personshort: Short = 4510,
    var weather2: Weather = Weather(),
    @EncryptedField var weather: Weather = Weather(),


//      @EncryptedField var Personarray: ArrayList<Int> = arrayListOf(12331,2651,651),
    @EncryptedField var weatherlist: ArrayList<Weather> = arrayListOf(Weather(),Weather(),Weather()),
    @EncryptedField var Personarray1: ArrayList<Int> = arrayListOf(51),
    @EncryptedField var Personarray2: ArrayList<Long> = arrayListOf(651),
    @EncryptedField var Personarray3: ArrayList<Short> = arrayListOf(651),
    @EncryptedField var Personarray4: ArrayList<Double> = arrayListOf(0.0),
    @EncryptedField var Personarray5: ArrayList<Float> = arrayListOf(15.00f),
    @EncryptedField var Personarray6: ArrayList<Char> = arrayListOf('s'),
    @EncryptedField var Personarray7: ArrayList<String> = arrayListOf(""),
    @EncryptedField var Personarray8: ArrayList<Boolean> = arrayListOf(false),

)
