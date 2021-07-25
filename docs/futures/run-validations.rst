Validate your topologies
*******************************

A normal practise in many *gitops* deployments is to run a set of automated validations before allowing the changes in.
JulieOps allows the users to run a variable set of validations before the project will apply the changes into each of the managed components.

Validate a topology in a feature branch
-----------

As a user you can use the *--validate* CLI option to only validate the incoming topology. Note this would run a validation completely offline,
without any knowledge of the current state in the cluster.

To configure which validations you require for your topology the reader would need to do it in the configuration file, this can be done like this:

.. code-block:: bash

        topology.validations.0=com.purbon.kafka.topology.validation.topic.ConfigurationKeyValidation
        topology.validations.1=com.purbon.kafka.topology.validation.topic.TopicNameRegexValidation
        topology.validations.topic.name.regexp="[a-z0-9]"

In the previous example we have configured two validations.

1.- ConfigurationKeyValidation will make sure all config keys are valid for Kafka.
2.- Will validate, based on the configured regexp that all topic names follow the right pattern.

All detected errors will be reported in a single outcome like this:

.. code-block:: bash

    ...
    Exception in thread "main" com.purbon.kafka.topology.exceptions.ValidationException: Topology name does not follow the camelCase format: context
    Topic context.company.env.source.projectA.foo has an invalid number of partitions: 1
    Topic context.company.env.source.projectA.bar.avro has an invalid number of partitions: 1
    Topic context.company.env.source.projectB.bar.avro has an invalid number of partitions: 1
    Topic context.company.env.source.projectC.topicE has an invalid number of partitions: 1
    Topic context.company.env.source.projectC.topicF has an invalid number of partitions: 1
	    at com.purbon.kafka.topology.JulieOps.build(JulieOps.java:125)
	    at com.purbon.kafka.topology.JulieOps.build(JulieOps.java:75)
	    at com.purbon.kafka.topology.CommandLineInterface.processTopology(CommandLineInterface.java:206)
	    at com.purbon.kafka.topology.CommandLineInterface.run(CommandLineInterface.java:156)
	    at com.purbon.kafka.topology.CommandLineInterface.main(CommandLineInterface.java:146)

Validate the maximum retention period for your topics
-----------

.. code-block:: bash

        topology.validations.0=com.purbon.kafka.topology.validation.topic.RetentionPeriodValidation
        topology.validation.max_retention_period=2592000000 # 30 days
        topology.validation.topic_matching_pattern="contextName\\.projectName\\.gdpr_.*" # Regex used to match topics which must be validated

.. code-block:: bash

    ...
    com.purbon.kafka.topology.exceptions.ValidationException: Topic null.context.project.users_topic exceeds the max retention period defined. Topic retention: 172800000, max retention allowed: 86400000

    	at com.purbon.kafka.topology.validation.topic.RetentionPeriodValidation.valid(RetentionPeriodValidation.java:20)
    	at com.purbon.kafka.topology.validation.topic.RetentionPeriodValidationTest.testKoConfigValuesWhenTopicRetentionIsHigherThanTheLimit(RetentionPeriodValidationTest.java:91)
    	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
    	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    	at java.base/java.lang.reflect.Method.invoke(Method.java:567)
    	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:59)
    	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
    	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:56)
    	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
    	at org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)
    	at org.junit.runners.BlockJUnit4ClassRunner$1.evaluate(BlockJUnit4ClassRunner.java:100)
    	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:366)
    	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:103)
    	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:63)
    	at org.junit.runners.ParentRunner$4.run(ParentRunner.java:331)
    	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:79)
    	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:329)
    	at org.junit.runners.ParentRunner.access$100(ParentRunner.java:66)
    	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:293)
    	at org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)
    	at org.junit.runners.ParentRunner.run(ParentRunner.java:413)
    	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
    	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:69)
    	at com.intellij.rt.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:33)
    	at com.intellij.rt.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:220)
    	at com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:53)

Add your own validations
-----------

JulieOps provides you with a set of integrated validations, however you as user can provide your own. To do so you will need to:

* Code your validation following the required interfaces as defined in the JulieOps project. See core validations to see the current pattern.
* Build a jar with your validations.
* Run JulieOps with a configured CLASSPATH where the JVM can find access to your validations jar in order to dynamically load them.
Remember when running JulieOps you can use the _JULIE_OPS_OPTIONS_ env variable to pass custom system configurations such as CLASSPATH or related to security.

**NOTE**: The UberJar is for now only available from the release page, in future releases we will facilitate a smaller plugin jar.