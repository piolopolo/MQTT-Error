package com.example.demo.configuration;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptionsBuilder;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.configuration.properties.MqttProp;

@Configuration
public class ApplicationConfig {

  @Bean
  public MqttClient mqttClient(MqttProp prop) throws MqttException {
    var options = new MqttConnectionOptionsBuilder()
        .automaticReconnect(true)
        .cleanStart(true)
        .connectionTimeout(30)
        .username(prop.getUsername())
        .password(prop.getPasswordBytes())
        .build();

    var client = new MqttClient(prop.getBrokerAddress(), prop.getClientId());
    try {
      client.connect(options);
      System.out.println("MQTT Client connected successfully");
    } catch (MqttException e) {
      System.err.println("Failed to connect to MQTT Broker: " + e.getMessage());
      throw e;
    }

    return client;
  }
}
