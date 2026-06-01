# Laporan Debugging

| No | Error | Penyebab | Solusi | Status |
| :---- | :---- | :---- | :---- | :---- |
| 1 | `SQLException: Communications link failure` | MySQL Server belum menyala atau URL database salah. | Menyalakan MySQL dan mengecek URL di `DatabaseConnection`. | Terselesaikan. |
| 2 | `Table 'perpustakaan.anggota' doesn't exist` | Data sudah ada tapi tidak tersammbung dengan baik | Memastikan AnggotaDAO.java BukuDAO.java PeminjamanDAO.java Tersambung dengan baik. | Terselesaikan. |
| 3 | `SQLIntegrityConstraintViolationException` saat hapus anggota/buku | Data masih digunakan oleh tabel `peminjaman`. | Hapus transaksi terkait atau gunakan data master yang belum pernah dipakai transaksi. | Terselesaikan. |
| 4 | `NumberFormatException` pada form | Kolom ID atau stok diisi teks non-angka. | Menambahkan penanganan error dan pesan dialog pada form. | Terselesaikan. |
| 5 | `IllegalStateException: Buku sedang habis` | Petugas mencoba meminjam buku dengan stok 0\. | Menambahkan validasi stok sebelum transaksi peminjaman. | Terselesaikan. |

