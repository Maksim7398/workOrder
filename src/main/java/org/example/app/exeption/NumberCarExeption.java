package org.example.app.exeption;

public class NumberCarExeption extends Exception {

  private final  String message;

    public NumberCarExeption(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
