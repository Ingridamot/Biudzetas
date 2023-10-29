package lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta;

import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.PajamuGavimoBudas;
import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.PajamuIslaiduEnumas;
import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.PajamuKategorija;

import java.time.LocalDateTime;

public class PajamuIrasas extends Irasas{

    private PajamuKategorija kategorija = PajamuKategorija.ALGA;
    private PajamuGavimoBudas budas;

    public PajamuIrasas(double suma, LocalDateTime data, PajamuKategorija kategorija, String papildomaInfo) {
        super(suma, data, papildomaInfo, PajamuIslaiduEnumas.PAJAMOS);
        this.kategorija = kategorija;
        this.budas = PajamuGavimoBudas.I_SASKAITA;
    }

    public PajamuGavimoBudas getBudas() {
        if (kategorija.equals(PajamuKategorija.ALGA)) {
            return PajamuGavimoBudas.I_SASKAITA;
        } else if (kategorija.equals(PajamuKategorija.STIPENDIJA)){
            return PajamuGavimoBudas.I_SASKAITA;
        } else {
            return PajamuGavimoBudas.GRYNIEJI;
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
