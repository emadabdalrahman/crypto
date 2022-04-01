package com.example.cryptography

import com.example.crypto.EncryptedField

//@EncryptedField
data class Todo(
    @EncryptedField var Todotitle: String = "",
    @EncryptedField var TodobackgroundImage: String = "",
    @EncryptedField var TodobackgroundColor: String = "",
     var Todolabel: String = "",
    @EncryptedField var Todofolder: String = "",
)