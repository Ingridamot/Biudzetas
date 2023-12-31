package lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta;


import lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta.enumai.PajamuIslaiduEnumas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if (irasas.getTipas()== PajamuIslaiduEnumas.PAJAMOS){
            pajamos.add((PajamuIrasas) irasas);
        }else{
            islaidos.add((IslaiduIrasas) irasas);
        }

    }

    public ArrayList<PajamuIrasas> gautiPajamuIrasus() {
        return new ArrayList<>(pajamos);
    }
    public ArrayList<IslaiduIrasas> gautiIslaiduIrasus() {
        return new ArrayList<>(islaidos);
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

    public List<String> readLines(String fileName){
        try {
            var reader = new BufferedReader(new FileReader(fileName));
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void writeToFile(String filename){
        try {
            var writer = new BufferedWriter(new FileWriter(filename));
            for(int i =0; i< irasasList.size(); i++){
                String irasas = irasasList.get(i).toString();
                int indeks = irasas.indexOf(",");
                irasas = irasas.substring(indeks + 1).trim()+'\n';
                writer.write(irasas);
            }
            writer.flush();
            writer.close();
        } catch (IOException e){
            throw new RuntimeException("Kazkas netaip", e);
        }
    }
}



