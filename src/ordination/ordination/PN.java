package ordination.ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class PN extends Ordination {

    private final double antalEnheder;
    private final ArrayList<LocalDate> anvendteDatoer = new ArrayList<>();
    public PN(LocalDate startDen, LocalDate slutDen, Patient patient, Laegemiddel laegemiddel, double antalEnheder) {
        super(startDen, slutDen, patient, laegemiddel);
        this.antalEnheder = antalEnheder;
    }

    /**
     * Registrerer at der er givet en dosis paa dagen givesDen
     * Returnerer true hvis givesDen er inden for ordinationens gyldighedsperiode og datoen huskes
     * Retrurner false ellers og datoen givesDen ignoreres
     * @param givesDen
     * @return
     */


    public boolean givDosis(LocalDate givesDen) {
        // TODO
        if ((givesDen.isBefore(getStartDen()) || givesDen.isAfter(getSlutDen()))) {
            throw new  IllegalArgumentException("Dato ligger uden for ordinationsperiode");

        } else {

            anvendteDatoer.add(givesDen);
        }
        return true;
    }


    public double doegnDosis() {
        if (anvendteDatoer.isEmpty()){
            return 0.;
        }

        LocalDate foerst = anvendteDatoer.getFirst();
        LocalDate sidste = anvendteDatoer.getLast();


        for (LocalDate dato : anvendteDatoer){
           if (dato.isBefore(foerst)) foerst = dato;
           if (dato.isAfter(sidste)) sidste = dato;
        }

        double dage = ChronoUnit.DAYS.between(foerst,sidste) + 1;
        return samletDosis()/dage;
    }

    @Override
    public String getType() {
        return "PN";
    }


    public double samletDosis() {
        // TODO Done
        return antalEnheder* getAntalGangeGivet();
    }

    /**
     * Returnerer antal gange ordinationen er anvendt
     * @return
     */
    public int getAntalGangeGivet() {
        // TODO Done
        return anvendteDatoer.size();
    }

    public double getAntalEnheder() {
        return antalEnheder;
    }

}
