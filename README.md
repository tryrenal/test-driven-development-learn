# Test Driven Development - Learn

Testing adalah memastikan semua berjalan sesuai dengan yang di harapkan

biasanya dilakukan dengan cara menjalankan aplikasi lalu mencoba untuk menjalaan fitur yang sudah kita buat, akan tetapi hal tersebut kurang efektif dan hanya menjalankan satu percobaan (test case) dengan bayaran waktu untuk menjalankan aplikasi dan akan bermasalah jika kita akan mengembangkan aplikasi kedepannya, selain itu kamu harus mengetes satu fungsi secara berkali-kali untuk hal kamu tambahkan selanjutnya.

```sh
Testing adalah wajib dan penting
```

### Jenis dari Testing

 - Unit Test
 - Integration Test
 - UI Test (end-to-end test)

###### Unit Test
menguji berdasarkan fungsi pada aplikasi, dan di rancang untuk tidak bergantung dengan fungsi yang lain sehingga unit test yang baik adalah yang indepedent.

##### Integration Test
menguji beberapa komponen bekerja bersama dalam aplikasi.
ex : fragment berinteraksi dengan viewModel 

beda antara integration test dengan integrated test.

- integration test -> menguji interaksi antar component
- integrated test -> pengujian yang mengandalkan android component, oleh karena itu harus dijalan pada emulator (compent yang ingin di test membutuhkan app resource atau context)

##### UI Test (end-to-end test)
menguji interaksi pengguna dengan aplikasi 
ex : login

### Test Driven Development
cara pengembangan dengan mengedepankan testing, prinsip utama dalam TDD  adalah kita harus menulis test case terlebih dahulu sebelum menulis code untuk implementasi fungsionalitas (only for unit test)

##### langkah-langkah TDD
- menuliskan signature dari func
- menuliskan test case dari func
(untuk pertama akan selalu fail karena pada pertama kita tidak memiliki content dalam func)
- menuliskan logik dari func, sehingga kita dapat melakukan testing.

Pada TDD kamu hanya harus memiliki satu tujuan untuk satu test case. jangan membuat test case yang bergantung hasilnya pada test case yang lain. karena pada penerapaan TDD, saat kita menjalankan test case dan didapatkan hasil gagal, maka kita akan fokus pada test case itu saja dan bukannya mengalisa alurnya.

##### Karakteristik dari good test 
- Scope
mencakup tentang seberapa banyak code yang dinaungi oleh satu test case

- Speed
seberapa cepat dari test case kita berjalan, hal ini berpengaruh terhadap performa, jika test case kita lama untuk dijalankan maka sebaiknya kita me-refactor code kita untuk dapat mendapatkan hasil yang maksimal.

- Fidelity
bagaimana test case yang kita buat semirip mungkin dengan keadaan nyatanya.

> Not a Flaky Test (terkadang berhasil dan terkadang gagal), jangan pernah membuat test case yang bergantung dengan test case yang lain.

——————————————————————————————————————

### Catatan dalam video testing Philip lackner

Referensi : 
https://www.youtube.com/playlist?list=PLQkwcJG4YTCSYJ13G4kVIJ10X5zisB2Lq

urutan file yang akan di test pada real app dan test case harus sama.
ex : 
main : repositories -> mainRepository.kt
test : repositories -> testRepository.kt

##### Annotation : 
- @SmallTest -> digunakan pada unit test, time limit = 60, network access = no, database = no, file system access = no, use external systems = no, multiple threads = no, sleep statement = no, system properties = no.

- @MediumTest -> digunakan pada integration test, time limit = 300, network access = localhost only, database = yes, file system access = yes, use external systems = discouraged (kecil), multiple threads = yes, sleep statement = yes, system properties = yes.

- @LargeTest -> digunakan pada UI test, time limit = 900+, network access = yes, database = yes, file system access = yes, use external systems = yes, multiple threads = yes, sleep statement = yes, system properties = yes. 

Terkait dengan speed maka dari untuk menguji tentang pemanggilan data dari network API maka kita tidak akan menggunakannya seperti di real app tapi dengan cara yang lain, yaitu test-double 

test-double disini akan menggambarkan tentang bagaimana kita menerapkan repository pada real app dan pada test case.
