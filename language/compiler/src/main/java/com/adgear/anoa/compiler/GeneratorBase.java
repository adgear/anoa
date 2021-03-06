package com.adgear.anoa.compiler;

import org.apache.avro.Protocol;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Base class for generating schema files and java source code.
 */
abstract class GeneratorBase implements Generator {

  final protected Protocol protocol;
  final private List<String> importedNamespaces;
  final private Consumer<String> logger;

  protected GeneratorBase(CompilationUnit cu, String suffix, Consumer<String> logger) {
    this.protocol = cu.generate(suffix, true);
    this.importedNamespaces = cu.getImportedNamespaces().collect(Collectors.toList());
    this.logger = logger;
  }

  protected Stream<Path> getImports() {
    return importedNamespaces.stream().sequential()
        .map(ns -> new File(ns.replace('.', File.separatorChar), schemaFileName(ns)).toPath());
  }


  File getSchemaFile() {
    return new File(protocol.getNamespace().replace('.', File.separatorChar),
                    schemaFileName(protocol.getNamespace()));
  }

  @Override
  public File generateSchema(File schemaRootDir) throws SchemaGenerationException {
    File outputFile = new File(schemaRootDir, getSchemaFile().toString());
    try {
      outputFile.getParentFile().mkdirs();
      try (FileWriter writer = new FileWriter(outputFile)) {
        writer.write(generateSchema());
      }
      return outputFile;
    } catch (IOException e) {
      throw new SchemaGenerationException("Error writing schema to '" + outputFile + "'." , e);
    }
  }

  abstract protected String schemaFileName(String namespace);

  protected String comments(String docString, String prefix, String suffix) {
    return (docString == null || docString.trim().isEmpty())
           ? ""
           : (prefix + "/** " + docString.trim() + " */" + suffix);
  }

  protected void log(String message) {
    logger.accept(message);
  }

  protected void runCommand(String cmd, Stream<String> opts, File cwd)
      throws CodeGenerationException {
    File source = new File(cwd, getSchemaFile().toString());
    String src = cwd.toPath().relativize(source.toPath()).toString();
    CommandLineUtils.runCommand(cmd, Stream.concat(opts, Stream.of(src)), cwd, logger);
  }
}
