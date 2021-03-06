package com.github.dylon.liblevenshtein.task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.ST;

public class GenerateWikidoc {
  public static void main(final String... args) throws IOException  {
    int argsIdx = 0;

    final String groupId = args[argsIdx ++];
    final String artifactId = args[argsIdx ++];
    final String version = args[argsIdx ++];

    final Path wikidocDir = Paths.get(args[argsIdx ++]);

    final String gradleVersion = args[argsIdx ++];
    final String javaSourceVersion = args[argsIdx ++];
    final String javaTargetVersion = args[argsIdx ++];

    final STGroup group = new STGroupDir("stringtemplate/wiki/", '$', '$');

    final String[] templateNames = {
      "installation",
      "building",
      "testing",
      "usage"
    };

    System.out.println();
    for (final String templateName : templateNames) {
      final ST template = group.getInstanceOf(templateName);
      template.add("groupId", groupId);
      template.add("artifactId", artifactId);
      template.add("version", version);
      template.add("gradleVersion", gradleVersion);
      template.add("javaSourceVersion", javaSourceVersion);
      template.add("javaTargetVersion", javaTargetVersion);

      final String wikidoc = template.render() + "\n";
      final Path wikidocPath = wikidocDir.resolve(templateName + ".java.md");
      System.out.printf("Rendering template [%s] to [%s]%n",
          templateName, wikidocPath);
      Files.write(wikidocPath, wikidoc.getBytes(StandardCharsets.UTF_8));
    }
  }
}
