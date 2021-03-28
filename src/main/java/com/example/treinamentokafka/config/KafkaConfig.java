package com.example.treinamentokafka.config;

import java.util.Collections;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
public class KafkaConfig {

  @Bean
  public SeekToCurrentErrorHandler seekToCurrentErrorHandler(
      KafkaOperations<Object, Object> kafkaOperations) {
    return new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(kafkaOperations),
        new FixedBackOff(1000, 2));
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplateString(ProducerFactory<String, String> pf) {
    Map<String, Object> configsToOverride = Collections
        .singletonMap(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    return new KafkaTemplate<>(pf, configsToOverride);
  }

  @Bean
  public KafkaTemplate<String, Object> kafkaTemplateJson(ProducerFactory<String, Object> pf) {
    Map<String, Object> configsToOverride = Collections
        .singletonMap(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new KafkaTemplate<>(pf, configsToOverride);
  }

  @Bean
  public KafkaTemplate<Object, Object> kafkaOperations(ProducerFactory<Object, Object> pf) {
    Map<String, Object> configsToOverride = Collections
        .singletonMap(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new KafkaTemplate<>(pf, configsToOverride);
  }

  @Bean
  public <K, V> KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> manualKafkaListener(
      ConsumerFactory<K, V> cf) {
    ConcurrentKafkaListenerContainerFactory<K, V> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(cf);
    factory.getContainerProperties().setAckMode(AckMode.MANUAL);
    return factory;
  }

}
