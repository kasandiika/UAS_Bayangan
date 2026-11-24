# Laporan Praktikum Spring AI — Chapter 1 dan 2

> Lengkapi bagian bertanda **[isi hasil praktikum]** setelah menjalankan aplikasi. Jangan mengklaim hasil eksperimen sebelum benar-benar dijalankan.

## 1. Tujuan

Membangun REST API Spring Boot yang mengirimkan pertanyaan ke model melalui Spring AI, kemudian memeriksa relevansi dan ketepatan jawaban dengan evaluator.

## 2. Teori, manfaat, dan keterbatasan

Spring AI menyediakan abstraksi klien yang menyederhanakan komunikasi aplikasi Spring dengan provider model. `ChatClient` mengirim prompt dan menerima teks hasil generasi. Evaluator dapat menggunakan model untuk menilai apakah jawaban terkait dengan prompt atau faktual.

**Manfaat:** abstraksi provider yang konsisten, integrasi dengan ekosistem Spring, dan cara langsung menghubungkan hasil generasi dengan REST API.

**Keterbatasan:** respons model bersifat probabilistik; evaluator juga merupakan penilaian model dan tidak menjamin kebenaran mutlak. Penggunaan provider cloud dapat berbiaya dan membutuhkan pengelolaan kredensial. Retry menambah jumlah panggilan dan biaya.

## 3. Lingkungan dan konfigurasi

| Komponen | Versi/konfigurasi |
|---|---|
| Java | 21 |
| Spring Boot | 3.5.5 |
| Spring AI | 1.0.1 |
| Provider/model | OpenAI / gpt-4o-mini |
| Build | Gradle |
| API key | Environment variable `SPRING_AI_OPENAI_API_KEY` |

**[isi hasil praktikum: OS, versi aktual tool, kendala instalasi, dan penyesuaian versi]**

API key tidak disimpan di `application.properties`, source code, screenshot, atau Git.

### Verifikasi awal di workspace

Di lingkungan pengembangan, proyek berhasil dikompilasi dengan Java 21 dan Gradle Wrapper 8.14.3. `.\gradlew.bat test` berhasil: tes endpoint dan tes WireMock lulus. Dua tes evaluator live dilewati karena API key tidak tersedia. Profil `self-evaluating` berhasil memulai server saat diberi nilai key lokal nonvalid untuk pemeriksaan startup; tidak ada request AI live yang dikirim.

## 4. Implementasi Chapter 1

`AskController` menerima `POST /ask` berisi JSON `{"question":"..."}`. Controller meneruskan `Question` ke `BoardGameService`; `SpringAiBoardGameService` menyusun prompt dengan `ChatClient` dan membungkus teks hasil menjadi `Answer`.

**Request contoh**

```json
{"question":"Apa itu Spring AI?"}
```

**Hasil aktual:** [isi hasil response]

### Pengujian WireMock

WireMock menggantikan endpoint provider dengan response tetap sehingga dapat menguji pemrosesan jawaban tanpa API key dan tanpa ketidakpastian hasil model.

**Hasil `.\gradlew.bat test`:** [jalankan ulang dan catat hasil pada komputermu]

## 5. Implementasi Chapter 2

Tes live menggunakan `RelevancyEvaluator` untuk menilai hubungan jawaban terhadap pertanyaan, dan `FactCheckingEvaluator` untuk memeriksa fakta. Tes ini memanggil provider sungguhan dan dijalankan terpisah memakai `gradle liveAiTest`.

Service profil `self-evaluating` menilai relevansi jawaban saat runtime. Jawaban yang gagal memicu retry hingga tiga percobaan; jika tetap gagal, metode `@Recover` mengembalikan pesan pemulihan.

**Hasil tes relevansi:** [isi hasil aktual]
**Hasil tes factual accuracy:** [isi hasil aktual]
**Hasil runtime/recovery:** [isi hasil aktual atau jelaskan bagaimana hasil diuji]

## 6. Diagram dan alur

Lihat diagram class Mermaid pada `README.md`. Alur utama: client HTTP → `AskController` → `BoardGameService` → `ChatClient` → provider model → `Answer` sebagai JSON.

## 7. Bukti praktikum

Tambahkan screenshot buatan sendiri dengan keterangan:

1. Konfigurasi proyek dan dependencies.
2. Kode controller, service, record, dan diagram class.
3. Aplikasi berjalan dan request/response endpoint.
4. Hasil tes WireMock.
5. Hasil evaluasi live dan runtime evaluator (jika dijalankan).

**Lokasi/nama berkas bukti:** [isi]

## 8. Publikasi GitHub dan video demo

**URL repository:** [isi setelah dipublikasikan]
**Akses dosen:** [isi setelah undangan collaborator dikirim]
**URL video demo:** [isi setelah direkam dan diunggah]

Pastikan README menjelaskan cara menjalankan aplikasi dan tes. Tunjukkan urutan kerja dan hasil nyata di video, tetapi jangan tampilkan API key atau informasi akun.

## 9. Kesimpulan

**[isi ringkasan hasil, kendala, dan pemahaman yang diperoleh]**
