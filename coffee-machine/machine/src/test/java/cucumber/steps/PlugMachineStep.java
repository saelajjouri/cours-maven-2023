package cucumber.steps;

import fr.imt.cours.machine.CoffeeMachine;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.mockito.Mockito;

import java.util.Random;

public class PlugMachineStep {

    public CoffeeMachine coffeeMachine;

    @Given("the machine of coffee with {double} l of min capacity, {double} l of max capacity, {double} l per h of water flow for the pump")
    public void givenACoffeeMachine(double minimalWaterCapacity, double maximalWaterCapacity, double pumpWaterFlow){
        coffeeMachine = new CoffeeMachine(minimalWaterCapacity, maximalWaterCapacity, minimalWaterCapacity, maximalWaterCapacity, pumpWaterFlow);
    }


    @And("the coffee machine is not connected to the electrical network")
    public void notPlugged(){
        //On créé un mock de l'objet random
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        //On vient ensuite stubber la méthode nextGaussian pour pouvoir controler la valeur retournée
        //ici on veut qu'elle retourne 0.6
        Mockito.when(randomMock.nextGaussian()).thenReturn(0.6);
        //On injecte ensuite le mock créé dans la machine à café
        coffeeMachine.setRandomGenerator(randomMock);

        MatcherAssert.assertThat(coffeeMachine.isPlugged(), Matchers.is(false));
    }

    @And("the coffee machine is not out of order")
    public void notOutOfOrder(){
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        Mockito.when(randomMock.nextGaussian()).thenReturn(0.0);
        coffeeMachine.setRandomGenerator(randomMock);

        MatcherAssert.assertThat(coffeeMachine.isOutOfOrder(), Matchers.is(false));
    }

    @When("the user plugs the machine into the electrical socket")
    public void machineIsConnectingToElectricalSocket() {
        coffeeMachine.plugToElectricalPlug();
    }


    @Then("the coffee machine is correctly connected to the electrical network")
    public void machineConnectedToElectricalSocket(){
        MatcherAssert.assertThat(coffeeMachine.isPlugged(), Matchers.is(true));
    }
}
