package lt.codeacademy.biudzetas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.*;
import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.IslaiduIrPajamuBudas;
import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.PajamuIslaiduEnumas;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Programa {

    public static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


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
                case 9 -> sukurtiJsonFile(biudzetas);
                default -> System.out.println("Netinkamas pasirinkimas, iveskite skaičių nuo 0 iki 5");
            }
        }
    }

    private static void sukurtiJsonFile(Biudzetas biudzetas) {
        List<PajamuIrasas> pajamos = new ArrayList<>();
        List<IslaiduIrasas> islaidos = new ArrayList<>();

        pajamos.addAll(biudzetas.gautiPajamuIrasus()); // Gauti pajamų ir išlaidų sąrašus iš biudžeto objekto
        islaidos.addAll(biudzetas.gautiIslaiduIrasus());

        List<Irasas> irasas = new ArrayList<>();
        irasas.addAll(pajamos);
        irasas.addAll(islaidos);

        File irasasJsonFile = new File("biudzetas.json");

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule()); //

        try {
            objectMapper.writeValue(irasasJsonFile, irasas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void gautiDuomenisIsFailo(Biudzetas biudzetas) {
        List<String> lines = biudzetas.readLines("sarasas-pajamos-ir-islaidos.csv");
        lines.forEach(line -> {
            String[] parts = line.split(",");
            String[] zodziai = line.split(" ");
            double suma = Double.parseDouble(parts[0].split(":")[1].trim());
            PajamuIslaiduEnumas kategorija = PajamuIslaiduEnumas.valueOf(parts[1].split(":")[1].trim());
            LocalDateTime date = LocalDateTime.parse(parts[2].split(":")[1].trim() + ":" + parts[2].split(":")[2].trim(), myFormatObj);
            IslaiduIrPajamuBudas budas = IslaiduIrPajamuBudas.valueOf(parts[3].split(":")[1].trim());
            int id= 0;
            if(zodziai[0].equals("Išlaidų")){
                biudzetas.pridetiIrasa(new IslaiduIrasas(id, suma, date, kategorija, budas));
            }else {
                biudzetas.pridetiIrasa(new PajamuIrasas(id, suma, date, kategorija, budas));
            }
        });
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
                PajamuIslaiduEnumas kategorija = switch (sk) {
                    case 1 -> PajamuIslaiduEnumas.PASKOLA;
                    case 2 -> PajamuIslaiduEnumas.LIZINGAS;
                    case 3 -> PajamuIslaiduEnumas.KELIONE;
                    case 4 -> PajamuIslaiduEnumas.PIRKINIAI;
                    default -> throw new RuntimeException();
                };
                System.out.println("iveskite kategorijos " + kategorija + " suma");
                int suma = Integer.parseInt(sc.nextLine());
                IslaiduIrPajamuBudas budas = IslaiduIrPajamuBudas.GRYNIEJI;
                int id = 0;
                biudzetas.pridetiIrasa(new IslaiduIrasas(id, suma, LocalDateTime.now(), kategorija, budas));
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
                PajamuIslaiduEnumas kategorija = switch (sk) {
                    case 1 -> PajamuIslaiduEnumas.ALGA;
                    case 2 -> PajamuIslaiduEnumas.INVESTICIJOS;
                    case 3 -> PajamuIslaiduEnumas.STIPENDIJA;
                    default -> throw new RuntimeException();
                };
                System.out.println("iveskite kategorijos " + kategorija + " suma");
                int suma = Integer.parseInt(sc.nextLine());
                IslaiduIrPajamuBudas budas =IslaiduIrPajamuBudas.I_SASKAITA;
                int id =0;
                biudzetas.pridetiIrasa(new PajamuIrasas(id, suma, LocalDateTime.now(), kategorija, budas));
            } catch (InputMismatchException e) {
                System.out.println("Įvesta neleistina reikšmė. Prašome įvesti skaičių.");
            } catch (RuntimeException e) {
                noriuVestiPajamas = false;
            }
        }
    }
}
