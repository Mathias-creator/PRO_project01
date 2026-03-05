package ordination.ordination;

import java.time.LocalDate;
import java.time.LocalTime;

public class DagligFast {
    // TODO
    // fast array med 4 doser: [morgen, middag, aften, nat]
    private Dosis[] doser = new Dosis[4];

    public DagligFast(LocalDate startDen, LocalDate slutDen, Patient patient, Laegemiddel laegemiddel, double morgenAntal,double middagAntal, double aftenAntal, double natAntal) {
        super();
        LocalTime[] tider = {
                LocalTime.of(8, 0),  //morgen
                LocalTime.of(12, 0), //middag
                LocalTime.of(18, 0), //aften
                LocalTime.of(24, 0), //nat
        };

        double[] mængder = {morgenAntal, middagAntal, aftenAntal, natAntal};
        for (int i = 0; i < 4; i++) {
            doser[i] = new Dosis(tider[i], mængder[i]);
        }
    }
    public Dosis[] getDoser() {
        return doser.clone(); // igen laver vi en kopi så den ikke ændres udefra
    }

    @Override
    public double samletDosis(){
        double total = 0.0;
        for (Dosis dosis : doser) {
            total += dosis.getAntal(); //summer mængde pr. dosis
        }
        return total * antalDage();
    }

    @Override
    public double doegnDosis(){
        double totalPrDag = 0.0;
        for (Dosis dosis : doser) {
            totalPrDag += dosis.getAntal();
        }
        return totalPrDag;
    }
}
