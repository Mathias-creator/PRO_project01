import ordination.ordination.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;


public class TestsDagligSkaev {

    private Patient patient;
    private Laegemiddel laegemiddel;
    private DagligSkaev dagligSkaev;
    private final LocalDate startDen = LocalDate.of(2025, 1, 1);
    private final LocalDate slutDen = LocalDate.of(2025, 1, 10); // 10 days

    @BeforeEach
    void setUp() {
        patient = new Patient("123456-7890", "Test Patient", 70);
        laegemiddel = new Laegemiddel("Test Lægemiddel", .1,
                .15, .2, "Styk");
        dagligSkaev = new DagligSkaev(startDen, slutDen, patient, laegemiddel);
    }

    @Test
    void testConstructor() {
        assertThat(dagligSkaev.getStartDen(), is(startDen));
        assertThat(dagligSkaev.getSlutDen(), is(slutDen));
        assertThat(dagligSkaev.getPatient(), is(patient));
        assertThat(dagligSkaev.getLaegemiddel(), is(laegemiddel));
        assertThat(dagligSkaev.getDagligDoser(), is(notNullValue()));
        assertThat(dagligSkaev.getDagligDoser().size(), is(0));
    }

    @Test
    void testOpretDosis() {
        LocalTime tid = LocalTime.of(10, 0);
        double antal = 2.;
        dagligSkaev.opretDosis(tid, antal);
        ArrayList<Dosis> doser = dagligSkaev.getDagligDoser();
        assertThat(doser.size(), is(1));
        assertThat(doser.getFirst().getTid(), is(tid));
        assertThat(doser.getFirst().getAntal(), is(antal));
    }

    @Test
    void testSamletDosis() {
        dagligSkaev.opretDosis(LocalTime.of(8, 0), 1.);
        dagligSkaev.opretDosis(LocalTime.of(16, 0), 1.5);
        // Samlet dosis = (1 + 1.5) * 10 dage = 25
        assertThat(dagligSkaev.samletDosis(), is(25.));
    }

    @Test
    void testDoegnDosis() {
        dagligSkaev.opretDosis(LocalTime.of(8, 0), 1.);
        dagligSkaev.opretDosis(LocalTime.of(16, 0), 1.5);
        assertThat(dagligSkaev.doegnDosis(), is(2.5));
    }

    @Test
    void testDoegnDosisZero() {
        assertThat(dagligSkaev.doegnDosis(), is(0.));
    }

    @Test
    void testSamletDosisZero() {
        assertThat(dagligSkaev.samletDosis(), is(0.));
    }

    @Test
    void testGetType() {
        assertThat(dagligSkaev.getType(), is("DagligDosis"));
    }

    @Test
    void testGetDagligDoserReturnsCopy() {
        ArrayList<Dosis> doser1 = dagligSkaev.getDagligDoser();
        ArrayList<Dosis> doser2 = dagligSkaev.getDagligDoser();
        assertThat(doser1, is(not(sameInstance(doser2))));
    }
}