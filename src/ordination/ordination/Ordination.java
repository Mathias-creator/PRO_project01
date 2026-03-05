package ordination.ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Ordination {
    private LocalDate startDen;
    private LocalDate slutDen;
    private Patient patient;
    // TODO Link til Laegemiddel
    private Laegemiddel laegemiddel;

    // TODO constructor (med specifikation)


    public Ordination(LocalDate startDen, LocalDate slutDen, Patient patient, Laegemiddel laegemiddel) {
        if (startDen.isAfter(slutDen)){
            throw new IllegalArgumentException("startdato skal være før eller lig med slutdato");
        }
        this.startDen = startDen;
        this.slutDen = slutDen;
        this.patient = patient;
        this.laegemiddel = laegemiddel;

        // VIGTIGT!: Esben sagde man skal have to-vejs link fordi patienten også skal vide, at den har denne ordination
        patient.addOrdination(this);
    }
    public LocalDate getStartDen() {
        return startDen;
    }

    public LocalDate getSlutDen() {
        return slutDen;
    }

    public Patient getPatient() {
        return patient;
    }

    public Laegemiddel getLaegemiddel() {
        return laegemiddel;
    }

    /**
     * Antal hele dage mellem startdato og slutdato. Begge dage inklusive.
     * @return antal dage ordinationen gælder for
     */
    public int antalDage() {
        // vi har +1 på fordi ChronoUnit.DAYS. ikke tæller slutdato med
        return (int) ChronoUnit.DAYS.between(startDen, slutDen) + 1;
    }

    @Override
    public String toString() {
        return "Ordination fra " + startDen + "til " + slutDen + " - " + laegemiddel.getNavn();
    }

    /**
     * Returnerer den totale dosis der er givet i den periode ordinationen er gyldig
     * @return
     */
    public abstract double samletDosis();

    /**
     * Returnerer den gennemsnitlige dosis givet pr dag i den periode ordinationen er gyldig
     * @return
     */
    public abstract double doegnDosis();

    /**
     * Returnerer ordinationstypen som en String
     * @return
     */
    public abstract String getType();
}
