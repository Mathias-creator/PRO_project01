import ordination.ordination.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestsPN {

    private Patient patient;
    private Laegemiddel laegemiddel;
    private final LocalDate startDen = LocalDate.of(2025, 1, 1);
    private final LocalDate slutDen = LocalDate.of(2025, 1, 10);
    private final double antalEnheder = 2.5;
    private PN pn;

    @BeforeEach
    void setUp() {
        patient = new Patient("123456-7890", "Test Patient", 70);
        laegemiddel = new Laegemiddel("Test Lægemiddel", .1,
                .15, .2, "Styk");
        pn = new PN(startDen, slutDen, patient, laegemiddel, antalEnheder);
    }

    @Test
    void testConstructor() {
        assertThat(pn, is(notNullValue()));
        assertThat(pn.getStartDen(), is(startDen));
        assertThat(pn.getSlutDen(), is(slutDen));
        assertThat(pn.getPatient(), is(patient));
        assertThat(pn.getLaegemiddel(), is(laegemiddel));
        assertThat(pn.getAntalEnheder(), is(antalEnheder));
    }

    @Test
    void testGivDosisWithinPeriod() {
        LocalDate givesDen = LocalDate.of(2025, 1, 5);
        boolean result = pn.givDosis(givesDen);
        assertThat(result, is(true));
        assertThat(pn.getAntalGangeGivet(), is(1));
    }

    @Test
    void testGivDosisOutsidePeriodBefore() {
        LocalDate givesDen = LocalDate.of(2024, 12, 31);
        boolean exceptionThrown = false;
        try {
            pn.givDosis(givesDen);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertThat(exceptionThrown, is(true));
        assertThat(pn.getAntalGangeGivet(), is(0));
    }

    @Test
    void testGivDosisOutsidePeriodAfter() {
        LocalDate givesDen = LocalDate.of(2025, 1, 11);
        boolean exceptionThrown = false;
        try {
            pn.givDosis(givesDen);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertThat(exceptionThrown, is(true));
        assertThat(pn.getAntalGangeGivet(), is(0));
    }

    @Test
    void testGivMultipleDoses() {
        pn.givDosis(LocalDate.of(2025, 1, 1));
        pn.givDosis(LocalDate.of(2025, 1, 5));
        pn.givDosis(LocalDate.of(2025, 1, 10));
        assertThat(pn.getAntalGangeGivet(), is(3));
    }

    @Test
    void testDoegnDosisNoDoses() {
        assertThat(pn.doegnDosis(), is(0.));
    }

    @Test
    void testDoegnDosisOneDose() {
        pn.givDosis(LocalDate.of(2025, 1, 5));
        assertThat(pn.doegnDosis(), is(antalEnheder));
    }

    @Test
    void testDoegnDosisMultipleDosesDifferentDays() {
        pn.givDosis(LocalDate.of(2025, 1, 1));
        pn.givDosis(LocalDate.of(2025, 1, 10));
        // Total doser: 2 * 2,5 = 5
        // Dage imellem = 10
        // Daglig dose = 5 / 10 = 0,5
        assertThat(pn.doegnDosis(), is(.5));
    }

    @Test
    void testDoegnDosisMultipleDosesSameDay() {
        pn.givDosis(LocalDate.of(2025, 1, 5));
        pn.givDosis(LocalDate.of(2025, 1, 5));
        // Total doser: 2 * 2,5 = 5
        // Dage imellem = 1 dag
        // Daglig dose = 5 / 1 = 5
        assertThat(pn.doegnDosis(), is(5.));
    }

    @Test
    void testSamletDosisNoDoses() {
        assertThat(pn.samletDosis(), is(0.));
    }

    @Test
    void testSamletDosisOneDose() {
        pn.givDosis(LocalDate.of(2025, 1, 5));
        assertThat(pn.samletDosis(), is(antalEnheder));
    }

    @Test
    void testSamletDosisMultipleDoses() {
        pn.givDosis(LocalDate.of(2025, 1, 1));
        pn.givDosis(LocalDate.of(2025, 1, 5));
        pn.givDosis(LocalDate.of(2025, 1, 10));
        assertThat(pn.samletDosis(), is(antalEnheder * 3));
    }

    @Test
    void testGetAntalGangeGivetNoDoses() {
        assertThat(pn.getAntalGangeGivet(), is(0));
    }

    @Test
    void testGetAntalGangeGivetOneDose() {
        pn.givDosis(LocalDate.of(2025, 1, 5));
        assertThat(pn.getAntalGangeGivet(), is(1));
    }

    @Test
    void testGetAntalGangeGivetMultipleDoses() {
        pn.givDosis(LocalDate.of(2025, 1, 1));
        pn.givDosis(LocalDate.of(2025, 1, 5));
        pn.givDosis(LocalDate.of(2025, 1, 10));
        assertThat(pn.getAntalGangeGivet(), is(3));
    }

    @Test
    void testGetAntalEnheder() {
        assertThat(pn.getAntalEnheder(), is(antalEnheder));
    }

    @Test
    void testGetType() {
        assertThat(pn.getType(), is("PN"));
    }
}