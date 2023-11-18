package lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.IslaiduIrPajamuBudas;
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
    protected IslaiduIrPajamuBudas budas; // kad vaikas galetu ji pasiekt (kai private gali pasiekti tik tevas)


    private static int nextId = 1;
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
@JsonCreator
    public Irasas( @JsonProperty("suma") double suma,
                   @JsonProperty("data") LocalDateTime data,
                   @JsonProperty("tipas") PajamuIslaiduEnumas tipas,
                   @JsonProperty("budas") IslaiduIrPajamuBudas budas) {
        this.id = nextId;
        nextId++;
        this.suma = suma;
        this.data = data;
        this.papildomaInfo = papildomaInfo;
        this.tipas = tipas;
        this.budas =budas;
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

    public String getBudas() {
        return budas.name();
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
