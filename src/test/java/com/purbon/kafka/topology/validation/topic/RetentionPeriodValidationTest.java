package com.purbon.kafka.topology.validation.topic;

import com.purbon.kafka.topology.Configuration;
import com.purbon.kafka.topology.Constants;
import com.purbon.kafka.topology.exceptions.ValidationException;
import com.purbon.kafka.topology.model.Impl.TopicImpl;
import com.typesafe.config.Config;
import java.util.HashMap;
import org.junit.Test;
import org.mockito.Mockito;

public class RetentionPeriodValidationTest {

  private final Long ONE_HOUR = 1000 * 60 * 60L;

  @Test
  public void testOkConfigValuesWhenNoTopicRetentionIsSet() throws ValidationException {
    var config = new HashMap<String, String>();

    var topic = new TopicImpl("topic", config);
    var configMock = Mockito.mock(Config.class);

    Mockito.when(configMock.hasPath(Constants.TOPOLOGY_VALIDATION_MAX_RETENTION_PERIOD_CONFIG))
        .thenReturn(true);
    Mockito.when(configMock.getLong(Constants.TOPOLOGY_VALIDATION_MAX_RETENTION_PERIOD_CONFIG))
        .thenReturn(1000 * 60 * 60 * 24L);

    var globalConfig = new Configuration(new HashMap<>(), configMock);

    RetentionPeriodValidation validation = new RetentionPeriodValidation();
    validation.valid(topic, globalConfig);
  }

  @Test
  public void testOkConfigValuesWhenTopicRetentionIsLowerThanTheLimit() throws ValidationException {
    var config = new HashMap<String, String>();
    config.put("delete.retention.ms", String.valueOf(ONE_HOUR * 10));

    var topic = new TopicImpl("topic", config);
    var configMock = Mockito.mock(Config.class);

    Mockito.when(configMock.hasPath(Constants.TOPOLOGY_VALIDATION_MAX_RETENTION_PERIOD_CONFIG))
        .thenReturn(true);
    Mockito.when(configMock.getLong(Constants.TOPOLOGY_VALIDATION_MAX_RETENTION_PERIOD_CONFIG))
        .thenReturn(1000 * 60 * 60 * 24L);

    var globalConfig = new Configuration(new HashMap<>(), configMock);

    RetentionPeriodValidation validation = new RetentionPeriodValidation();
    validation.valid(topic, globalConfig);
  }

  @Test(expected = ValidationException.class)
  public void testKoConfigValuesWhenTopicRetentionIsHigherThanTheLimit()
      throws ValidationException {
    var config = new HashMap<String, String>();
    config.put("delete.retention.ms", String.valueOf(ONE_HOUR * 48));

    var topic = new TopicImpl("topic", config);
    var configMock = Mockito.mock(Config.class);

    Mockito.when(configMock.hasPath(Constants.TOPOLOGY_VALIDATION_MAX_RETENTION_PERIOD_CONFIG))
        .thenReturn(true);
    Mockito.when(configMock.getLong(Constants.TOPOLOGY_VALIDATION_MAX_RETENTION_PERIOD_CONFIG))
        .thenReturn(1000 * 60 * 60 * 24L);

    var globalConfig = new Configuration(new HashMap<>(), configMock);

    RetentionPeriodValidation validation = new RetentionPeriodValidation();
    validation.valid(topic, globalConfig);
  }
}
