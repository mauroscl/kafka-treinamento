package com.example.treinamentokafka.listener;

import com.example.treinamentokafka.model.DomainBusinessException;
import com.example.treinamentokafka.model.Pessoa;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PessoaListener {

  @KafkaListener(topics = "pessoas", groupId = "pessoa-consumer",
      properties = {
          ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG
              + "=org.springframework.kafka.support.serializer.JsonDeserializer"
      })

  public void pessoaConsumer(Message<Pessoa> mensagem) {
    var pessoa = mensagem.getPayload();
    log.info("mensagem recebida {}", pessoa);
    if (pessoa.getIdade().compareTo(18) < 0) {
      throw new DomainBusinessException("Pessoa não pode ter menos que 18 anos");
    }
    log.info("mensagem processada {}", pessoa);

  }


}
