# Visão geral
Projeto contendo códigos de exemplo de um treinamento sobre Kafka utilizando Spring Kafka

### Pré-requisitos
Para rodar estes exemplos são necessários

* docker
* docker-compose
* Java 11

### Informações importantes
Existem configuração de volume no docker-compose para armazenar os dados dos containers. Portanto, é necessário que seja criada uma pasta  com o nome `docker-data` no mesmo caminho que está o arquivo docker-compose.yml

A aplicação não foi testada em ambiente Windows, mas deve funcionar tendo o docker e o docker-compose instalados corretamente

### Rodando o docker-compose
Os comandos do docker-compose devem ser executados no mesmo diretório em que se encontra o arquivo `docker-compose.yml`. 
Outra opção é utilizar a opção -f, passando o path do arquivo docker-compose.yml

Para iniciar o ambiente do cluster kafka executar o seguinte comando:
```
docker-compose up -d
``` 

No Ubuntu, se tiver problemas de permissão de escrita nesta pasta quando subir os containers pela primeira vez, execute o seguinte comando:
```
 sudo chown -R $USER docker-data    
```

A opção `up` do docker-compose é necessária apenas na primeira execução, para que sejam gerados todos os containers e a rede no docker.
Nas demais vezes pode ser utilizada a opção `start`, com o comando
```
docker-composer start
``` 

Para encerrar o ambiente do cluster deve ser executado o comando
```
docker-compose stop
``` 

### Rodando a aplicação
Abra o projeto na sua IDE preferida, aguarde baixar as dependências e inicie a aplicação.
Ou ainda se quiser rodar sem abrir o código na IDE pode utilizar a task bootRun do gradle
```
gradle bootRun
```


Abrir a página [http://localhost:8080/swagger-ui.html](htpp://localhost:8080/swagger-ui.html). 
Nesta página você tem acesso aos end points para executar os casos de uso.

Todos os tópicos necessários para executar os casos de uso estão definidos no arquivo KafkaTopicsConfig e serão criados quando a aplicação for executada pela primeira vez.

### Casos de uso

#### /pessoas 
Deve ser informado o nome e a idade de uma pessoa. 
Se a idade for menor que 18 anos a mensagem será enviada para a dead letter, para o tópico pessoas.DLT.
Se a idade for maior que 18 anos a mensagem é processada

#### /numeros
Deve ser informado dois números. Estes números são enviados para o tópico de mesmo nome. 
Existem dois listeners para este tópico: um executa uma operação de multiplicação e outra executa uma divisão
O objetivo é mostrar que podemos ter dois (ou mais) consumer groups distintos com interesses diferentes no mesmo tópico

#### /sentencas
Deve ser informada uma sentença (string) qualquer.
A string recebida é enviada para o tópicos sentencas.
O tópicos sentencas tem dois consumer groups:
* contador-caracteres: conta a quantidade de caracteres da string e utiliza a anotação `@SendoTo`, que envia automaticamente o retorno da função para o tópico `sentenca-uppercase`
* contador-palavras: conta a quantidade de palavras na string. Nesse caso temos dois consumidores distintos para o mesmo consumer group. 
  O objetivo aqui é mostrar a escalabilidade dos consumidores dentro do mesmo consumer group. Por isso o tópico sentencas precisa ter duas partições.

O listener do tópico sentencas-uppercase transforma a mensagem recebida pela `@SendTo` e transforma a mesma em upper case, logando no console.

Neste listener é exemplificado a utilização Acknowledgment manual.
Perceba que para fazer isso a propriedade containerFactory é setada com o valor manualContainerFactory

O bean manualContainerFactory é definido no arquivo KafkaConfig

```
  @Bean
  public <K, V> KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> manualKafkaListener(
      ConsumerFactory<K, V> cf) {
    ConcurrentKafkaListenerContainerFactory<K, V> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(cf);
    factory.getContainerProperties().setAckMode(AckMode.MANUAL);
    return factory;
  }
``` 







