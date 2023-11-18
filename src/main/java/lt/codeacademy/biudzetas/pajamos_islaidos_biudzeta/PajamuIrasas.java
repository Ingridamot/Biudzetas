package lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta;

import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.IslaiduIrPajamuBudas;
import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.PajamuIslaiduEnumas;
import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.PajamuKategorija;

import java.time.LocalDateTime;

public class PajamuIrasas extends Irasas{

    private PajamuKategorija kategorija = PajamuKategorija.ALGA;

    public PajamuIrasas(double suma, LocalDateTime data, PajamuKategorija kategorija, String papildomaInfo) {
        super(suma, data, papildomaInfo, PajamuIslaiduEnumas.PAJAMOS, IslaiduIrPajamuBudas.MASTERCARD);
        this.kategorija = kategorija;
        if (kategorija.equals(PajamuKategorija.ALGA)) {
            this.budas = IslaiduIrPajamuBudas.I_SASKAITA;
        } else if (kategorija.equals(PajamuKategorija.STIPENDIJA)){
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
                "pajamos ID: %s, Suma €%s, kategorija %s, data: %s, pajamų gavimo būdas %s%n",
                getId(), getSuma(), getKategorija(), getData().format(myFormatObj),getBudas());
    }

    @Override
    public boolean setKategorija(String tipas) {
        tipas = tipas.toUpperCase();
        switch (tipas) {
            case "ALGA" -> kategorija = PajamuKategorija.ALGA;
            case "STIPENDIJA" -> kategorija = PajamuKategorija.STIPENDIJA;
            case "INVESTICIJOS" -> kategorija = PajamuKategorija.INVESTICIJOS;
            default -> {
                return false;
            }
        }
        return true;
    }
}
