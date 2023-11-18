package lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta;

import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.IslaiduIrPajamuBudas;
import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.PajamuIslaiduEnumas;

import java.time.LocalDateTime;

public class PajamuIrasas extends Irasas{

    private PajamuIslaiduEnumas kategorija = PajamuIslaiduEnumas.ALGA;

    public PajamuIrasas(int id, double suma, LocalDateTime data, PajamuIslaiduEnumas kategorija, IslaiduIrPajamuBudas budas) {
        super(suma, data, PajamuIslaiduEnumas.PAJAMOS, IslaiduIrPajamuBudas.MASTERCARD);
        this.kategorija = kategorija;
        if (kategorija.equals(PajamuIslaiduEnumas.ALGA)) {
            this.budas = IslaiduIrPajamuBudas.I_SASKAITA;
        } else if (kategorija.equals(PajamuIslaiduEnumas.STIPENDIJA)){
            this.budas = IslaiduIrPajamuBudas.I_SASKAITA;
        } else {
            this.budas = IslaiduIrPajamuBudas.GRYNIEJI;
        }
    }


    @Override
    public String getKategorija() {
            return kategorija.name();
    }

    @Override
    public String toString(){
        return String.format(
                "Pajamos ID: %s, Pajamų Suma €: %s, kategorija: %s, data: %s,Pajamos gavimo būdas: %s%n",
                getId(), getSuma(), getKategorija(), getData().format(myFormatObj),getBudas());
    }

    @Override
    public boolean setKategorija(String tipas) {
        tipas = tipas.toUpperCase();
        switch (tipas) {
            case "ALGA" -> kategorija = PajamuIslaiduEnumas.ALGA;
            case "STIPENDIJA" -> kategorija = PajamuIslaiduEnumas.STIPENDIJA;
            case "INVESTICIJOS" -> kategorija = PajamuIslaiduEnumas.INVESTICIJOS;
            default -> {
                return false;
            }
        }
        return true;
    }
}
