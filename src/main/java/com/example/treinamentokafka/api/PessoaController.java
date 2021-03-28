package com.example.treinamentokafka.api;

import com.example.treinamentokafka.model.Pessoa;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PessoaController {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public PessoaController(
      final KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @PostMapping(value = "/pessoas")
  @ResponseStatus(HttpStatus.CREATED)
  @Transactional
  public void criarPessoa(@RequestBody Pessoa pessoa) {

    kafkaTemplate.send("pessoas", pessoa);

  }

}
