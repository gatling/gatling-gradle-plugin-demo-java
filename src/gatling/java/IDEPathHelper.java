import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

public class IDEPathHelper {

  static final Path gradleSourcesDirectory;
  static final Path gradleResourcesDirectory;
  static final Path gradleBinariesDirectory;
  static final Path resultsDirectory;
  static final Path recorderConfigFile;

  static {
      gradleBinariesDirectory =
              Arrays.stream(System.getProperty("java.class.path").split(File.pathSeparator))
                      .map(cpe -> Path.of(cpe))
                      .filter(cpe -> cpe.endsWith("gatling"))
                      .findFirst()
                      .get();

      Path gradleBuildDirectory = gradleBinariesDirectory.getParent().getParent().getParent();
      Path projectRootDir = gradleBuildDirectory.getParent();
      Path gradleSrcDirectory = projectRootDir.resolve("src").resolve("gatling");

      gradleSourcesDirectory = gradleSrcDirectory.resolve("java");
      gradleResourcesDirectory = gradleSrcDirectory.resolve("resources");
      resultsDirectory = gradleBuildDirectory.resolve("reports").resolve("gatling");
      recorderConfigFile = gradleResourcesDirectory.resolve("recorder.conf");
  }
}
