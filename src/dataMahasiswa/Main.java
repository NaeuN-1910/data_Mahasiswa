package dataMahasiswa;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.nio.file.Files;

public class Main {
    public static void main (String[] args)throws IOException {
        Scanner input = new Scanner(System.in);
        String pilih;
        boolean ulang = true;

        while (ulang) {
            System.out.println("\t\t\tData Mahasiswa");
            System.out.println("***************************************");
            System.out.println("| 1.\tLihat Seluruh Data Mahasiswa  |");
            System.out.println("| 2.\tCari Data Mahasiswa           |");
            System.out.println("| 3.\tTambah Data Mahasiswa         |");
            System.out.println("| 4.\tHapus data Mahasiswa          |");
            System.out.println("***************************************");

            System.out.print("\nSilahkan Pilih : ");
            pilih = input.next();

            switch (pilih) {
                case "1":
                    System.out.println("\n ========================");
                    System.out.println("| Data SELURUH Mahasiswa |");
                    System.out.println(" ========================");
                    lihatData();
                    break;
                case "2":
                    System.out.println("\n ========================");
                    System.out.println("| Mencari Data Mahasiswa |");
                    System.out.println(" ========================\n");
                    cariData();
                    break;
                case "3":
                    System.out.println("\n =========================");
                    System.out.println("| Menambah Data Mahasiswa |");
                    System.out.println(" =========================\n");
                    tambahData();
                    break;
                case "4":
                    System.out.println("\n ==================");
                    System.out.println("Hapus Data Mahasiswa");
                    System.out.println(" ==================\n");
                    hapusData();
                    break;
                default:
                    System.err.println("Input yang anda masukkan tidak ditemukan");
            }

            ulang = pilihYaatauTidak("Apakah Anda Ingin Melanjutkan");
        }
    }

    private static void lihatData()throws IOException{
        FileReader bacaDatabase;
        BufferedReader bacaFile;
        try {
            bacaDatabase = new FileReader("database.txt");
            bacaFile = new BufferedReader(bacaDatabase);
        }catch (Exception e){
            System.out.println("\nData Tidak ditemukan di Database !!!");
            System.out.print("Silahkan Tambahkan Data Terlebih Dahulu\n");
            tambahData();
            return;
        }
        System.out.println("**************************************************************************************");
        System.out.println("|No. | NPM             |\tNama                          |\tJurusan                  |");
        System.out.println("**************************************************************************************");

        String data = bacaFile.readLine();
        int nomorData=0;
        while (data != null) {
            nomorData++;

            StringTokenizer stringtoken = new StringTokenizer(data, ",");

            System.out.printf("|%2d  ",nomorData);
            System.out.printf("| %s      ", stringtoken.nextToken());
            System.out.printf("| \t%-29s ", stringtoken.nextToken());
            System.out.printf("| \t%-20s |", stringtoken.nextToken());
            System.out.print("\n");

            data = bacaFile.readLine();
        }
        System.out.println("**************************************************************************************");
    }

    private static void cariData()throws IOException{
        try {
            File lihatDatabase = new File("database.txt");
        }catch (Exception e){
            System.err.print("\nTidak Ada Data di Database !!!\n");
            System.err.println("\t Silahkan Tambahkan Data Terlebih Dahulu ");
            tambahData();
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.print("Masukan Identitas yang akan di cari : ");
        String cari = input.nextLine();

        String[] keyword = cari.split("\\s+");

        cekdata(keyword,true);
    }

    private static void tambahData()throws IOException {
        FileWriter tulisDatabase = new FileWriter("database.txt", true);
        BufferedWriter tulisFile = new BufferedWriter(tulisDatabase);
        Scanner masuk = new Scanner(System.in);

        String npm, nama, jurusan;

        System.out.print("NPM     : ");
        npm = masuk.nextLine();
        System.out.print("Nama    : ");
        nama = masuk.nextLine();
        System.out.print("Jurusan : ");
        jurusan = masuk.nextLine();

        String[] keyword ={npm+","+nama+","+jurusan};
        boolean ketemu = cekdata(keyword,false);
        if (!ketemu){
            System.out.println("Data Yang Akan Anda Input");
            System.out.println("=========================");
            System.out.println("NPM     : "+npm);
            System.out.println("Nama    : "+nama);
            System.out.println("Jurusan : "+jurusan);

            boolean tambah = pilihYaatauTidak("Apakah Anda Ingin Menambah Data Tersebut ? ");
            if (!ketemu && tambah){
                tulisFile.write(npm+","+nama+","+jurusan);
                tulisFile.newLine();
                tulisFile.flush();

                System.out.println("Anda Berhasil Menambahkan Data Baru ");
            } else {
                System.out.println("\nAnda Belum Menambahkan Data Tersebut ");
                System.out.println("Data Tersimpan Masih Yang Lama \n");
            }
        }else {
            System.out.println("Data Yang Anda Masukan Sudah Tersedia dengan Data Berikut ");
            cekdata(keyword,true);
        }
        tulisFile.close();
    }

    private static void hapusData()throws IOException{
        File database = new File("database.txt");
        FileReader bacaDatabase = new FileReader(database);
        BufferedReader bacaFile = new BufferedReader(bacaDatabase);

        File updateDatabase = new File("updateFile.txt");
        FileWriter tulisDatabase = new FileWriter(updateDatabase);
        BufferedWriter tulisFile = new BufferedWriter(tulisDatabase);

        System.out.println("Berikut Data Mahasiswa yang ada di Database");
        lihatData();

        Scanner input = new Scanner(System.in);
        System.out.print("Masukan No. Data yang akan di Hapus : ");
        int hapusData = input.nextInt();

        int hitungPerbaris = 0;
        int nomorData = 0;
        String data = bacaFile.readLine();

        while (data !=null){
            hitungPerbaris++;
            nomorData++;
            boolean hapus = false;

            StringTokenizer stringtoken = new StringTokenizer(data,",");
            if (hapusData == hitungPerbaris){
                System.out.println("______________________________________________________________________________________");
                System.out.println("\t\t\t\t\t\t\tData Yang Akan Anda Hapus");
                System.out.println("______________________________________________________________________________________");
                System.out.printf("|%2d  ",nomorData);
                System.out.printf("| %s      ", stringtoken.nextToken());
                System.out.printf("| \t%-29s ", stringtoken.nextToken());
                System.out.printf("| \t%-20s |", stringtoken.nextToken());
                System.out.println("\n______________________________________________________________________________________");
                hapus = pilihYaatauTidak("Apakah Anda Ingin Mengahapus Data Tersebut ?");
            }
            if (hapus){
                System.out.println("Data Berhasil Dihapus");
            }else {
                tulisFile.write(data);
                tulisFile.newLine();
            }
            data = bacaFile.readLine();
        }
        tulisFile.flush();

        tulisFile.close();
        tulisDatabase.close();
        bacaDatabase.close();
        bacaFile.close();
        System.gc();

        database.delete();
        updateDatabase.renameTo(database);
    }

    private static boolean cekdata(String[] keyword,boolean tampil)throws IOException {

        FileReader bacaDatabase = new FileReader("database.txt");
        BufferedReader bacaFile = new BufferedReader(bacaDatabase);

        String data = bacaFile.readLine();
        int nomorData = 0;
        boolean ketemu = false;

        if (tampil) {
            System.out.println("**************************************************************************************");
            System.out.println("|No. | NPM             |\tNama                          |\tJurusan                  |");
            System.out.println("**************************************************************************************");
        }
        while (data != null) {
            nomorData++;
            ketemu = true;
            for (String kataKunci : keyword) {
                ketemu = ketemu && data.toLowerCase().contains(kataKunci.toLowerCase());
            }
            if (ketemu) {
                if (tampil) {
                    StringTokenizer stringtoken = new StringTokenizer(data, ",");

                    System.out.printf("|%2d  ",nomorData);
                    System.out.printf("| %s      ", stringtoken.nextToken());
                    System.out.printf("| \t%-29s ", stringtoken.nextToken());
                    System.out.printf("| \t%-20s |", stringtoken.nextToken());
                    System.out.print("\n");
                } else {
                    break;
                }
            }
            data = bacaFile.readLine();
        }
        if (tampil) {
            System.out.println("**************************************************************************************");
        }
        return ketemu;
    }

    private static boolean pilihYaatauTidak(String Pesan)throws IOException{
        Scanner input = new Scanner(System.in);
        System.out.print("\n"+Pesan+"(y/t)");
        String pilih = input.next();

        while (!pilih.equalsIgnoreCase("y") && !pilih.equalsIgnoreCase("t")){
            System.err.println("Silahkan Pilih y / t");
            System.out.print("\n"+Pesan+"(y/t) ");
            pilih = input.next();
        }
        return pilih.equalsIgnoreCase("y");
    }
}
