import ordination.ordination.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestsDagligFast {
    private Patient patient;
    private Laegemiddel laegemiddel;
    private DagligFast dagligFast;
    private final LocalDate startDen = LocalDate.of(2025, 1, 1);
    private final LocalDate slutDen = LocalDate.of(2025, 1, 10);

    @BeforeEach
    void setUp() {
        patient = new Patient("123456-7890", "Test Patient", 70);
        laegemiddel = new Laegemiddel("Test Lægemiddel",
                .1, .15, .2, "Styk");
        dagligFast = new DagligFast(startDen, slutDen, patient, laegemiddel,
                1, 1, 1, 1);
    }

    @Test
    void testConstructor() {
        assertThat(dagligFast.getDoser(), is(notNullValue()));
        assertThat(dagligFast.getDoser().length, is(4));
        assertThat(dagligFast.getStartDen(), is(startDen));
        assertThat(dagligFast.getSlutDen(), is(slutDen));
        assertThat(dagligFast.getPatient(), is(patient));
        assertThat(dagligFast.getLaegemiddel(), is(laegemiddel));

        Dosis[] doser = dagligFast.getDoser();
        assertThat(doser[0].getAntal(), is(1.));
        assertThat(doser[0].getTid(), is(LocalTime.of(8, 0)));
        assertThat(doser[1].getAntal(), is(1.));
        assertThat(doser[1].getTid(), is(LocalTime.of(12, 0)));
        assertThat(doser[2].getAntal(), is(1.));
        assertThat(doser[2].getTid(), is(LocalTime.of(18, 0)));
        assertThat(doser[3].getAntal(), is(1.));
        assertThat(doser[3].getTid(), is(LocalTime.of(23, 0)));
    }

    @Test
    void testGetDoserReturnsClone() {
        Dosis[] doser1 = dagligFast.getDoser();
        Dosis[] doser2 = dagligFast.getDoser();
        assertThat(doser1, is(not(sameInstance(doser2))));
    }

    @Test
    void testDoegnDosis() {
        assertThat(dagligFast.doegnDosis(), is(4.));
    }

    @Test
    void testSamletDosis() {
        assertThat(dagligFast.samletDosis(), is(40.));
    }
    
    @Test
    void testDoegnDosisZero() {
        DagligFast zeroDoseOrdination = new DagligFast(startDen, slutDen, patient,
                laegemiddel, 0, 0, 0, 0);
        assertThat(zeroDoseOrdination.doegnDosis(), is(0.));
    }

    @Test
    void testSamletDosisZero() {
        DagligFast zeroDoseOrdination = new DagligFast(startDen, slutDen, patient,
                laegemiddel, 0, 0, 0, 0);
        assertThat(zeroDoseOrdination.samletDosis(), is(0.));
    }

    @Test
    void testGetType() {
        assertThat(dagligFast.getType(), is("Daglig fast"));
    }
}
