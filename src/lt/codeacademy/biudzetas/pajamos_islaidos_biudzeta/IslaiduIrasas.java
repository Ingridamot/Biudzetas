package lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta;

import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.*;

import java.time.LocalDateTime;

public class IslaiduIrasas extends Irasas{

    private IslaiduKategorija kategorija = IslaiduKategorija.PASKOLA;
    private IslaiduAtsiskaitymoBudas budas;


    public IslaiduIrasas(double suma, LocalDateTime data, IslaiduKategorija kategorija, String papildomaInfo) {
        super(suma, data, papildomaInfo);
        setTipas(PajamuIslaiduEnumas.ISLAIDOS);
        this.kategorija = kategorija;
        this.budas = IslaiduAtsiskaitymoBudas.MASTERCARD;
    }

    public IslaiduAtsiskaitymoBudas getBudas() {
        if (kategorija.equals(IslaiduKategorija.PASKOLA)) {
            return IslaiduAtsiskaitymoBudas.MASTERCARD;
        } else if (kategorija.equals(IslaiduKategorija.LIZINGAS)) {
            return IslaiduAtsiskaitymoBudas.MASTERCARD;
        } else {
            return IslaiduAtsiskaitymoBudas.GRYNIEJI;
        }
    }

    @Override
    public String getTipas(){
        return kategorija.name();
    }

    @Override
    public String toString(){
        return String.format(
                "islaidos ID: %s, Suma €%s, kategorija %s, data: %s, išlaidos būdas %s%n ",
                 getId(), getSuma(), getTipas(), getData().format(myFormatObj), getBudas());
    }
    @Override
    public boolean setTipas(String tipas) {
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
