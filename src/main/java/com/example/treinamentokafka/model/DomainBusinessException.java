package com.example.treinamentokafka.model;

public class DomainBusinessException extends RuntimeException{

  public DomainBusinessException(final String message) {
    super(message);
  }
}
