package fr.imt.coffee.machine.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ElectricalResistanceTest {

    /** Fonction permettant de calculer le temps de chauffe de l'eau attendu.
     * Permet d'éviter de recalculer ce temps dans chaque test et éviter les erreurs humaines
     @param power Puissance de la résistance électrique en Watts
     @param waterVolume Volume d'eau en litre.
     */
    public double computeHeatingTime(double power, double waterVolume){
        return ((waterVolume * 5180 * (90 - 20)) / power) * 1000 / 10;
    }

    /**
     * Test permettant de vérifier le fonctionnement de la résistance électrique dans un cas nominal avec JUnit
     * @throws InterruptedException
     */
    @Test
    public void testWaterHeatingNominalCaseWithJUnit() throws InterruptedException {
        double power = 1000;
        double waterVolume = 0.15;

        double heatingTimeExpected = computeHeatingTime(power, waterVolume);

        ElectricalResistance electricalResistance = new ElectricalResistance(power);
        double heatingTimeActual = electricalResistance.waterHeating(waterVolume);

        //Assertion faite avec JUnit5 qui permet de vérifier que la valeur renvoyée par la resistance est bien égale
        //à l'attendue.
        //assertEquals(valeurAttendue, valeurRenvoyée)
        Assertions.assertEquals(heatingTimeExpected, heatingTimeActual);
    }

    /**
     * Test permettant de vérifier le fonctionnement de la résistance électrique dans un cas nominal avec Hamcrest
     * @throws InterruptedException
     */
    @Test
    public void testWaterHeatingNominalCaseWithHamcrest() throws InterruptedException {
        double power = 1000;
        double waterVolume = 0.15;

        double heatingTimeExpected = computeHeatingTime(power, waterVolume);

        ElectricalResistance electricalResistance = new ElectricalResistance(power);
        double heatingTimeActual = electricalResistance.waterHeating(waterVolume);

        //Assertion faite avec Hamcrest. Hamcrest permet comme JUnit de faire des assertions.
        //Il est parfois préféré car Hamcrest dispose de plus de fonction d'assertion que JUnit 5 notamment sur les listes.
        //Il dispose également d'une sémantique plus explicite que JUnit
        //Hamcrest utilise des matchers, ici: is()
        assertThat(heatingTimeExpected, is(heatingTimeActual));
    }


}
