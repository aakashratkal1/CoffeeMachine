package com.machinecoding.coffee.bo;


import com.machinecoding.coffee.enums.Beverage;
import java.util.Map;

/**
 * Class to be taken as an input to the test cases.
 */
public class CoffeeMachineInitializer {

  private Outlets outlets;
  private Beverages beverages;
  private Configurations totalItems;

  public CoffeeMachineInitializer(Outlets outlets,
      Beverages beverages,
      Configurations totalItems) {
    this.outlets = outlets;
    this.beverages = beverages;
    this.totalItems = totalItems;
  }

  public Outlets getOutlets() {
    return outlets;
  }

  public Beverages getBeverages() {
    return beverages;
  }

  public Configurations getTotalItems() {
    return totalItems;
  }


  public static class Outlets {

    public Outlets(int count) {
      this.count = count;
    }

    private int count;

    public int getCount() {
      return count;
    }
  }

  public static class Beverages {

    private Map<Beverage, Configurations> configMap;

    public Map<Beverage, Configurations> getConfigMap() {
      return configMap;
    }

    public void setConfigMap(
        Map<Beverage, Configurations> configMap) {
      this.configMap = configMap;
    }
  }

}
