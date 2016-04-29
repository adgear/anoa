package com.adgear.anoa.compiler.javagen;

public class JavaCodeGenerationException extends Exception {

  public JavaCodeGenerationException(String message) {
    super(message);
  }

  public JavaCodeGenerationException(String message, Throwable cause) {
    super(message, cause);
  }
}