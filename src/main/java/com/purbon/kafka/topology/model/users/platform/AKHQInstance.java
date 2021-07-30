package com.purbon.kafka.topology.model.users.platform;

public class AKHQInstance {

  private String principal;
  private String topic;
  private String group;

  public AKHQInstance() {}

  public String getPrincipal() {
    return principal;
  }

  public void setPrincipal(String principal) {
    this.principal = principal;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }
}
