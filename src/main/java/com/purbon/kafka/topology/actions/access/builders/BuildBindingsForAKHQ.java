package com.purbon.kafka.topology.actions.access.builders;

import com.purbon.kafka.topology.BindingsBuilderProvider;
import com.purbon.kafka.topology.actions.BaseAccessControlAction;
import com.purbon.kafka.topology.model.users.platform.AKHQInstance;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.resource.PatternType;

public class BuildBindingsForAKHQ extends BaseAccessControlAction {

  private final BindingsBuilderProvider builderProvider;
  private final AKHQInstance instance;

  public BuildBindingsForAKHQ(
      final BindingsBuilderProvider builderProvider, final AKHQInstance instance) {
    super();
    this.builderProvider = builderProvider;
    this.instance = instance;
  }

  @Override
  protected void execute() throws IOException {
    bindings = builderProvider.buildBindingsForAKHQ(instance);
  }

  @Override
  protected Map<String, Object> props() {
    Map<String, Object> map = new HashMap<>();
    map.put("Operation", getClass().getName());
    map.put("Principal", instance.getPrincipal());
    map.put("Topic", instance.getTopic());
    map.put("TopicPattern", PatternType.LITERAL);
    map.put("Group", instance.getGroup());
    map.put("GroupPattern", PatternType.LITERAL);
    return map;
  }
}
