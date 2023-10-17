package lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Biudzetas {
    private List<Irasas> irasasList;
    private ArrayList<PajamuIrasas> pajamos = new ArrayList<>();
    private ArrayList<IslaiduIrasas> islaidos = new ArrayList<>();
    private final Meniu meniu = new Meniu();

    public Biudzetas() {
        this.irasasList = new ArrayList<>();
        irasasList.addAll(pajamos);
        irasasList.addAll(islaidos);
    }


    public void pridetiIrasa(Irasas irasas) {
        this.irasasList.add(irasas);
    }

    public ArrayList<PajamuIrasas> getPajamuIrasus() { // sukurtas paga uzduoties reikalavimus
        return pajamos;
    }

    public ArrayList<IslaiduIrasas> getIslaiduIrasus() { // sukurtas paga uzduoties reikalavimus
        return islaidos;
    }


    public double getBendrosMenesioIslaidos() {
        double sumaIslaidos = 0.0;
        for (IslaiduIrasas islaidosReiksme : islaidos) {
            sumaIslaidos += islaidosReiksme.getSuma();
        }
        return sumaIslaidos;
    }

    public double getBendrosMenesioPajamos() {
        double sumaPajamos = 0.0;
        for (PajamuIrasas pajamosReiksme : pajamos) {
            sumaPajamos += pajamosReiksme.getSuma();
        }
        return sumaPajamos;
    }
    public double getBalansas() {
        return getBendrosMenesioPajamos() - getBendrosMenesioIslaidos();
    }


    public String gautiIrasoString(){
        return irasasList.toString();
    }

    public Irasas gautiIrasaPagalIDNumeri(int id) {
        return irasasList.get(id);
    }

    public void atnaujintiIrasa(Irasas irasas, int id){
        irasasList.set(id, irasas); // surandame irasa pagal ID ir i jo vieta istatome nauja irasa(atnaujiname irasa)
    }

    public void irasoIstrynimas(int id){
        irasasList.remove(id);
    }
}



