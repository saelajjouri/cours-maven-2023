import fr.imt.cours.machine.CoffeeMachine;
import fr.imt.cours.machine.exception.CannotMakeCremaWithSimpleCoffeeMachine;
import fr.imt.cours.machine.exception.CoffeeTypeCupDifferentOfCoffeeTypeTankException;
import fr.imt.cours.machine.exception.LackOfWaterInTankException;
import fr.imt.cours.machine.exception.MachineNotPluggedException;
import fr.imt.cours.storage.coffee.type.CoffeeType;
import fr.imt.cours.storage.container.*;
import fr.imt.cours.storage.exception.CupNotEmptyException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class CoffeeMachineUnitTest {
    public CoffeeMachine coffeeMachineUnderTest;

    /**
     * @BeforeEach est une annotation permettant d'exécuter la méthode annotée avant chaque test unitaire
     * Ici avant chaque test on initialise la machine à café
     */
    @BeforeEach
    public void beforeTest(){
        coffeeMachineUnderTest = new CoffeeMachine(
                0,10,
                0,10,  700);
    }

    /**
     * On vient tester si la machine ne se met pas en défaut
     */
    @Test
    public void testMachineFailureTrue(){
        //On créé un mock de l'objet random
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        //On vient ensuite stubber la méthode nextGaussian pour pouvoir contrôler la valeur retournée
        //ici on veut qu'elle retourne 1.0
        //when : permet de définir quand sur quelle méthode établir le stub
        //thenReturn : va permettre de contrôler la valeur retournée par le stub
        Mockito.when(randomMock.nextGaussian()).thenReturn(1.0);
        //On injecte ensuite le mock créé dans la machine à café
        coffeeMachineUnderTest.setRandomGenerator(randomMock);

        //On vérifie que le booleen outOfOrder est bien à faux avant d'appeler la méthode
        Assertions.assertFalse(coffeeMachineUnderTest.isOutOfOrder());
        //Ou avec Hamcrest
        assertThat(false, is(coffeeMachineUnderTest.isOutOfOrder()));

        //on appelle la méthode qui met la machine en défaut
        //On a mocké l'objet random donc la valeur retournée par nextGaussian() sera 1
        //La machine doit donc se mettre en défaut
        coffeeMachineUnderTest.coffeeMachineFailure();

        Assertions.assertTrue(coffeeMachineUnderTest.isOutOfOrder());
        assertThat(true, is(coffeeMachineUnderTest.isOutOfOrder()));
    }

    /**
     * On vient tester si la machine se met en défaut
     */
    @Test
    public void testMachineFailureFalse(){
        //On créé un mock de l'objet random
        Random randomMock = Mockito.mock(Random.class, Mockito.withSettings().withoutAnnotations());
        //On vient ensuite stubber la méthode nextGaussian pour pouvoir contrôler la valeur retournée
        //ici on veut qu'elle retourne 0.6
        //when : permet de définir quand sur quelle méthode établir le stub
        //thenReturn : va permettre de contrôler la valeur retournée par le stub
        Mockito.when(randomMock.nextGaussian()).thenReturn(0.6);
        //On injecte ensuite le mock créé dans la machine à café
        coffeeMachineUnderTest.setRandomGenerator(randomMock);

        //On vérifie que le booleen outOfOrder est bien à faux avant d'appeler la méthode
        Assertions.assertFalse(coffeeMachineUnderTest.isOutOfOrder());
        //Ou avec Hamcrest
        assertThat(false, is(coffeeMachineUnderTest.isOutOfOrder()));

        //on appelle la méthode qui met la machine en défaut
        //On a mocker l'objet random donc la valeur retournée par nextGaussian() sera 0.6
        //La machine doit donc NE PAS se mettre en défaut
        coffeeMachineUnderTest.coffeeMachineFailure();

        Assertions.assertFalse(coffeeMachineUnderTest.isOutOfOrder());
        //Ou avec Hamcrest
        assertThat(false, is(coffeeMachineUnderTest.isOutOfOrder()));
    }

    /**
     * On test que la machine se branche correctement au réseau électrique
     */
    @Test
    public void testPlugMachine(){
        Assertions.assertFalse(coffeeMachineUnderTest.isPlugged());

        coffeeMachineUnderTest.plugToElectricalPlug();

        Assertions.assertTrue(coffeeMachineUnderTest.isPlugged());
    }

    /**
     * On test qu'une exception est bien levée lorsque que le cup passé en paramètre retourne qu'il n'est pas vide
     * Tout comme le test sur la mise en défaut afin d'avoir un comportement isolé et indépendant de la machine
     * on vient ici mocker un objet Cup afin d'en maitriser complétement son comportement
     * On ne compte pas sur "le bon fonctionnement de la méthode"
     */
    @Test
    public void testMakeACoffeeCupNotEmptyException(){
        Cup mockCup = Mockito.mock(Cup.class);
        Mockito.when(mockCup.isEmpty()).thenReturn(false);

        coffeeMachineUnderTest.plugToElectricalPlug();

        //assertThrows( [Exception class expected], [lambda expression with the method that throws an exception], [exception message expected])
        //AssertThrows va permettre de venir tester la levée d'une exception, ici lorsque que le contenant passé en
        //paramètre n'est pas vide
        //On teste à la fois le type d'exception levée mais aussi le message de l'exception
        Assertions.assertThrows(CupNotEmptyException.class, ()->{
                coffeeMachineUnderTest.makeACoffee(mockCup, CoffeeType.MOKA);
            });
    }

    // Test machine out of order
    @Test
    public void testMakeACoffeeOutOfOrderException() throws Exception{

        Cup mockCup = Mockito.mock(Cup.class);
        Mockito.when(mockCup.isEmpty()).thenReturn(true);
        coffeeMachineUnderTest.plugToElectricalPlug();
        // Add water
        coffeeMachineUnderTest.addWaterInTank(15);
        coffeeMachineUnderTest.getBeanTank().setBeanCoffeeType(CoffeeType.ROBUSTA);
        coffeeMachineUnderTest.setOutOfOrder(true);

        //we call the method makeACoffe()
        CoffeeContainer container = coffeeMachineUnderTest.makeACoffee(mockCup, CoffeeType.ROBUSTA);
        //It should be out of order
        Assertions.assertNull(container);

    }
    @Test
    public void testToString() {


        String expectedOutput = "Your coffee machine has : \n" +
                "- water tank : " + coffeeMachineUnderTest.getWaterTank().toString() + "\n" +
                "- water pump : " + coffeeMachineUnderTest.getWaterPump().toString() + "\n" +
                "- electrical resistance : " + coffeeMachineUnderTest.getElectricalResistance() + "\n" +
                "- is plugged : " + coffeeMachineUnderTest.isPlugged() + "\n" +
                "and made " + coffeeMachineUnderTest.getNbCoffeeMade() + " coffees";

        Assertions.assertEquals(expectedOutput, coffeeMachineUnderTest.toString());

    }@Test
    public void testSetters() {

        // Test setters
        coffeeMachineUnderTest.setOutOfOrder(true);
        Assertions.assertEquals(true, coffeeMachineUnderTest.isOutOfOrder());

        coffeeMachineUnderTest.setNbCoffeeMade(1);
        Assertions.assertEquals(1, coffeeMachineUnderTest.getNbCoffeeMade());
// a finir
    }

    @Test
    public void testReset() {
        // Set the machine out of order
        coffeeMachineUnderTest.setOutOfOrder(true);

        // Verify that the machine is initially out of order
        Assertions.assertTrue(coffeeMachineUnderTest.isOutOfOrder());

        // Call the reset method
        coffeeMachineUnderTest.reset();

        // Verify that the machine is no longer out of order after calling reset
        Assertions.assertFalse(coffeeMachineUnderTest.isOutOfOrder());
    }
    @Test
    public void testMakeACoffeeCremaException() {
        Cup mockCup = Mockito.mock(Cup.class);
        Mockito.when(mockCup.isEmpty()).thenReturn(true);

        coffeeMachineUnderTest.plugToElectricalPlug();
        coffeeMachineUnderTest.getBeanTank().setBeanCoffeeType(CoffeeType.ARABICA_CREMA);

        // l'exception CannotMakeCremaWithSimpleCoffeeMachine est levée ( on a besoin de la machine espresso)
        Assertions.assertThrows(CannotMakeCremaWithSimpleCoffeeMachine.class, () -> {
            coffeeMachineUnderTest.makeACoffee(mockCup, CoffeeType.ARABICA_CREMA);
        });
    }
    @Test
    public void TestMakeCoffeeAndMachineUnplugged(){
        Cup mockCup = Mockito.mock(Cup.class);
        Mockito.when(mockCup.isEmpty()).thenReturn(true);
        Mockito.when(mockCup.getCapacity()).thenReturn(0.40);

        // Add water
        coffeeMachineUnderTest.addWaterInTank(10);

        // Add coffee type
        coffeeMachineUnderTest.getBeanTank().setBeanCoffeeType(CoffeeType.ROBUSTA);
        // l'exception MachineNotPluggedException est levée
        Assertions.assertThrows(MachineNotPluggedException.class, () -> {
            coffeeMachineUnderTest.makeACoffee(mockCup, CoffeeType.ARABICA_CREMA);
        });

    }
    // Test où on a coffeeType est différent au type de café dans le réservoir
    @Test
    public void testMakeACoffeeDifferentCoffeeTypeException() {
        Cup mockCup = Mockito.mock(Cup.class);
        Mockito.when(mockCup.isEmpty()).thenReturn(true);
        // Brancher la machine
        coffeeMachineUnderTest.plugToElectricalPlug();
        //l'exception CoffeeTypeCupDifferentOfCoffeeTypeTankException est levée
        Assertions.assertThrows(CoffeeTypeCupDifferentOfCoffeeTypeTankException.class, () -> {
            coffeeMachineUnderTest.makeACoffee(mockCup, CoffeeType.ARABICA);
        });
    }
    @Test
    public void testLackOfWaterInTankException() {
        Cup mockCup = Mockito.mock(Cup.class);
        Mockito.when(mockCup.isEmpty()).thenReturn(true);
        Mockito.when(mockCup.getCapacity()).thenReturn(0.50);
        // Brancher la machine
        coffeeMachineUnderTest.plugToElectricalPlug();
        // On ajoute le bon type de cafe
        coffeeMachineUnderTest.getBeanTank().setBeanCoffeeType(CoffeeType.BAHIA);
        //l'exception MachineNotPluggedException est levée
        Assertions.assertThrows(LackOfWaterInTankException.class, () -> {
            coffeeMachineUnderTest.makeACoffee(mockCup, CoffeeType.ARABICA_CREMA);
        });
    }

    @Test
    public void TestAddCoffeeInBeanTank(){

        coffeeMachineUnderTest.getBeanTank().setBeanCoffeeType(CoffeeType.ARABICA);
        // Add coffee of the same type
        coffeeMachineUnderTest.getBeanTank().increaseCoffeeVolumeInTank(20, CoffeeType.ARABICA);
        Assertions.assertEquals(coffeeMachineUnderTest.getBeanTank().getActualVolume(), 20);
    }
    @Test
    public void testAddCoffeeInBeanTank() {

        coffeeMachineUnderTest.getBeanTank().setBeanCoffeeType(CoffeeType.ARABICA);
        coffeeMachineUnderTest.getBeanTank().setActualVolume(20); // Volume initial
        coffeeMachineUnderTest.addCoffeeInBeanTank(30, CoffeeType.ARABICA);
        Assertions.assertEquals(50, coffeeMachineUnderTest.getBeanTank().getActualVolume());
    }

    @Test
    public void testMakeACoffeeInCup() throws Exception {

        // On crée un mock de Cup
        Cup mockCup = Mockito.mock(Cup.class);
        Mockito.when(mockCup.isEmpty()).thenReturn(true);
        Mockito.when(mockCup.getCapacity()).thenReturn(0.40); // Capacité de la tasse en millilitres
        coffeeMachineUnderTest.plugToElectricalPlug();
        coffeeMachineUnderTest.addWaterInTank(20);
        coffeeMachineUnderTest.getBeanTank().setBeanCoffeeType(CoffeeType.ARABICA);
        //Call the method make a coffee
        CoffeeContainer container = coffeeMachineUnderTest.makeACoffee(mockCup, CoffeeType.ARABICA);
        Assertions.assertNotNull(container);
        Assertions.assertTrue(container instanceof CoffeeCup);
        Assertions.assertEquals(container.getCoffeeType(), CoffeeType.ARABICA);

    }
    // prepare a coffee in a Mug
    @Test
    public void testMakeACoffeeInAMug() throws Exception {
        // On crée un mock de Mug
        Mug mockMug = Mockito.mock(Mug.class);
        Mockito.when(mockMug.isEmpty()).thenReturn(true);
        coffeeMachineUnderTest.plugToElectricalPlug();
        coffeeMachineUnderTest.getBeanTank().setBeanCoffeeType(CoffeeType.ARABICA);
        coffeeMachineUnderTest.plugToElectricalPlug();
        coffeeMachineUnderTest.addWaterInTank(20);
        coffeeMachineUnderTest.getBeanTank().setBeanCoffeeType(CoffeeType.ARABICA);
        CoffeeContainer container = coffeeMachineUnderTest.makeACoffee(mockMug, CoffeeType.ARABICA);
        Assertions.assertNotNull(container);
        Assertions.assertTrue(container instanceof CoffeeMug);

        //On verifie que le type de café est le bon
        Assertions.assertEquals(container.getCoffeeType(), CoffeeType.ARABICA);
    }



    @AfterEach
    public void afterTest(){

    }
}
