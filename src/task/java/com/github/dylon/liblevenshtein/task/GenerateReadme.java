package com.github.dylon.liblevenshtein.task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.ST;

public class GenerateReadme {
  public static void main(final String... args) throws IOException  {
    int argsIdx = 0;

    final String groupId = args[argsIdx ++];
    final String artifactId = args[argsIdx ++];
    final String version = args[argsIdx ++];

    final Path readmePath = Paths.get(args[argsIdx ++]);

    final String gradleVersion = args[argsIdx ++];
    final String javaSourceVersion = args[argsIdx ++];
    final String javaTargetVersion = args[argsIdx ++];

    final STGroup group = new STGroupDir("stringtemplate", '$', '$');

    final ST template = group.getInstanceOf("README");
    template.add("groupId", groupId);
    template.add("artifactId", artifactId);
    template.add("version", version);
    template.add("gradleVersion", gradleVersion);
    template.add("javaSourceVersion", javaSourceVersion);
    template.add("javaTargetVersion", javaTargetVersion);

    System.out.printf("%nRendering template [README] to [%s]%n", readmePath);
    final String readme = template.render() + "\n";
    Files.write(readmePath, readme.getBytes(StandardCharsets.UTF_8));
  }
}
