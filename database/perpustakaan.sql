CREATE DATABASE IF NOT EXISTS perpustakaan;
USE perpustakaan;

CREATE TABLE IF NOT EXISTS anggota (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    alamat TEXT,
    telepon VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS buku (
    id INT AUTO_INCREMENT PRIMARY KEY,
    judul VARCHAR(200) NOT NULL,
    penulis VARCHAR(100) NOT NULL,
    stok INT NOT NULL DEFAULT 0,
    CONSTRAINT chk_stok_non_negatif CHECK (stok >= 0)
);

CREATE TABLE IF NOT EXISTS peminjaman (
    id INT AUTO_INCREMENT PRIMARY KEY,
    anggota_id INT NOT NULL,
    buku_id INT NOT NULL,
    tanggal_pinjam DATE NOT NULL,
    tanggal_kembali DATE,
    FOREIGN KEY (anggota_id) REFERENCES anggota(id),
    FOREIGN KEY (buku_id) REFERENCES buku(id)
);

CREATE INDEX idx_judul ON buku(judul);
CREATE INDEX idx_peminjaman_tanggal ON peminjaman(tanggal_pinjam);

INSERT INTO anggota (nama, alamat, telepon) VALUES
('Ani Wijaya', 'Jl. Melati No. 1', '081234567890'),
('Budi Santoso', 'Jl. Kenanga No. 2', '081298765432');

INSERT INTO buku (judul, penulis, stok) VALUES
('Pemrograman Java Dasar', 'Andi Nugroho', 5),
('Basis Data MySQL', 'Sari Pratama', 3),
('Analisis dan Desain Sistem', 'Dewi Lestari', 2);
