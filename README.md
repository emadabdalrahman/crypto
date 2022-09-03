
# Crypto lib

- Encryption library that works with annotations like Gson
- it has an encryption processor that run at compile time to generate kotlin files for data classes that had annotated
- encryption happened at field level so lib will look for each filed and see if it is annotated to be encrypted or not and if that field is an object or collection it will look inside it, also you can choose fields to be ignored
- you can create your encryption engine to be used for encrypting and decrypting

# How To Use
create you data class
```
data class Person(  
  @EncryptedField val name: String = "user name",  
  @EncryptedField val age: Int = 20,  
  @EncryptedField val address: Address = Address()  
)
```
```
data class Address(  
  @EncryptedField val lat: Int = 5464,  
  @EncryptedField val lon: Int = 6239  
)
```

crypto-compiler will generate classes have same name plus `Encrypted` of class that had field annotated with @EncryptedField  at compile time as follow
```
public final class PersonEncrypted {  
  public byte[] name;  
  
  public byte[] age;  
  
  public AddressEncrypted address;  
}
```
```
public final class AddressEncrypted {  
  public byte[] lat;  
  
  public byte[] lon;  
}
```

# encrypt
to encrypt data class use `crypto()`

```
override fun onCreate(savedInstanceState: Bundle?) {  
  super.onCreate(savedInstanceState)  
  setContentView(R.layout.activity_main)  
  val crypto = Crypto("ksndvlk")  
  val personEncrypted = PersonEncrypted()  
  crypto.encryptObj(Person(),personEncrypted)    
}
```
now `personEncrypted` will hold encripted data


- if you want to ignore field just **Don't** annotate it with `@EncryptedField` like following
```
data class Person(  
  val id : String,
  @EncryptedField val name: String = "user name",  
  @EncryptedField val age: Int = 20,  
  @EncryptedField val address: Address = Address()  
)
```
now id will not be encrypted

# 
