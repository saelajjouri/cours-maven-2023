package fr.imt.cours.machine.component;

public class WaterTank extends Tank {
    /**
     * Réservoir d'eau de la cafetière.
     * @param initialVolume Volume d'eau à mettre dans le réservoir à sa création
     * @param minVolume Volume d'eau minimal du réservoir
     * @param maxVolume Volume d'eau maximal du réservoir
     */
    public WaterTank(double initialVolume, double minVolume, double maxVolume){
        super(initialVolume, minVolume, maxVolume);
    }
}

