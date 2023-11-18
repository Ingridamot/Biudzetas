package lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta;

import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.*;

import java.time.LocalDateTime;

public class IslaiduIrasas extends Irasas{

    private IslaiduKategorija kategorija = IslaiduKategorija.PASKOLA;
    ;


    public IslaiduIrasas(double suma, LocalDateTime data, IslaiduKategorija kategorija, String papildomaInfo) {
        super(suma, data, papildomaInfo,PajamuIslaiduEnumas.ISLAIDOS, IslaiduIrPajamuBudas.MASTERCARD);
        this.kategorija = kategorija;
        if (kategorija.equals(IslaiduKategorija.PASKOLA)) {
            this.budas = IslaiduIrPajamuBudas.MASTERCARD;
        } else if (kategorija.equals(IslaiduKategorija.LIZINGAS)) {
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
                "islaidos ID: %s, Suma €%s, kategorija %s, data: %s, išlaidos būdas %s%n ",
                 getId(), getSuma(), getKategorija(), getData().format(myFormatObj), getBudas());
    }
    @Override
    public boolean setKategorija(String tipas) {
        tipas = tipas.toUpperCase();
        switch (tipas) {
            case "PASKOLA" -> kategorija = IslaiduKategorija.PASKOLA;
            case "LIZINGAS" -> kategorija = IslaiduKategorija.LIZINGAS;
            case "KELIONE" -> kategorija = IslaiduKategorija.KELIONE;
            case "PIRKINIAI" -> kategorija = IslaiduKategorija.PIRKINIAI;
            default -> {
                return false;
            }
        }
        return true;
    }
}
