package ordination.ordination;

import java.util.ArrayList;

public class Patient {
    private String cprnr;
    private String navn;
    private double vaegt;

    // TODO: Link til Ordination
    private java.util.ArrayList<Ordination> ordinationer = new ArrayList<>();
    public java.util.ArrayList<Ordination> getOrdinationer() {
        return new java.util.ArrayList<>(ordinationer);
        // laver en kopi således at listen ikke ændres udefra
    }

    public void addOrdination(Ordination ordination) {
        if (!ordinationer.contains(ordination)) {
            ordinationer.add(ordination);
        }
    }

    public Patient(String cprnr, String navn, double vaegt) {
        this.cprnr = cprnr;
        this.navn = navn;
        this.vaegt = vaegt;
    }

    public String getCprnr() {
        return cprnr;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public double getVaegt(){
        return vaegt;
    }

    public void setVaegt(double vaegt){
        this.vaegt = vaegt;
    }

    //TODO: Metoder (med specifikation) til at vedligeholde link til Ordination

    @Override
    public String toString(){
        return navn + "  " + cprnr;
    }

}
