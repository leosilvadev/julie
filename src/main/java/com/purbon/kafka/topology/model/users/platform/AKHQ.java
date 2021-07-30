package com.purbon.kafka.topology.model.users.platform;

import java.util.ArrayList;
import java.util.List;

public class AKHQ {

  private List<AKHQInstance> instances;

  public AKHQ() {
    this.instances = new ArrayList<>();
  }

  public List<AKHQInstance> getInstances() {
    return instances;
  }

  public void setInstances(List<AKHQInstance> instances) {
    this.instances = instances;
  }
}
