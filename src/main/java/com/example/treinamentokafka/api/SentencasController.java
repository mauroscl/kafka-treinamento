package com.example.treinamentokafka.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentencasController {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public SentencasController(
      final KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @PostMapping(value = "/sentencas", consumes = MediaType.TEXT_PLAIN_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void criarSentencas(@RequestBody String sentenca) {
    kafkaTemplate.send("sentencas", sentenca);
  }

}
