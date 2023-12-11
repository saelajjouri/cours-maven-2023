package fr.imt.cours.machine.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CoffeeGrinder {
    public static final Logger logger = LogManager.getLogger(CoffeeGrinder.class);
    private final int grindingTime;

    /**
     * Constructeur du moulin à café intégré dans la machine
     * @param grindingTime Temps que met le moulin pour moudre le café en millisecondes
     */
    public CoffeeGrinder(int grindingTime) {
        this.grindingTime = grindingTime;
    }

    /**
     * Moue le volume de café spécifié dans le réservoir beanTank
     * durée du pompage : (volume en L / débit de la pompe en L/seconde) * 1000 pour les ms * 2
     * @param beanTank Réservoir de café
     * @return Temps pour moudre le café
     * @throws InterruptedException Exception levée en cas de problèmes lors du sleep par le Thread
     */
    public double grindCoffee(BeanTank beanTank) throws InterruptedException {
        logger.info("Grinding time : "  +  grindingTime);
        logger.info("Grinding...");
        Thread.sleep(grindingTime);
        beanTank.increaseVolumeInTank(0.2);
        logger.info("Grinding OK");
        return grindingTime;
    }
}
