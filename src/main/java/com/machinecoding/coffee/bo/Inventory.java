package com.machinecoding.coffee.bo;

import com.machinecoding.coffee.exceptions.IngredientExhaustedException;

/**
 * Inventory object. Contains methods to refill inventory, procureIngredients and get snapshot.
 */
public class Inventory {

  Configurations inventoryConfigurations;

  public Inventory(
      Configurations inventoryConfigurations) {
    this.inventoryConfigurations = inventoryConfigurations;
  }
  /**
   * Method can be used to find the levels of various ingredients
   * @return
   */
  public Configurations getInventorySnapShot() {
    return new Configurations(inventoryConfigurations.getHotWater(), inventoryConfigurations.getHotMilk(),
        inventoryConfigurations.getGingerSyrup(), inventoryConfigurations.getSugarSyrup(),
        inventoryConfigurations.getTeaLeavesSyrup(), inventoryConfigurations.getGreenMixture());
  }

  /**
   * Method to refill inventory.
   * @param configurations
   */
  public void refillInventory(Configurations configurations) {
    inventoryConfigurations
        .setHotMilk(inventoryConfigurations.getHotMilk() + configurations.getHotMilk());
    inventoryConfigurations.setGingerSyrup(
        inventoryConfigurations.getGingerSyrup() + configurations.getGingerSyrup());
    inventoryConfigurations
        .setHotWater(inventoryConfigurations.getHotWater() + configurations.getHotWater());
    inventoryConfigurations
        .setSugarSyrup(inventoryConfigurations.getSugarSyrup() + configurations.getSugarSyrup());
    inventoryConfigurations.setTeaLeavesSyrup(
        inventoryConfigurations.getTeaLeavesSyrup() + configurations.getTeaLeavesSyrup());
    inventoryConfigurations.setGreenMixture(
        inventoryConfigurations.getGreenMixture() + configurations.getGreenMixture());
  }


  public synchronized String procureIngredients(Configurations configurations) {
    try {
      procureHotMilk(configurations.getHotMilk());
      procureHotWater(configurations.getHotWater());
      procureGingerSyrup(configurations.getGingerSyrup());
      procureSugarSyrup(configurations.getGingerSyrup());
      procureTeaLeavesSyrup(configurations.getTeaLeavesSyrup());
      procureGreenMixture(configurations.getGreenMixture());
    } catch (IngredientExhaustedException e) {
      return e.getMessage();
    }
    return null;
  }

  private void procureHotMilk(int requiredValue)
      throws IngredientExhaustedException {
    if (requiredValue > inventoryConfigurations.getHotMilk()) {
      throw new IngredientExhaustedException("Hot milk");
    }
    inventoryConfigurations.setHotMilk(inventoryConfigurations.getHotMilk() - requiredValue);
  }

  private void procureHotWater(int requiredValue)
      throws IngredientExhaustedException {
    if (requiredValue > inventoryConfigurations.getHotWater()) {
      throw new IngredientExhaustedException("Hot water");
    }
    inventoryConfigurations.setHotWater(inventoryConfigurations.getHotWater() - requiredValue);
  }

  private void procureGingerSyrup(int requiredValue)
      throws IngredientExhaustedException {
    if (requiredValue > inventoryConfigurations.getGingerSyrup()) {
      throw new IngredientExhaustedException("Ginger Syrup");
    }
    inventoryConfigurations
        .setGingerSyrup(inventoryConfigurations.getGingerSyrup() - requiredValue);
  }

  private void procureSugarSyrup(int requiredValue)
      throws IngredientExhaustedException {
    if (requiredValue > inventoryConfigurations.getSugarSyrup()) {
      throw new IngredientExhaustedException("Sugar Syrup");
    }
    inventoryConfigurations
        .setSugarSyrup(inventoryConfigurations.getSugarSyrup() - requiredValue);
  }

  private void procureTeaLeavesSyrup(int requiredValue)
      throws IngredientExhaustedException {
    if (requiredValue > inventoryConfigurations.getTeaLeavesSyrup()) {
      throw new IngredientExhaustedException("HotTea Leaves Syrup");
    }
    inventoryConfigurations
        .setTeaLeavesSyrup(inventoryConfigurations.getTeaLeavesSyrup() - requiredValue);
  }

  private void procureGreenMixture(int requiredValue)
      throws IngredientExhaustedException {
    if (requiredValue > inventoryConfigurations.getGreenMixture()) {
      throw new IngredientExhaustedException("Green Mixture");
    }
    inventoryConfigurations
        .setGreenMixture(inventoryConfigurations.getGreenMixture() - requiredValue);
  }


}
