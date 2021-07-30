package com.purbon.kafka.topology.model;

import com.purbon.kafka.topology.model.users.platform.*;

public class Platform {

  private Kafka kafka;
  private KafkaConnect kafkaConnect;
  private SchemaRegistry schemaRegistry;
  private ControlCenter controlCenter;
  private KsqlServer ksqlServer;
  private AKHQ akhq;

  public Platform() {
    this.kafka = new Kafka();
    this.kafkaConnect = new KafkaConnect();
    this.schemaRegistry = new SchemaRegistry();
    this.controlCenter = new ControlCenter();
    this.ksqlServer = new KsqlServer();
    this.akhq = new AKHQ();
  }

  public Kafka getKafka() {
    return kafka;
  }

  public void setKafka(Kafka kafka) {
    this.kafka = kafka;
  }

  public SchemaRegistry getSchemaRegistry() {
    return schemaRegistry;
  }

  public void setSchemaRegistry(SchemaRegistry schemaRegistry) {
    this.schemaRegistry = schemaRegistry;
  }

  public ControlCenter getControlCenter() {
    return controlCenter;
  }

  public void setControlCenter(ControlCenter controlCenter) {
    this.controlCenter = controlCenter;
  }

  public KafkaConnect getKafkaConnect() {
    return kafkaConnect;
  }

  public void setKafkaConnect(KafkaConnect kafkaConnect) {
    this.kafkaConnect = kafkaConnect;
  }

  public KsqlServer getKsqlServer() {
    return ksqlServer;
  }

  public void setKsqlServer(KsqlServer ksqlServer) {
    this.ksqlServer = ksqlServer;
  }

  public AKHQ getAkhq() {
    return akhq;
  }

  public void setAkhq(AKHQ akhq) {
    this.akhq = akhq;
  }
}
