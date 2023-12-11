package fr.imt.cours.machine.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WaterPump {

    public static final Logger logger = LogManager.getLogger(WaterPump.class);
    private final double pumpingCapacity;

    /**
     * Pompe à eau de la cafetière
     * @param pumpingCapacity Capacité de la pompe en litres par secondes
     */
    public WaterPump(double pumpingCapacity){
        this.pumpingCapacity = pumpingCapacity;
    }

    /**
     * Pompe le volume d'eau spécifié dans le réservoir
     * durée du pompage : (volume en L / débit de la pompe en L/seconde) * 1000 pour les ms * 2
     * @param waterVolume Volume d'eau à pomper
     * @param waterTank Réservoir d'eau
     * @return Temps de pompage en millisecondes multiplié par 2
     * @throws InterruptedException Exception levée en cas de problèmes lors du sleep par le Thread
     */
    public double pumpWater(double waterVolume, WaterTank waterTank) throws InterruptedException {
        double pumpingTime = (waterVolume / pumpingCapacity) * 1000 * 2;
        logger.info("Pumping time : "  +  pumpingTime);
        logger.info("Pumping...");
        Thread.sleep((long) (pumpingTime));
        waterTank.decreaseVolumeInTank(waterVolume);
        logger.info("Pumping OK");
        return pumpingTime;
    }

    public double getPumpingCapacity() {
        return pumpingCapacity;
    }
}
