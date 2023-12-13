package fr.imt.cours.machine;


import fr.imt.cours.machine.component.BeanTank;
import fr.imt.cours.machine.component.SteamPipe;
import fr.imt.cours.machine.exception.CoffeeTypeCupDifferentOfCoffeeTypeTankException;
import fr.imt.cours.machine.exception.LackOfWaterInTankException;
import fr.imt.cours.storage.coffee.type.CoffeeType;
import fr.imt.cours.storage.container.*;
import fr.imt.cours.storage.exception.CupNotEmptyException;
import fr.imt.cours.machine.exception.*;

public class EspressoCoffeeMachine extends CoffeeMachine {

    private final BeanTank secondaryBeanTank;
    private final SteamPipe steamPipe;
    public EspressoCoffeeMachine(double minCoffeeBeanTank, double maxCoffeeBeanTank,
                                 double minWaterTank, double maxWaterTank,
                                 double pumpingCapacity) {
        super(minCoffeeBeanTank, maxCoffeeBeanTank, minWaterTank, maxWaterTank, pumpingCapacity);
        this.secondaryBeanTank = new BeanTank(0, minCoffeeBeanTank, maxCoffeeBeanTank, null);
        this.steamPipe = new SteamPipe();
    }

    /**
     * Permet de faire couler un café à partir d'un contenant et d'un type de café
     * Doit retourner une instance d'un objet CoffeeMug ou CoffeeCup quand un Mug ou un Cup est passé en paramètre
     * Le contenant du café retourné ne doit pas être vide et doit avoir la même capacité que le contenant passsé en paramètre
     * Le contenant doit également avoir son coffeeType qui est égale au type de café passé en paramètre
     * Le nombre de café de la machine doit être incrémenté de 1
     * @param container Contenant pour faire couler le café
     * @param coffeeType Type de café dans l'énumération CoffeeType.java
     * @return Contenant non vide avec son type de café
     * @throws LackOfWaterInTankException Exception à lever lorsque que l'on manque d'eau dans le réservoir, message "You must plug your coffee machine to an electrical plug."
     * @throws MachineNotPluggedException Exception levée lorsque que la machine n'est pas branchée, message : "You must add more water in the water tank."
     * @throws CupNotEmptyException Exception levée lorsque le contenant donné en paramètre n'est pas vide, message : "The container given is not empty."
     * @throws InterruptedException Exception levée lorsqu'un problème survient dans les Threads lors du sleep
     * @throws CoffeeTypeCupDifferentOfCoffeeTypeTankException Exception levée lorsque le café souhaité est différent de celui chargé dans le réservoir de la cafetière
     */
    public CoffeeContainer makeACoffee(Container container, CoffeeType coffeeType) throws LackOfWaterInTankException, InterruptedException, MachineNotPluggedException, CupNotEmptyException, CoffeeTypeCupDifferentOfCoffeeTypeTankException {
        if(!isPlugged()){
            throw new MachineNotPluggedException("You must plug your coffee machine");
        }

        if(isOutOfOrder()){
            logger.warn("The machine is out of order. Please reset the coffee machine");
            return null;
        }

        if (getWaterTank().getActualVolume() < container.getCapacity()){
            throw new LackOfWaterInTankException("You must add more water in the water tank.");
        }

        if (!container.isEmpty()){
            throw new CupNotEmptyException("The container given is not empty or null.");
        }

        if(coffeeType != this.beanTank.getBeanCoffeeType() && coffeeType != this.secondaryBeanTank.getBeanCoffeeType()){
            throw new CoffeeTypeCupDifferentOfCoffeeTypeTankException("The type of coffee to be made in the cup is different from that in the tank.");
        }

        //coffeeMachineFailure();


        getElectricalResistance().waterHeating(container.getCapacity());
        getWaterPump().pumpWater(container.getCapacity(), getWaterTank());
        getCoffeeGrinder().grindCoffee(getBeanTank().getBeanCoffeeType().equals(coffeeType) ? getBeanTank() : secondaryBeanTank);

        CoffeeContainer coffeeContainer = null;
        if(container instanceof Cup)
            coffeeContainer = new CoffeeCup((Cup) container, coffeeType);
        if(container instanceof Mug)
            coffeeContainer = new CoffeeMug((Mug) container, coffeeType);

        coffeeContainer.setEmpty(false);
        return coffeeContainer;
    }
}
