Gatling plugin for Gradle demo project
======================================

A simple showcase of a Gradle project using the
[Gatling plugin for Gradle](https://github.com/gatling/gatling-gradle-plugin). The full documentation for the plugin can
be found [on the Gatling website](https://gatling.io/docs/current/extensions/gradle_plugin/).

This project is written in Java, others are available for [Kotlin](https://github.com/gatling/gatling-gradle-plugin-demo-kotlin)
and [Scala](https://github.com/gatling/gatling-gradle-plugin-demo-scala).

It includes:

* minimal `build.gradle` leveraging Gradle wrapper
* latest version of `io.gatling.gradle` plugin applied
* sample
  [Simulation](https://javadoc.io/doc/io.gatling/gatling-core-java/latest/io/gatling/javaapi/core/Simulation.html)
  class, demonstrating sufficient Gatling functionality
* proper source file layout

Create sample project
---------------------

For this quickstart: `git` must be installed and available in $PATH,
and a JDK must be installed and $JAVA_HOME configured.

```bash
$ git clone https://github.com/gatling/gatling-gradle-plugin-demo-java.git
$ cd ./gatling-gradle-plugin-demo-java
```

Run simulations
---------------

To test it out, use the following commands:

* Run all Gatling simulations at once

```bash
$ ./gradlew gatlingRun
```

* Run a single simulation by its FQN (fully qualified class name)

```bash
$ ./gradlew gatlingRun-computerdatabase.BasicSimulation
```

Use with Gatling Enterprise Cloud
---------------------------------

To package your simulation for [Gatling Enterprise Cloud](https://gatling.io/enterprise/), run:

```bash
$ ./gradlew gatlingEnterprisePackage
```

Check out [the documentation](https://gatling.io/docs/enterprise/cloud/reference/user/package_gen/#gradle-project) for
more information, including how to upload the package to Gatling Enterprise Cloud directly from Gradle.

Use with Gatling Enterprise Self-Hosted
---------------------------------------

This sample is ready for [Gatling Enterprise Self-Hosted](https://gatling.io/enterprise/) to build from sources;
alternatively you can configure the build to publish to a binary repository. Check out
[the documentation](https://gatling.io/docs/enterprise/self-hosted/reference/current/user/binary/) for more information.
