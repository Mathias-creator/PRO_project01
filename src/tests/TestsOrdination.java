import ordination.ordination.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestsOrdination {

    private Patient patient;
    private Laegemiddel laegemiddel;
    private final LocalDate startDen = LocalDate.of(2025, 1, 1);
    private final LocalDate slutDen = LocalDate.of(2025, 1, 10);

    @BeforeEach
    void setUp() {
        patient = new Patient("123456-7890", "Test Patient", 70);
        laegemiddel = new Laegemiddel("Test Lægemiddel", .1,
                .15, .2, "Styk");
    }

    @Test
    void testAntalDage() {
        Ordination ordination = new PN(startDen, slutDen, patient, laegemiddel, 1.);
        int antalDage = ordination.antalDage();
        assertThat(antalDage, is(10));
    }

    @Test
    void testAntalDageSameDay() {
        Ordination ordination = new PN(startDen, startDen, patient, laegemiddel, 1.);
        int antalDage = ordination.antalDage();
        assertThat(antalDage, is(1));
    }

    @Test
    void testConstructorStartDateAfterEndDate() {
        LocalDate newStartDen = LocalDate.of(2025, 1, 11);
        boolean exceptionThrown = false;

        try {
            new PN(newStartDen, slutDen, patient, laegemiddel, 1.);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, is(true));
    }
}
