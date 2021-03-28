package com.example.treinamentokafka.api;

import com.example.treinamentokafka.model.Numeros;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumerosController {

  private final KafkaTemplate<String, Object> template;

  public NumerosController(
      final KafkaTemplate<String, Object> template) {
    this.template = template;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(value = "/numeros", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void processarNumeros(@RequestBody Numeros numeros) {
    template.send("numeros", numeros);
  }

}
