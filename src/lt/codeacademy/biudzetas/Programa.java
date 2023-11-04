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
            int input = Integer.parseInt(sc.nextLine());
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
                case 7 -> issaugotiDuomenisFaile(biudzetas);
                case 8 -> gautiDuomenisIsFailo(biudzetas);
                default -> System.out.println("Netinkamas pasirinkimas, iveskite skaičių nuo 0 iki 5");
            }
        }
    }

    private static void gautiDuomenisIsFailo(Biudzetas biudzetas) {
        for (var irasasList : biudzetas.readLines("sarasas-pajamos-ir-islaidos.csv")) {
            String[] atskirosEiluteDalys = irasasList.split(",");

            if (atskirosEiluteDalys.length >= 5) {
                int irasoID = Integer.parseInt(atskirosEiluteDalys[0]);
                int irasoSuma = Integer.parseInt(atskirosEiluteDalys[1]);
                String irasoKategorija = atskirosEiluteDalys[2];
                String irasoData = atskirosEiluteDalys[3];
                String irasoBudas = atskirosEiluteDalys[4];
                System.out.println(irasoID + ", " + irasoSuma + ", " + irasoKategorija + ", " + irasoData + ", " + irasoBudas);
            } else {
                System.out.println("pagavau paskutine tuscia eilute, reikia ispresti: " + irasasList);
            }
        }
    }

    private static void issaugotiDuomenisFaile(Biudzetas biudzetas) {
        biudzetas.writeToFile("sarasas-pajamos-ir-islaidos.csv");
        System.out.println("CSV failas sukurtas, jame rasite visus irašus.");
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
        int irasas = Integer.parseInt(sc.nextLine());
        biudzetas.irasoIstrynimas(irasas);
        System.out.println(biudzetas.gautiIrasoString());
    }

    private static void irasoKoregavimas(Biudzetas biudzetas, Scanner sc, DateTimeFormatter myFormatObj) {
        int input;
        System.out.println(biudzetas.gautiIrasoString());
        System.out.println("Pasirinkite irasa kuri norite koreguoti, iveskite iraso ID: ");
        int id = Integer.parseInt(sc.nextLine()) - 1;
        Irasas naujasIrasas = biudzetas.gautiIrasaPagalIDNumeri(id);
        koreguotiSuma(sc, naujasIrasas);
        koreguotiKategorija(sc, naujasIrasas);
        koreguotiData(sc, myFormatObj, naujasIrasas);
        biudzetas.atnaujintiIrasa(naujasIrasas, id);
    }

    private static void koreguotiKategorija(Scanner sc, Irasas naujasIrasas) {
        int input;
        System.out.println("kategorija:" + naujasIrasas.getKategorija());
        System.out.println("[1] - redaguoti, [2] - toliau");
        input = Integer.parseInt(sc.nextLine());
        if (input == 1) {
            boolean vestiKategorija = true;
            System.out.println("Iveskite naują kategoriją: ");
            String kategorija = sc.nextLine();
            while (vestiKategorija) { // vartotojas turi vesti kategorija tol kol ji teisinga
                if (naujasIrasas.setKategorija(kategorija)) { // tikrinu ar ivesta kategorija tokia egzistuoja
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
        input = Integer.parseInt(sc.nextLine());
        if (input == 1) {
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Iveskite naują sumą: ");
                try {
                    int naujaSuma = Integer.parseInt(sc.nextLine());
                    naujasIrasas.setSuma(naujaSuma);
                    validInput = true;
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Įvesta neleistina reikšmė. Prašome įvesti skaičių.");
                }
            }
        }
    }

    private static void koreguotiData(Scanner sc, DateTimeFormatter myFormatObj, Irasas naujasIrasas) {
        int input;
        System.out.println("Data: " + naujasIrasas.getData().format(myFormatObj));
        System.out.println("[1] - redaguoti, [2] - toliau");
        input = Integer.parseInt(sc.nextLine());
        if (input == 1) {
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Iveskite naują datą: YYYY-MM-DD HH:mm");
                try {
                    String data = sc.nextLine();
                    naujasIrasas.setData(data, myFormatObj);
                    validInput = true;
                } catch (DateTimeParseException | InputMismatchException e) {
                    System.out.println("Įvesta neleistina reikšmė. Prašome įvesti datą formatu: YYYY-MM-DD HH:mm.");
                }
            }
        }
    }

    private static void islaiduIvedimas(Meniu meniu, Scanner sc, Biudzetas biudzetas) {
        System.out.println(meniu.islaiduKategorijuMeniu());
        boolean noriuVestiIslaidas = true;
        while (noriuVestiIslaidas) {
            int sk = Integer.parseInt(sc.nextLine());
            try {
                IslaiduKategorija kategorija = switch (sk) {
                    case 1 -> IslaiduKategorija.PASKOLA;
                    case 2 -> IslaiduKategorija.LIZINGAS;
                    case 3 -> IslaiduKategorija.KELIONE;
                    case 4 -> IslaiduKategorija.PIRKINIAI;
                    default -> throw new RuntimeException();
                };
                System.out.println("iveskite kategorijos " + kategorija + " suma");
                int suma = Integer.parseInt(sc.nextLine());
                biudzetas.pridetiIrasa(new IslaiduIrasas(suma, LocalDateTime.now(), kategorija, "papildoma info"));
            } catch (InputMismatchException e) {
                System.out.println("Įvesta neleistina reikšmė. Prašome įvesti skaičių.");
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
            int sk = Integer.parseInt(sc.nextLine());
            try {
                PajamuKategorija kategorija = switch (sk) {
                    case 1 -> PajamuKategorija.ALGA;
                    case 2 -> PajamuKategorija.INVESTICIJOS;
                    case 3 -> PajamuKategorija.STIPENDIJA;
                    default -> throw new RuntimeException();
                };
                System.out.println("iveskite kategorijos " + kategorija + " suma");
                int suma = Integer.parseInt(sc.nextLine());
                biudzetas.pridetiIrasa(new PajamuIrasas(suma, LocalDateTime.now(), kategorija, "papildoma info"));
            } catch (InputMismatchException e) {
                System.out.println("Įvesta neleistina reikšmė. Prašome įvesti skaičių.");
            } catch (RuntimeException e) {
                noriuVestiPajamas = false;
            }
        }
    }
}
