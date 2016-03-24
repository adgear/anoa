package com.adgear.anoa.compiler;

import org.apache.avro.compiler.specific.SpecificCompiler;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

class InterfaceGenerator extends GeneratorBase {

  final boolean withAvro;
  final boolean withProtobuf;
  final boolean withThrift;

  InterfaceGenerator(CompilationUnit cu,
                     Consumer<String> logger,
                     boolean withAvro,
                     boolean withProtobuf,
                     boolean withThrift) {
    super(cu, "", logger);
    this.withAvro = withAvro;
    this.withProtobuf = withProtobuf;
    this.withThrift = withThrift;
  }

  @Override
  public String generateSchema() {
    return null;
  }

  @Override
  protected String schemaFileName(String namespace) {
    return namespace.substring(namespace.lastIndexOf('.') + 1) + ".anoa";
  }

  @Override
  public void generateSchema(File schemaRootDir) throws SchemaGenerationException {
    throw new SchemaGenerationException("Unsupported method");
  }

  @Override
  public void generateJava(File schemaRootDir, File javaRootDir)
      throws JavaCodeGenerationException {
    SpecificCompiler compiler =
        new AnoaInterfaceSpecificCompiler(protocol, withAvro, withProtobuf, withThrift);
    File source = new File(schemaRootDir, getSchemaFile().toString());
    log("Generating java code for Anoa schema in '" + getSchemaFile() + "'...");
    try {
      compiler.compileToDestination(source, javaRootDir);
    } catch (IOException e) {
      throw new JavaCodeGenerationException("Anoa code generation failed for '" + source + "'.", e);
    }
    log("Successfully generated java code for Anoa schema in '" + getSchemaFile() + "'.");
  }
}