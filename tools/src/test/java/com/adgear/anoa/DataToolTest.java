package com.adgear.anoa;

import com.adgear.anoa.read.AvroStreams;
import com.adgear.anoa.read.CborStreams;
import com.adgear.anoa.tools.runnable.DataTool;
import com.adgear.anoa.tools.runnable.Format;

import org.apache.avro.Schema;
import org.apache.thrift.TBase;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class DataToolTest {

  final private Schema schema = BidReqs.avroSchema;

  static public byte[] convert(Schema schema,
                               Format in,
                               Format out,
                               InputStream inputStream) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    new DataTool<>(schema, null, null, in, out, inputStream, baos).run();
    return baos.toByteArray();
  }

  static public <T extends TBase> byte[] convert(
      Class<T> thriftClass,
      Format in,
      Format out,
      InputStream inputStream) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    new DataTool<>(null, thriftClass, null, in, out, inputStream, baos).run();
    return baos.toByteArray();
  }

  private InputStream bidreqs() {
    return getClass().getResourceAsStream("/bidreqs.json");
  }

  @Test
  public void testJsonToAvro() {
    Assert.assertEquals(
        946,
        AvroStreams.batch(AnoaHandler.NO_OP_HANDLER, new ByteArrayInputStream(
            convert(schema, Format.JSON, Format.AVRO, bidreqs())))
            .filter(Anoa::isPresent)
            .count());
  }

  @Test
  public void testJsonToThriftToCbor() {
    Assert.assertEquals(
        946,
        new CborStreams()
            .from(convert(BidReqs.thriftClass,
                          Format.THRIFT_JSON,
                          Format.CBOR,
                          new ByteArrayInputStream(
                              convert(BidReqs.thriftClass,
                                      Format.JSON,
                                      Format.THRIFT_JSON,
                                      bidreqs()))))
            .count());
  }
}
