package fr.imt.cours.machine.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElectricalResistance {
    private final double power;
    public static final Logger logger = LogManager.getLogger(ElectricalResistance.class);

    /**
     * Résistance électrique permettant de chauffer l'eau de la cafetière
     * @param power Puissance en Watts de la résistance
     */
    public ElectricalResistance(double power){
        this.power = power;
    }

    /**
     * Permet de chauffer l'eau de la cafetière. Calcul le temps de chauffe en le divisant par 10 pour simuler un
     * temps d'attente de chauffe acceptable. Met en attente le programme le temps que l'eau chauffe.
     * @param waterVolume Masse d'eau à chauffer (en L car 1l = 1kg)
     * @return Temps de chauffe de l'eau calculé via la formule : (masse x Ceau x (température finale - température départ)) / puissance résistance * 1000 / 10
     * @throws InterruptedException Exception levée en cas de problèmes lors du sleep par le Thread
     */
    public double waterHeating(double waterVolume) throws InterruptedException {
        // durée de chauffe d'un volume d'eau
        // Temps en sec = (masse x Ceau x (temp finale - temp départ)) / puissance résistance
        // on vient ensuite multiplier par 1000 pour avoir le temps en ms puis diviser par 10 pour éviter un temps d'attente trop long
        double heatingTime = ((waterVolume * 5180 * (90 - 20)) / power) * 1000 / 10;
        logger.info("Water heating time : "  +  heatingTime);
        logger.info("Water heating...");
        Thread.sleep((long) (heatingTime));
        logger.info("Water heating OK");
        return heatingTime;
    }

    public double getPower() {
        return power;
    }
}
