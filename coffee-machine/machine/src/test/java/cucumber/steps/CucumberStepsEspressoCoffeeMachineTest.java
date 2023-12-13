package cucumber.steps;
import fr.imt.cours.machine.EspressoCoffeeMachine;
import fr.imt.cours.machine.exception.CannotMakeCremaWithSimpleCoffeeMachine;
import fr.imt.cours.machine.exception.CoffeeTypeCupDifferentOfCoffeeTypeTankException;
import fr.imt.cours.machine.exception.LackOfWaterInTankException;
import fr.imt.cours.machine.exception.MachineNotPluggedException;
import fr.imt.cours.storage.coffee.type.CoffeeType;
import fr.imt.cours.storage.container.*;
//import fr.imt.cours.storage.cupboard.espresso.type.CoffeeType;
//import fr.imt.cours.storage.cupboard.container.*;
//import fr.imt.cours.storage.cupboard.exception.CupNotEmptyException;
import fr.imt.cours.storage.exception.CupNotEmptyException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import java.util.Random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


public class CucumberStepsEspressoCoffeeMachineTest {

    public EspressoCoffeeMachine espressoMachine;
    public Mug espressoMug;
    public Cup espressoCup;
    public CoffeeContainer espressoContainerWithCoffee;

    @Given("a espresso machine with {double} l of min capacity, {double} l of max capacity, {double} l per h of water flow for the pump")
    public void givenAnEspressoMachine(double minimalWaterCapacity, double maximalWaterCapacity, double pumpWaterFlow){
        espressoMachine = new EspressoCoffeeMachine(minimalWaterCapacity, maximalWaterCapacity, minimalWaterCapacity, maximalWaterCapacity, pumpWaterFlow);
    }
    @Given("a espresso machine with {double} l of min capacity, {double} l of max capacity, {double} l per h of water flow for the pump that is out of order")
    public void givenAnEspressoMachineOutOfOrder(double minimalWaterCapacity, double maximalWaterCapacity, double pumpWaterFlow){
        espressoMachine = new EspressoCoffeeMachine(minimalWaterCapacity, maximalWaterCapacity, minimalWaterCapacity, maximalWaterCapacity, pumpWaterFlow);
        espressoMachine.setOutOfOrder(true);
    }
    @And("a espresso {string} with a capacity of {double}")
    public void aWithACapacityOf(String containerType, double containerCapacity) {
        if ("mug".equals(containerType))
            espressoMug = new Mug(containerCapacity);
        if ("cup".equals(containerType))
            espressoCup = new Cup(containerCapacity);
    }

    @When("I plug the espresso machine to electricity")
    public void iPlugTheMachineToElectricity() {
        espressoMachine.plugToElectricalPlug();
    }

    @And("I add {double} liter of water in the water tank of the espresso machine")
    public void iAddLitersOfWater(double waterVolume) {
        espressoMachine.addWaterInTank(waterVolume);
    }

    @And("I add {double} liter of {string} in the bean tank of the espresso machine")
    public void iAddLitersOfCoffeeBeans(double beanVolume, String coffeeType) {
        espressoMachine.addCoffeeInBeanTank(beanVolume, CoffeeType.valueOf(coffeeType));
    }

    @And("I made a espresso {string}")
    public void iMadeACoffee(String coffeeType) throws InterruptedException, CupNotEmptyException, LackOfWaterInTankException, MachineNotPluggedException, CoffeeTypeCupDifferentOfCoffeeTypeTankException, CannotMakeCremaWithSimpleCoffeeMachine {
        //On créé un mock de l'objet random
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        //On vient ensuite stubber la méthode nextGaussian pour pouvoir controler la valeur retournée
        //ici on veut qu'elle retourne 0.6
        Mockito.when(randomMock.nextGaussian()).thenReturn(0.6);
        //On injecte ensuite le mock créé dans la machine à café
        espressoMachine.setRandomGenerator(randomMock);

        if (espressoMug != null)
            espressoContainerWithCoffee = espressoMachine.makeACoffee(espressoMug, CoffeeType.valueOf(coffeeType));
        if (espressoCup != null)
            espressoContainerWithCoffee = espressoMachine.makeACoffee(espressoCup, CoffeeType.valueOf(coffeeType));

    }

    @Then("the espresso machine return a espresso mug not empty")
    public void theCoffeeMachineReturnACoffeeMugNotEmpty() {
        Assertions.assertFalse(espressoContainerWithCoffee.isEmpty());
    }


    @And("a espresso volume equals to {double}")
    public void aCoffeeVolumeEqualsTo(double coffeeVolume) {
        assertThat(coffeeVolume, is(espressoContainerWithCoffee.getCapacity()));
    }

    @And("a espresso {string} containing a espresso type {string}")
    public void aCoffeeMugContainingACoffeeType(String containerType, String coffeeType) {
        if ("mug".equals(containerType))
            assertThat(espressoContainerWithCoffee, instanceOf(CoffeeMug.class));
        if ("cup".equals(containerType))
            assertThat(espressoContainerWithCoffee, instanceOf(CoffeeCup.class));

        assertThat(espressoContainerWithCoffee.getCoffeeType(), is(CoffeeType.valueOf(coffeeType)));
    }
    @Then("nothing is returned to the user because the espresso machine is out of order")
    public void machineOutOfOrder() throws InterruptedException, CupNotEmptyException, LackOfWaterInTankException, MachineNotPluggedException, CoffeeTypeCupDifferentOfCoffeeTypeTankException, CannotMakeCremaWithSimpleCoffeeMachine, CoffeeTypeCupDifferentOfCoffeeTypeTankException,LackOfWaterInTankException,MachineNotPluggedException {
        espressoContainerWithCoffee = espressoMachine.makeACoffee(espressoCup, CoffeeType.ROBUSTA);
        Assertions.assertNull(espressoContainerWithCoffee);
    }


}
