package com.machinecoding.coffee;

import com.machinecoding.coffee.bo.Configurations;
import com.machinecoding.coffee.bo.Inventory;
import com.machinecoding.coffee.enums.Beverage;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CoffeeMachine {

  Inventory inventory;
  ExecutorService outlets;
  private Map<Beverage, Configurations> configurationsMap;

  public CoffeeMachine(int outlets, Inventory inventory,
      Map<Beverage, Configurations> configurationsMap) {
    this.outlets = Executors.newFixedThreadPool(outlets);
    this.inventory = inventory;
    this.configurationsMap = configurationsMap;
  }

  /**
   * Method to be used to dispense any beverage
   *
   * @param beverage
   * @return
   */
  public CompletableFuture<Void> dispenseBeverageTask(Beverage beverage) {
    return CompletableFuture.runAsync(() -> {
      System.out.println("Processing order for: " + beverage.name());
      System.out.println(this.prepareBeverage(beverage));
    }, outlets);
  }

  /**
   * Method to refill inventory
   *
   * @param inventoryToAdd
   */
  public void refillInventory(Configurations inventoryToAdd) {
    inventory.refillInventory(inventoryToAdd);
  }

  public Configurations getInventorySnapShot(){
    return inventory.getInventorySnapShot();
  }

  /**
   * Method to reconfigure beverage
   *
   * @param beverage
   * @param configurations
   */
  public void updateConfiguration(Beverage beverage, Configurations configurations) {
    configurationsMap.put(beverage, configurations);
  }

  /**
   * Method used to prepare beverage.
   *
   * @param beverage
   * @return
   */
  public String prepareBeverage(Beverage beverage) {

    try {
      Configurations beverageConfig = configurationsMap.get(beverage);
      if (null == beverageConfig) {
        return "Beverage not initialized";
      }

      // Procuring inventory
      String errorMessage = inventory.procureIngredients(beverageConfig);
      if (null != errorMessage) {
        return beverage.name() + " cannot be prepared because " + errorMessage + " is not available";
      }

      // Preparing beverage. Added a mock sleep of 3 seconds to depict beverage preparation time.
      Thread.sleep(3000);
      return beverage.name() + " is prepared";

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return null;
    }
  }


}
