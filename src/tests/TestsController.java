import ordination.controller.Controller;
import ordination.ordination.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class TestsController {

    private Controller controller;
    private Patient testPatient;
    private Laegemiddel testLaegemiddel;
    private final LocalDate startDen = LocalDate.of(2025, 1, 1);
    private final LocalDate slutDen = LocalDate.of(2025, 1, 10);


    @BeforeEach
    public void setUp() {
        controller = Controller.getTestController();
        testPatient = controller.opretPatient("123456-7890", "Test Patient", 70);
        testLaegemiddel = controller.opretLaegemiddel("Test Lægemiddel",
                .1, .15, .2,
                "Styk");
    }

    @Test
    public void testOpretPNOrdinationKorrektInput() {
        double antal = 2.;

        PN result = controller.opretPNOrdination(startDen, slutDen,
                testPatient, testLaegemiddel, antal);

        assertThat(result, is(notNullValue()));
        assertThat(startDen, is(result.getStartDen()));
        assertThat(slutDen, is(result.getSlutDen()));
        assertThat(antal, is(result.getAntalEnheder()));
        assertThat(testLaegemiddel, is(result.getLaegemiddel()));
    }

    @Test
    public void testOpretPNOrdinationSammeDato() {
        double antal = 1.;

        PN result = controller.opretPNOrdination(startDen, startDen,
                testPatient, testLaegemiddel, antal);


        assertThat(result, is(notNullValue()));
        assertThat(startDen, is(result.getStartDen()));
        assertThat(startDen, is(result.getSlutDen()));
    }

    @Test
    public void testOpretDagligFastOrdinationKorrektInput() {
        DagligFast result = controller.opretDagligFastOrdination(startDen, slutDen,
                testPatient, testLaegemiddel, 1, 1, 1, 1);

        assertThat(result, is(notNullValue()));
        assertThat(startDen, is(result.getStartDen()));
        assertThat(slutDen, is(result.getSlutDen()));
        assertThat(40., is(result.samletDosis()));
    }

    @Test
    public void testOpretDagligSkaevOrdinationKorrektInput() {
        LocalTime[] tider = {LocalTime.of(8, 0), LocalTime.of(12, 0)};
        double[] doser = {1., 1.5};

        DagligSkaev result = controller.opretDagligSkaevOrdination(startDen, slutDen,
                testPatient, testLaegemiddel, tider, doser);

        assertThat(result, is(notNullValue()));
        assertThat(startDen, is(result.getStartDen()));
        assertThat(slutDen, is(result.getSlutDen()));
        assertThat(25., is(result.samletDosis()));
    }

    @Test
    public void testOrdinationPNAnvendtKorrektInput() {
        PN ordination = controller.opretPNOrdination(startDen, slutDen,
                testPatient, testLaegemiddel, 2.);

        controller.ordinationPNAnvendt(ordination, LocalDate.of(2025, 1, 5));

        assertThat(1, is(ordination.getAntalGangeGivet()));
    }

    @Test
    public void testAnbefaletDosisPrDoegnLet() {
        Patient letPatient = controller.opretPatient("123456-7890", "Let Patient", 20);
        double expectedDosis = 20 * .1;

        double result = controller.anbefaletDosisPrDoegn(letPatient, testLaegemiddel);

        assertThat(expectedDosis, is(result));
    }

    @Test
    public void testAnbefaletDosisPrDoegnNormal() {
        double expectedDosis = 70 * .15;

        double result = controller.anbefaletDosisPrDoegn(testPatient, testLaegemiddel);

        assertThat(expectedDosis, is(result));
    }

    @Test
    public void testAnbefaletDosisPrDoegnTung() {
        Patient tungPatient = controller.opretPatient("123456-7890", "Tung Patient", 130);
        double expectedDosis = 130 * .2;

        double result = controller.anbefaletDosisPrDoegn(tungPatient, testLaegemiddel);

        assertThat(expectedDosis, is(result));
    }

    @Test
    public void testAntalOrdinationerPrVægtPrLægemiddel() {
        controller.opretPNOrdination(startDen, slutDen,
                testPatient, testLaegemiddel, 2.);
        controller.opretDagligFastOrdination(startDen, slutDen,
                testPatient, testLaegemiddel, 1, 1, 1, 1);
        controller.opretDagligSkaevOrdination(startDen, slutDen,
                testPatient, testLaegemiddel, new LocalTime[]{LocalTime.of(8, 0)}, new double[]{1.});

        int result = controller.antalOrdinationerPrVægtPrLægemiddel(60, 80, testLaegemiddel);

        assertThat(3, is(result));
    }
}