package com.machinecoding.coffee;


import com.machinecoding.coffee.bo.CoffeeMachineInitializer;
import com.machinecoding.coffee.bo.CoffeeMachineInitializer.Beverages;
import com.machinecoding.coffee.bo.CoffeeMachineInitializer.Outlets;
import com.machinecoding.coffee.bo.Configurations;
import com.machinecoding.coffee.bo.Inventory;
import com.machinecoding.coffee.enums.Beverage;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.Assert;
import org.junit.Test;

public class CoffeeMachineTest {


  @Test
  public void dispenseSingleBeverageTest() throws InterruptedException, ExecutionException {

    Outlets outlets = new Outlets(2);
    Configurations totalItems = new Configurations(500, 500, 100, 100, 100, 0);
    Beverages beverages = initialiseBeverages();
    CoffeeMachineInitializer coffeeMachineInitializer = new CoffeeMachineInitializer(outlets,
        beverages, totalItems);
    Inventory inventory = new Inventory(coffeeMachineInitializer.getTotalItems());

    CoffeeMachine coffeeMachine = new CoffeeMachine(
        coffeeMachineInitializer.getOutlets().getCount(), inventory,
        coffeeMachineInitializer.getBeverages().getConfigMap());
    CompletableFuture<Void> makeTea = coffeeMachine.dispenseBeverageTask(Beverage.HOT_TEA);
    makeTea.get();
    Configurations configurations = coffeeMachine.getInventorySnapShot();
    Assert.assertEquals(300, configurations.getHotWater());
    Assert.assertEquals(400, configurations.getHotMilk());
    Assert.assertEquals(90, configurations.getGingerSyrup());
    Assert.assertEquals(90, configurations.getSugarSyrup());
    Assert.assertEquals(70, configurations.getTeaLeavesSyrup());
    Assert.assertEquals(0, configurations.getGreenMixture());
  }

  @Test
  public void dispenseSingleBeverageAndRefillInventoryTest()
      throws InterruptedException, ExecutionException {

    Outlets outlets = new Outlets(2);
    Configurations totalItems = new Configurations(500, 500, 100, 100, 100, 0);
    Beverages beverages = initialiseBeverages();
    CoffeeMachineInitializer coffeeMachineInitializer = new CoffeeMachineInitializer(outlets,
        beverages, totalItems);
    Inventory inventory = new Inventory(coffeeMachineInitializer.getTotalItems());

    CoffeeMachine coffeeMachine = new CoffeeMachine(
        coffeeMachineInitializer.getOutlets().getCount(), inventory,
        coffeeMachineInitializer.getBeverages().getConfigMap());
    CompletableFuture<Void> makeTea = coffeeMachine.dispenseBeverageTask(Beverage.HOT_TEA);
    makeTea.get();
    coffeeMachine.refillInventory(new Configurations(200, 100, 10, 10, 30, 0));
    Configurations configurations = coffeeMachine.getInventorySnapShot();
    Assert.assertEquals(500, configurations.getHotWater());
    Assert.assertEquals(500, configurations.getHotMilk());
    Assert.assertEquals(100, configurations.getGingerSyrup());
    Assert.assertEquals(100, configurations.getSugarSyrup());
    Assert.assertEquals(100, configurations.getTeaLeavesSyrup());
    Assert.assertEquals(0, configurations.getGreenMixture());

  }

  @Test
  public void updateTeaConfigurationTest()
      throws InterruptedException, ExecutionException {

    Outlets outlets = new Outlets(2);
    Configurations totalItems = new Configurations(500, 500, 100, 100, 100, 0);
    Beverages beverages = initialiseBeverages();
    CoffeeMachineInitializer coffeeMachineInitializer = new CoffeeMachineInitializer(outlets,
        beverages, totalItems);
    Inventory inventory = new Inventory(coffeeMachineInitializer.getTotalItems());

    CoffeeMachine coffeeMachine = new CoffeeMachine(
        coffeeMachineInitializer.getOutlets().getCount(), inventory,
        coffeeMachineInitializer.getBeverages().getConfigMap());
    CompletableFuture<Void> makeTea = coffeeMachine.dispenseBeverageTask(Beverage.HOT_TEA);
    makeTea.get();
    Configurations configurations = coffeeMachine.getInventorySnapShot();
    Assert.assertEquals(300, configurations.getHotWater());
    Assert.assertEquals(400, configurations.getHotMilk());
    Assert.assertEquals(90, configurations.getGingerSyrup());
    Assert.assertEquals(90, configurations.getSugarSyrup());
    Assert.assertEquals(70, configurations.getTeaLeavesSyrup());
    Assert.assertEquals(0, configurations.getGreenMixture());

    coffeeMachine.updateConfiguration(Beverage.HOT_TEA, new Configurations(300, 200, 20, 20, 20, 0));
    CompletableFuture<Void> makeSecondTea = coffeeMachine.dispenseBeverageTask(Beverage.HOT_TEA);
    makeSecondTea.get();
    configurations = coffeeMachine.getInventorySnapShot();
    Assert.assertEquals(0, configurations.getHotWater());
    Assert.assertEquals(200, configurations.getHotMilk());
    Assert.assertEquals(70, configurations.getGingerSyrup());
    Assert.assertEquals(70, configurations.getSugarSyrup());
    Assert.assertEquals(50, configurations.getTeaLeavesSyrup());
    Assert.assertEquals(0, configurations.getGreenMixture());

  }


  @Test
  public void dispenseMultipleBeveragesInParallelTest()
      throws InterruptedException, ExecutionException {

    Outlets outlets = new Outlets(3);
    Configurations totalItems = new Configurations(500, 500, 100, 100, 100, 0);
    CoffeeMachineInitializer coffeeMachineInitializer = new CoffeeMachineInitializer(outlets,
        initialiseBeverages(),
        totalItems);
    Inventory inventory = new Inventory(coffeeMachineInitializer.getTotalItems());

    CoffeeMachine coffeeMachine = new CoffeeMachine(
        coffeeMachineInitializer.getOutlets().getCount(), inventory,
        coffeeMachineInitializer.getBeverages().getConfigMap());
    CompletableFuture<Void> makeTea = coffeeMachine.dispenseBeverageTask(Beverage.HOT_TEA);
    CompletableFuture<Void> makeCoffee = coffeeMachine.dispenseBeverageTask(Beverage.HOT_COFFEE);
    CompletableFuture<Void> makeBlackTea = coffeeMachine.dispenseBeverageTask(Beverage.BLACK_TEA);
    CompletableFuture<Void> makeGreenTea = coffeeMachine.dispenseBeverageTask(Beverage.GREEN_TEA);
    CompletableFuture.allOf(makeTea, makeCoffee, makeBlackTea, makeGreenTea).get();

  }

  private Beverages initialiseBeverages() {

    Beverages beverages = new Beverages();
    Map<Beverage, Configurations> configurationsMap = new EnumMap<>(Beverage.class);
    configurationsMap.put(Beverage.HOT_TEA, new Configurations(200, 100, 10, 10, 30, 0));
    configurationsMap.put(Beverage.HOT_COFFEE, new Configurations(100, 400, 30, 50, 30, 0));
    configurationsMap.put(Beverage.BLACK_TEA, new Configurations(300, 0, 30, 50, 30, 0));
    configurationsMap.put(Beverage.GREEN_TEA, new Configurations(100, 0, 30, 50, 0, 30));

    beverages.setConfigMap(configurationsMap);
    return beverages;
  }


}