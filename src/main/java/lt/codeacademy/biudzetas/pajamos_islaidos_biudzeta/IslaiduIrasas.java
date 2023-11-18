package lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta;

import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.*;

import java.time.LocalDateTime;

public class IslaiduIrasas extends Irasas{

    private PajamuIslaiduEnumas kategorija = PajamuIslaiduEnumas.PASKOLA;


    public IslaiduIrasas(int id, double suma, LocalDateTime data, PajamuIslaiduEnumas kategorija, IslaiduIrPajamuBudas budas) {
        super(suma, data,PajamuIslaiduEnumas.ISLAIDOS, IslaiduIrPajamuBudas.MASTERCARD);
        this.kategorija = kategorija;
        if (kategorija.equals(PajamuIslaiduEnumas.PASKOLA)) {
            this.budas = IslaiduIrPajamuBudas.MASTERCARD;
        } else if (kategorija.equals(PajamuIslaiduEnumas.LIZINGAS)) {
            this.budas = IslaiduIrPajamuBudas.MASTERCARD;
        } else {
            this.budas = IslaiduIrPajamuBudas.GRYNIEJI;
        }
    }


    @Override
    public String getKategorija(){
        return kategorija.name();
    }

    @Override
    public String toString(){
        return String.format(
                "Išlaidos ID: %s, Išlaidų Suma €: %s, kategorija: %s, data: %s, Išlaidos būdas: %s%n ",
                 getId(), getSuma(), getKategorija(), getData().format(myFormatObj), getBudas());
    }
    @Override
    public boolean setKategorija(String tipas) {
        tipas = tipas.toUpperCase();
        switch (tipas) {
            case "PASKOLA" -> kategorija = PajamuIslaiduEnumas.PASKOLA;
            case "LIZINGAS" -> kategorija = PajamuIslaiduEnumas.LIZINGAS;
            case "KELIONE" -> kategorija = PajamuIslaiduEnumas.KELIONE;
            case "PIRKINIAI" -> kategorija = PajamuIslaiduEnumas.PIRKINIAI;
            default -> {
                return false;
            }
        }
        return true;
    }
}
