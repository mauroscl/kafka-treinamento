package com.example.treinamentokafka.listener;

import com.example.treinamentokafka.model.Numeros;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NumerosListener {

  @KafkaListener(topics = "numeros", groupId = "multiplicador", properties =
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG
          + "=org.springframework.kafka.support.serializer.JsonDeserializer")
  public void multiplicar(Message<Numeros> message) {
    var numeros = message.getPayload();
    var numero1 = numeros.getNumero1();
    var numero2 = numeros.getNumero2();
    log.info("{} * {} = {}", numero1, numero2, numero1.multiply(numero2));
  }

  @KafkaListener(topics = "numeros", groupId = "divisor", properties =
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG
          + "=org.springframework.kafka.support.serializer.JsonDeserializer")
  public void dividir(Message<Numeros> message) {
    var numeros = message.getPayload();
    var numero1 = numeros.getNumero1();
    var numero2 = numeros.getNumero2();
    if (numero2.compareTo(BigDecimal.ZERO) == 0) {
      log.info("não é possivel dividir por zero");
    } else {
      log.info("{} / {} = {}", numero1, numero2, numero1.divide(numero2, RoundingMode.HALF_UP));
    }
  }


}
