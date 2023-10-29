package lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta;

import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.PajamuIslaiduEnumas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Irasas {

    int id;
    private double suma;
    private LocalDateTime data;
    private String papildomaInfo;
    private PajamuIslaiduEnumas kategorija;
    private PajamuIslaiduEnumas tipas;


    private static int nextId = 1;
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Irasas( double suma, LocalDateTime data, String papildomaInfo, PajamuIslaiduEnumas tipas) {
        this.id = nextId;
        nextId++;
        this.suma = suma;
        this.data = data;
        this.papildomaInfo = papildomaInfo;
        this.tipas = tipas;
    }

    public int getId() {
        return id;
    }

    public double getSuma() {
        return suma;
    }

    public PajamuIslaiduEnumas getTipas() {
        return tipas;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public void setData(String data, DateTimeFormatter formatas) {
        this.data = LocalDateTime.parse(data, formatas);
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getPapildomaInfo() {
        return papildomaInfo;
    }

    public String getKategorija() {
        return kategorija.name();
    }

    public void setKategorija(PajamuIslaiduEnumas kategorija) {
        this.kategorija = kategorija;
    }

    public boolean setKategorija(String kategorija){
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return getId() == ((Irasas)obj).getId();
    }

}
