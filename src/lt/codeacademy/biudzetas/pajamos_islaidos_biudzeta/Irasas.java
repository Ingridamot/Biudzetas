package lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta;

import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.PajamuIslaiduEnumas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Irasas {

    int id;
    private double suma;
    private LocalDateTime data;
    private String papildomaInfo;
    private PajamuIslaiduEnumas tipas;
    private static int nextId = 1;
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Irasas( double suma, LocalDateTime data, String papildomaInfo) {
        this.id = nextId;
        nextId++;
        this.suma = suma;
        this.data = data;
        this.papildomaInfo = papildomaInfo;
    }

    public int getId() {
        return id;
    }

    public double getSuma() {
        return suma;
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

    public String getTipas() {
        return tipas.name();
    }

    public void setTipas(PajamuIslaiduEnumas tipas) {
        this.tipas = tipas;
    }

    public boolean setTipas(String tipas){
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return getId() == ((Irasas)obj).getId();
    }

}
