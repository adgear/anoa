package com.adgear.anoa;

import org.jooq.lambda.Unchecked;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.stream.Stream;

public class TestInputStream extends InputStream {

  final private long readFailureIndex;
  final private ByteArrayInputStream byteArrayInputStream;
  private long c = 0;

  public TestInputStream(byte[] bytes, int readFailureIndex) {
    this(Stream.of(bytes), readFailureIndex);
  }

  public TestInputStream(Stream <byte[]> bytes, long readFailureIndex) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bytes.forEach(Unchecked.consumer(((OutputStream) baos)::write));
    this.byteArrayInputStream = new ByteArrayInputStream(baos.toByteArray());
    this.readFailureIndex = (readFailureIndex < 0) ? Long.MAX_VALUE : readFailureIndex;
  }

  @Override
  public int read() throws IOException {
    if (c++ >= readFailureIndex) {
      throw new TestIOException(readFailureIndex);
    }
    return byteArrayInputStream.read();
  }

  @Override
  public int available() throws IOException {
    return byteArrayInputStream.available();
  }
}
