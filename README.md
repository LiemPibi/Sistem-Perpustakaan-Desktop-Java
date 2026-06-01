# Sistem Perpustakaan Desktop Java

Aplikasi desktop Java sederhana yang dirancang khusus untuk sistem perpustakaan.

## Fitur Utama

1. Login petugas.
2. Kelola anggota (CRUD).
3. Kelola buku (CRUD).
4. Katalog dan pencarian buku.
5. Peminjaman buku.
6. Pengembalian buku.
7. Laporan peminjaman.
8. Dokumentasi JavaDoc, UML, laporan debugging, dan unit testing.

## Teknologi

- Java.
- Maven.
- MySQL.
- MySQL Connector/J.

## Struktur Project

```text
src/main/java/id/lsp/perpustakaan
├── LibraryApplication.java
├── dao
│   ├── AnggotaDAO.java
│   ├── BukuDAO.java
│   ├── Crudable.java
│   └── PeminjamanDAO.java
├── model
│   ├── Anggota.java
│   ├── Buku.java
│   ├── Displayable.java
│   ├── Peminjaman.java
│   ├── Petugas.java
│   └── User.java
├── service
│   └── LibraryService.java
├── util
│   └── DatabaseConnection.java
└── view
    ├── FormAnggota.java
    ├── FormBuku.java
    ├── FormPeminjaman.java
    ├── GridLayoutPanel.java
    ├── LoginForm.java
    └── MainMenu.java
```

## Instalasi Cepat

1. Pastikan Java, Maven, dan MySQL sudah terpasang.
2. Buat database dan tabel:

```bash
mysql -u root -p < database/perpustakaan.sql
```

3. Jalankan test:

```bash
mvn test
```

4. Jalankan aplikasi dari IDE melalui class:

```text
id.lsp.perpustakaan.LibraryApplication
```

Akun demo:

- Username: `admin`
- Password: `admin123`

## Konfigurasi Database

Default koneksi ada di `DatabaseConnection` dan dapat dioverride melalui system property:

```bash
java \
  -Ddb.url="jdbc:mysql://localhost:3306/perpustakaan?serverTimezone=UTC" \
  -Ddb.user="root" \
  -Ddb.password="" \
  -cp target/classes id.lsp.perpustakaan.LibraryApplication
```

## Dokumen Sertifikasi

- Script database: `database/perpustakaan.sql`.
- UML: `docs/UML.md`.
- Manual instalasi: `docs/MANUAL_INSTALASI.md`.
- Dokumentasi program: `docs/DOKUMENTASI_PROGRAM.md`.
- Laporan debugging: `docs/LAPORAN_DEBUGGING.md`.
- Unit test: `src/test/java/id/lsp/perpustakaan/LibraryTest.java`.

## Pemetaan Unit Kompetensi

| Unit | Bukti Implementasi |
| 1. Menggunakan spesifikasi program | UML dan dokumen requirement. |
| 2. Coding guideline | Naming convention Java dan JavaDoc. |
| 3. Pemrograman terstruktur | If-else, loop, method, collection, validasi input. |
| 4. OOP | Model, inheritance, interface, polymorphism. |
| 5. Library external | MySQL Connector/J dan JUnit 5. |
| 6. Basis data | MySQL schema, CRUD DAO, foreign key, index. |
| 7. Dokumentasi program | JavaDoc, README, manual, UML. |
| 8. Debugging | Laporan debugging dan penanganan exception. |
| 9. Unit testing | JUnit 5 test suite. |

