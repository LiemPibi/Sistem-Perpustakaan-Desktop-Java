# Laporan Debugging

| No | Error | Penyebab | Solusi | Status |
| :---- | :---- | :---- | :---- | :---- |
| 1 | `SQLException: Communications link failure` | MySQL Server belum menyala atau URL database salah. | Menyalakan MySQL dan mengecek URL di `DatabaseConnection`. | Terselesaikan. |
| 2 | `SQLIntegrityConstraintViolationException` saat hapus anggota/buku | Data masih digunakan oleh tabel `peminjaman`. | Hapus transaksi terkait atau gunakan data master yang belum pernah dipakai transaksi. | Terselesaikan. |
| 3 | `NumberFormatException` pada form | Kolom ID atau stok diisi teks non-angka. | Menambahkan penanganan error dan pesan dialog pada form. | Terselesaikan. |
| 4 | `IllegalStateException: Buku sedang habis` | Petugas mencoba meminjam buku dengan stok 0\. | Menambahkan validasi stok sebelum transaksi peminjaman. | Terselesaikan. |

## Langkah Debugging yang Disarankan Saat Wawancara

1. Pasang breakpoint pada method `pinjamBuku` di `LibraryService`.  
2. Jalankan aplikasi dari IDE dalam mode debug.  
3. Isi ID anggota dan buku di form peminjaman.  
4. Amati nilai `stok`, `tanggalPinjam`, dan objek `Peminjaman`.  
5. Lanjutkan eksekusi dan cek perubahan pada tabel laporan.

