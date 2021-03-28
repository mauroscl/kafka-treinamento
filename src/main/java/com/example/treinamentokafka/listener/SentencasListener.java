package com.example.treinamentokafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SentencasListener {

  @KafkaListener(topics = "sentencas", groupId = "contador-caracteres",
      properties = {ConsumerConfig.AUTO_OFFSET_RESET_CONFIG + "=earliest"})
  @SendTo("sentencas-uppercase")
  public String contarCaracteres(Message<String> message){
    var sentenca = message.getPayload();
    log.info("{} tem {} caracteres", sentenca, sentenca.length());
    return sentenca;
  }

  @KafkaListener(topics = "sentencas", groupId = "contador-palavras",
      properties = {ConsumerConfig.AUTO_OFFSET_RESET_CONFIG + "=earliest"})
  public void contarPalavras1(ConsumerRecord<String, String> cr) {
    log.info("contar palavras 1 recebendo dados da partição {}, offset {} ", cr.partition(), cr.offset());
    var sentenca = cr.value();
    var resultado = sentenca.split("\\s.").length;
    log.info("{} tem {} palavras", sentenca, resultado);
  }

  @KafkaListener(topics = "sentencas", groupId = "contador-palavras",
      properties = {ConsumerConfig.AUTO_OFFSET_RESET_CONFIG + "=earliest"})
  public void contarPalavras2(ConsumerRecord<String, String> cr) {
    log.info("contar palavras 2 recebendo dados da partição {}, offset {}: ", cr.partition(), cr.offset());
    var sentenca = cr.value();
    var resultado = sentenca.split("\\s.").length;
    log.info("{} tem {} palavras", sentenca, resultado);
  }

  @KafkaListener(topics = "sentencas-uppercase", groupId = "toupper",
      properties = {ConsumerConfig.AUTO_OFFSET_RESET_CONFIG + "=earliest"},
      containerFactory = "manualKafkaListener")
  public void exibirUpperCase(ConsumerRecord<String, String> cr, Acknowledgment acknowledgment) {
    log.info(cr.value().toUpperCase());
    acknowledgment.acknowledge();
  }

}
