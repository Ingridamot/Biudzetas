package lt.codeacademy.biudzetas;

import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.*;
import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.IslaiduKategorija;
import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.PajamuKategorija;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Programa {

    public static void main(String[] args) {

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Scanner sc = new Scanner(System.in);
        Biudzetas biudzetas = new Biudzetas();
        Meniu meniu = new Meniu();

        while (true) {
            System.out.println(meniu.pagrindinisMeniu());
            int input = sc.nextInt();
            if (input == 0) {
                break;
            }

            switch (input) {
                case 1 -> {
                    System.out.println(meniu.pajamuKategorijuMeniu());
                    boolean noriuVestiPajamas =true;
                    while (noriuVestiPajamas) {
                        int sk = sc.nextInt();
                        try {
                            PajamuKategorija kategorija = switch (sk) {
                                case 1 -> PajamuKategorija.ALGA;
                                case 2 -> PajamuKategorija.INVESTICIJOS;
                                case 3 -> PajamuKategorija.STIPENDIJA;
                                default -> throw new RuntimeException();
                             };
                            System.out.println("iveskite kategorijos " + kategorija + " suma");
                            int suma = sc.nextInt();
                            biudzetas.pridetiIrasa(new PajamuIrasas(suma, LocalDateTime.now(), kategorija, "papildoma info"));
                        } catch(RuntimeException e) {
                            noriuVestiPajamas = false;
                        }
                    }
                }

                case 2 -> {
                    System.out.println(meniu.islaiduKategorijuMeniu());
                    boolean noriuVestiIslaidas =true;
                    while (noriuVestiIslaidas) {
                        int sk = sc.nextInt();
                        try {
                            IslaiduKategorija kategorija = switch (sk) {
                                case 1 -> IslaiduKategorija.PASKOLA;
                                case 2 -> IslaiduKategorija.LIZINGAS;
                                case 3 -> IslaiduKategorija.KELIONE;
                                case 4 -> IslaiduKategorija.PIRKINIAI;
                                default ->  throw new RuntimeException();
                            };
                            System.out.println("iveskite kategorijos " + kategorija + " suma");
                            int suma = sc.nextInt();
                            biudzetas.pridetiIrasa(new IslaiduIrasas(suma, LocalDateTime.now(), kategorija, "papildoma info"));
                        } catch (RuntimeException e) {
                            noriuVestiIslaidas = false;
                        }
                    }
                }

                case 3 -> {
                    System.out.println(biudzetas.gautiIrasoString());
                    System.out.println("Pasirinkite irasa kuri norite koreguoti, iveskite iraso ID: ");
                    int id = sc.nextInt()-1;
                    Irasas naujasIrasas = biudzetas.gautiIrasaPagalIDNumeri(id);
                    System.out.println("Suma:" + naujasIrasas.getSuma());
                    System.out.println("[1] - redaguoti, [2] - toliau");
                    input = sc.nextInt();
                    if (input == 1) {
                        System.out.println("Iveskite naują sumą: ");
                        naujasIrasas.setSuma(sc.nextInt());
                    }
                    System.out.println("kategorija:" + naujasIrasas.getTipas());
                    System.out.println("[1] - redaguoti, [2] - toliau");
                    input = sc.nextInt();
                    if (input == 1) {
                        boolean vestiKategorija = true;
                        System.out.println("Iveskite naują kategoriją: ");
                        String kategorija = sc.nextLine();
                        kategorija = sc.nextLine();
                        while (vestiKategorija) { // vartotojas turi vesti kategorija tol kol ji teisinga
                            if (naujasIrasas.setTipas(kategorija)) { // tikrinu ar ivesta kategorija tokia egzistuoja
                                vestiKategorija =false;
                            } else {
                                System.out.println("Ivestas tipas neteisingas, veskite dar kartą");
                                kategorija = sc.nextLine();
                            }
                        }
                    }
                    System.out.println("Data: " + naujasIrasas.getData().format(myFormatObj));
                    System.out.println("[1] - redaguoti, [2] - toliau");
                    input = sc.nextInt();
                    if (input == 1) {
                        System.out.println("Iveskite naują datą: YYYY-MM-DD HH:mm");
                        String data = sc.nextLine();
                        data = sc.nextLine();
                        naujasIrasas.setData(data, myFormatObj);
                    }
                    biudzetas.atnaujintiIrasa(naujasIrasas,id);
                }
                case 4 -> {
                    System.out.println(biudzetas.gautiIrasoString());
                    System.out.println("Pasirinkite ID įrašo, kurį norite pašalinti");
                    int irasas = sc.nextInt();
                    biudzetas.irasoIstrynimas(irasas);
                }
                case 5 -> {
                    System.out.println("Visos irasai: ");
                    System.out.println(biudzetas.gautiIrasoString());
                }

                case 6 -> System.out.printf(
                        "Jūsų šio mėnesio bendros pajamos: %s, išlaidos: %s, balansas: %s%n",
                        biudzetas.getBendrosMenesioPajamos(),
                        biudzetas.getBendrosMenesioIslaidos(),
                        biudzetas.getBalansas()
                );
                default -> System.out.println("Netinkamas pasirinkimas, iveskite skaičių nuo 0 iki 5");
            }
        }
    }
}
