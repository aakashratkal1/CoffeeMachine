package com.machinecoding.coffee.exceptions;

/**
 * Exception to be thrown when any of the ingredients get over.
 */
public class IngredientExhaustedException extends Exception {

    public IngredientExhaustedException(String ingredientName) {
      super(ingredientName);
    }
  }
