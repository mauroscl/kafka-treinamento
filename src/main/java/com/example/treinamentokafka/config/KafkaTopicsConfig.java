package com.example.treinamentokafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

  @Bean
  public NewTopic sentencas() {
    return TopicBuilder.name("sentencas")
        .partitions(2)
        .replicas(1)
        .build();
  }

  @Bean
  public NewTopic sentencasUppercase() {
    return TopicBuilder.name("sentencas-uppercase")
        .partitions(1)
        .replicas(1)
        .build();
  }

  @Bean
  public NewTopic numeros() {
    return TopicBuilder.name("numeros")
        .partitions(1)
        .replicas(1)
        .build();
  }

  @Bean
  public NewTopic pessoas() {
    return TopicBuilder.name("pessoas")
        .partitions(1)
        .replicas(1)
        .build();
  }

  @Bean
  public NewTopic pessoasDlt() {
    return TopicBuilder.name("pessoas.DLT")
        .partitions(1)
        .replicas(1)
        .build();
  }



}
