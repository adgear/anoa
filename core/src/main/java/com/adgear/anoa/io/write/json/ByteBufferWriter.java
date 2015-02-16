package com.adgear.anoa.io.write.json;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.nio.ByteBuffer;

class ByteBufferWriter extends JsonWriter<ByteBuffer> {

  @Override
  public void write(ByteBuffer byteBuffer, JsonGenerator jsonGenerator) throws IOException {
    final byte[] bytes = new byte[byteBuffer.limit() - byteBuffer.position()];
    byteBuffer.get(bytes, byteBuffer.position(), byteBuffer.limit());
    jsonGenerator.writeBinary(bytes);
  }
}
