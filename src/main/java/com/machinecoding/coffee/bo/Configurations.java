package com.machinecoding.coffee.bo;

/**
 * Class containing a wrapper of different ingredients and their quantity
 */
public class Configurations {

    private int hotWater;
    private int hotMilk;
    private int gingerSyrup;
    private int sugarSyrup;
    private int teaLeavesSyrup;
    private int greenMixture;

  public Configurations(int hotWater, int hotMilk, int gingerSyrup, int sugarSyrup,
      int teaLeavesSyrup, int greenMixture) {
    this.hotWater = hotWater;
    this.hotMilk = hotMilk;
    this.gingerSyrup = gingerSyrup;
    this.sugarSyrup = sugarSyrup;
    this.teaLeavesSyrup = teaLeavesSyrup;
    this.greenMixture = greenMixture;
  }


  public int getHotWater() {
      return hotWater;
    }

    public void setHotWater(int hotWater) {
      this.hotWater = hotWater;
    }

    public int getHotMilk() {
      return hotMilk;
    }

    public void setHotMilk(int hotMilk) {
      this.hotMilk = hotMilk;
    }

    public int getGingerSyrup() {
      return gingerSyrup;
    }

    public void setGingerSyrup(int gingerSyrup) {
      this.gingerSyrup = gingerSyrup;
    }

    public int getSugarSyrup() {
      return sugarSyrup;
    }

    public void setSugarSyrup(int sugarSyrup) {
      this.sugarSyrup = sugarSyrup;
    }

    public int getTeaLeavesSyrup() {
      return teaLeavesSyrup;
    }

    public void setTeaLeavesSyrup(int teaLeavesSyrup) {
      this.teaLeavesSyrup = teaLeavesSyrup;
    }

    public int getGreenMixture() {
      return greenMixture;
    }

    public void setGreenMixture(int greenMixture) {
      this.greenMixture = greenMixture;
    }
  }
