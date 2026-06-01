# Manual Instalasi

## Prasyarat

1. Java.  
2. Apache Maven 3.9 atau fitur Maven bawaan NetBeans/IntelliJ IDEA.  
3. MySQL Server.  
4. MySQL Connector/J sudah otomatis dikelola oleh Maven melalui `pom.xml`.

## Langkah Instalasi

1. Clone atau buka folder project ini di IntelliJ IDEA.  
2. Buat database dengan menjalankan file `database/perpustakaan.sql` di MySQL.  
3. Sesuaikan kredensial database jika diperlukan:  
   - Default URL: `jdbc:mysql://localhost:3306/perpustakaan?serverTimezone=UTC`  
   - Default user: `root`  
   - Default password: kosong  
4. Jalankan aplikasi: (Menggunakan cmd pada File Sistem-Perpustakaan-Desktop-Java)

mvn clean package

mvn exec:java \-Dexec.mainClass=id.lsp.perpustakaan.LibraryApplication

Jika tidak memakai plugin exec Maven, jalankan class `id.lsp.perpustakaan.LibraryApplication` dari IDE.

## Akun Login Demo

- Username: `admin`  
- Password: `admin123`

## Generate JavaDoc

mvn javadoc:javadoc

Dokumentasi akan dibuat di `target/site/apidocs`.  
