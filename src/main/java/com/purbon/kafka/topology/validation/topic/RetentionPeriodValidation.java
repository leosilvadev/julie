package com.purbon.kafka.topology.validation.topic;

import com.purbon.kafka.topology.Configuration;
import com.purbon.kafka.topology.exceptions.ValidationException;
import com.purbon.kafka.topology.model.Topic;
import com.purbon.kafka.topology.validation.TopicValidation;

public class RetentionPeriodValidation implements TopicValidation {

  @Override
  public void valid(Topic topic, Configuration globalConfig) throws ValidationException {
    final Long topicRetention = getRetentionOrZero(topic);
    if (topicRetention > globalConfig.getTopologyValidationMaxRetentionPeriod()) {
      String msg =
          String.format(
              "Topic %s exceeds the max retention period defined. Topic retention: %s, max retention allowed: %s",
              topic, topicRetention, globalConfig.getTopologyValidationMaxRetentionPeriod());
      throw new ValidationException(msg);
    }
  }

  private Long getRetentionOrZero(Topic topic) {
    try {
      return Long.parseLong(topic.getConfig().getOrDefault("delete.retention.ms", "0"));
    } catch (final NumberFormatException ex) {
      return 0L;
    }
  }
}
