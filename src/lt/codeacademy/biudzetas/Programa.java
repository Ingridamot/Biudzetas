package lt.codeacademy.biudzetas;

import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.*;
import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.IslaiduKategorija;
import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.PajamuKategorija;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
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
                case 1 -> pajamuIvedimas(meniu, sc, biudzetas);
                case 2 -> islaiduIvedimas(meniu, sc, biudzetas);
                case 3 -> irasoKoregavimas(biudzetas, sc, myFormatObj);
                case 4 -> irasoIstrynimas(biudzetas, sc);
                case 5 -> spausdintiIrasus(biudzetas);
                case 6 -> spausdintiBalansa(biudzetas);
                default -> System.out.println("Netinkamas pasirinkimas, iveskite skaičių nuo 0 iki 5");
            }
        }
    }

    private static void spausdintiBalansa(Biudzetas biudzetas) {
        System.out.printf(
                "Jūsų šio mėnesio bendros pajamos: %s, išlaidos: %s, balansas: %s%n",
                biudzetas.getBendrosMenesioPajamos(),
                biudzetas.getBendrosMenesioIslaidos(),
                biudzetas.getBalansas()
        );
    }

    private static void spausdintiIrasus(Biudzetas biudzetas) {
        System.out.println("Visos irasai: ");
        System.out.println(biudzetas.gautiIrasoString());
    }

    private static void irasoIstrynimas(Biudzetas biudzetas, Scanner sc) {
        System.out.println(biudzetas.gautiIrasoString());
        System.out.println("Pasirinkite ID įrašo, kurį norite pašalinti");
        int irasas = sc.nextInt();
        biudzetas.irasoIstrynimas(irasas);
    }

    private static void irasoKoregavimas(Biudzetas biudzetas, Scanner sc, DateTimeFormatter myFormatObj) {
        int input;
        System.out.println(biudzetas.gautiIrasoString());
        System.out.println("Pasirinkite irasa kuri norite koreguoti, iveskite iraso ID: ");
        int id = sc.nextInt() - 1;
        Irasas naujasIrasas = biudzetas.gautiIrasaPagalIDNumeri(id);
        koreguotiSuma(sc, naujasIrasas);
        koreguotiKategorija(sc, naujasIrasas);
        koreguotiData(sc, myFormatObj, naujasIrasas);
        biudzetas.atnaujintiIrasa(naujasIrasas, id);
    }

    private static void koreguotiKategorija(Scanner sc, Irasas naujasIrasas) {
        int input;
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
                    vestiKategorija = false;
                } else {
                    System.out.println("Ivestas tipas neteisingas, veskite dar kartą");
                    kategorija = sc.nextLine();
                }
            }
        }
    }

    private static void koreguotiSuma(Scanner sc, Irasas naujasIrasas) {
        int input;
        System.out.println("Suma:" + naujasIrasas.getSuma());
        System.out.println("[1] - redaguoti, [2] - toliau");
        input = sc.nextInt();
        if (input == 1) {
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Iveskite naują sumą: ");
                try {
                    int naujaSuma = sc.nextInt();
                    naujasIrasas.setSuma(naujaSuma);
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Įvesta neleistina reikšmė. Prašome įvesti skaičių.");
                    sc.next(); // Išvalau neteisingą įvestį
                }
            }
        }
    }

    private static void koreguotiData(Scanner sc, DateTimeFormatter myFormatObj, Irasas naujasIrasas) {
        int input;
        System.out.println("Data: " + naujasIrasas.getData().format(myFormatObj));
        System.out.println("[1] - redaguoti, [2] - toliau");
        input = sc.nextInt();
        if (input == 1) {
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Iveskite naują datą: YYYY-MM-DD HH:mm");
                try {
                    String data = sc.nextLine();
                    data = sc.nextLine();
                    naujasIrasas.setData(data, myFormatObj);
                    validInput = true;
                } catch (DateTimeParseException | InputMismatchException e) {
                    System.out.println("Įvesta neleistina reikšmė. Prašome įvesti datą formatu: YYYY-MM-DD HH:mm.");
                    sc.next(); // Išvalau neteisingą įvestį
                }
            }
        }
    }

    private static void islaiduIvedimas(Meniu meniu, Scanner sc, Biudzetas biudzetas) {
        System.out.println(meniu.islaiduKategorijuMeniu());
        boolean noriuVestiIslaidas = true;
        while (noriuVestiIslaidas) {
            int sk = sc.nextInt();
            try {
                IslaiduKategorija kategorija = switch (sk) {
                    case 1 -> IslaiduKategorija.PASKOLA;
                    case 2 -> IslaiduKategorija.LIZINGAS;
                    case 3 -> IslaiduKategorija.KELIONE;
                    case 4 -> IslaiduKategorija.PIRKINIAI;
                    default -> throw new RuntimeException();
                };
                System.out.println("iveskite kategorijos " + kategorija + " suma");
                int suma = sc.nextInt();
                biudzetas.pridetiIrasa(new IslaiduIrasas(suma, LocalDateTime.now(), kategorija, "papildoma info"));
            } catch (InputMismatchException e) {
                System.out.println("Įvesta neleistina reikšmė. Prašome įvesti skaičių.");
                sc.next(); // Išvalau neteisingą įvestį
            } catch (RuntimeException e) {
                System.out.println("Įvesta neleistina reikšmė. Prašome įvesti skaičių.");
                noriuVestiIslaidas = false;
            }
        }
    }

    private static void pajamuIvedimas(Meniu meniu, Scanner sc, Biudzetas biudzetas) {
        System.out.println(meniu.pajamuKategorijuMeniu());
        boolean noriuVestiPajamas = true;
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
            } catch (InputMismatchException e) {
                System.out.println("Įvesta neleistina reikšmė. Prašome įvesti skaičių.");
                sc.next(); // Išvalau neteisingą įvestį
            } catch (RuntimeException e) {
                noriuVestiPajamas = false;
            }
        }
    }
}
