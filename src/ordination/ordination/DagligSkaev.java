package ordination.ordination;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DagligSkaev extends Ordination{
    private ArrayList<Dosis> dagligDoser = new ArrayList<>();

    public DagligSkaev(LocalDate startDen, LocalDate slutDen, Patient patient, Laegemiddel laegemiddel) {
        super(startDen, slutDen, patient, laegemiddel);
    }


    // TODO
    public void opretDosis(LocalTime tid, double antal) {
        Dosis dosis = new Dosis(tid,antal);
        dagligDoser.add(dosis);

        // TODO
    }

    @Override
    public double samletDosis() {
        return doegnDosis() * antalDage();
    }

    @Override
    public double doegnDosis() {
        if (dagligDoser.isEmpty()){
            return 0.;
        }

        double totalPrDag = 0.0;
        for (Dosis dosis : dagligDoser) {
            totalPrDag += dosis.getAntal();
        }
        return totalPrDag;

    }

    @Override
    public String getType() {
        return "DagligDosis";
    }

    public ArrayList<Dosis> getDagligDoser() {
        return new ArrayList<>(dagligDoser);
    }
}
