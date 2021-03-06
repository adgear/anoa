package com.adgear.anoa.write;

import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;

import com.adgear.anoa.Anoa;
import com.adgear.anoa.AnoaHandler;
import com.fasterxml.jackson.core.JsonGenerator;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility class for generating functions for serializing Protobuf records. Unless specified
 * otherwise, the functions should not be deemed thread-safe.
 */
final public class ProtobufEncoders {

  private ProtobufEncoders() {
  }

  /**
   * @return A function for serializing Protobuf records as binary blobs.
   */
  static public <R extends MessageLite> Function<R, byte[]> binary() {
    return MessageLite::toByteArray;
  }

  /**
   * @param anoaHandler {@code AnoaHandler} instance to use for exception handling
   * @param <M>         Metadata type
   * @return A function for serializing Protobuf records as binary blobs.
   */
  static public <R extends MessageLite, M> Function<Anoa<R, M>, Anoa<byte[], M>> binary(
      AnoaHandler<M> anoaHandler) {
    return anoaHandler.function(MessageLite::toByteArray);
  }

  /**
   * @param recordClass Protobuf record class object
   * @param supplier    called for each new record serialization
   * @param <R>         Protobuf record type
   * @param <G>         JsonGenerator type
   * @return A function which calls the supplier for a JsonGenerator object and writes the record
   * into it, in compact form.
   */
  static public <R extends Message, G extends JsonGenerator> Function<R, G> jackson(
      Class<R> recordClass,
      Supplier<G> supplier) {
    return new ProtobufWriter<>(recordClass).encoder(supplier);
  }

  /**
   * @param recordClass Protobuf record class object
   * @param supplier    called for each new record serialization
   * @param <R>         Protobuf record type
   * @param <G>         JsonGenerator type
   * @return A function which calls the supplier for a JsonGenerator object and writes the record
   * into it, in strict form.
   */
  static public <R extends Message, G extends JsonGenerator> Function<R, G> jacksonStrict(
      Class<R> recordClass,
      Supplier<G> supplier) {
    return new ProtobufWriter<>(recordClass).encoderStrict(supplier);
  }

  /**
   * @param anoaHandler {@code AnoaHandler} instance to use for exception handling
   * @param recordClass Protobuf record class object
   * @param supplier    called for each new record serialization
   * @param <R>         Protobuf record type
   * @param <G>         JsonGenerator type
   * @param <M>         Metadata type
   * @return A function which calls the supplier for a JsonGenerator object and writes the record
   * into it, in compact form.
   */
  static public <R extends Message, G extends JsonGenerator, M>
  Function<Anoa<R, M>, Anoa<G, M>> jackson(
      AnoaHandler<M> anoaHandler,
      Class<R> recordClass,
      Supplier<G> supplier) {
    return new ProtobufWriter<>(recordClass).encoder(anoaHandler, supplier);
  }

  /**
   * @param anoaHandler {@code AnoaHandler} instance to use for exception handling
   * @param recordClass Protobuf record class object
   * @param supplier    called for each new record serialization
   * @param <R>         Protobuf record type
   * @param <G>         JsonGenerator type
   * @param <M>         Metadata type
   * @return A function which calls the supplier for a JsonGenerator object and writes the record
   * into it, in strict form.
   */
  static public <R extends Message, G extends JsonGenerator, M>
  Function<Anoa<R, M>, Anoa<G, M>> jacksonStrict(
      AnoaHandler<M> anoaHandler,
      Class<R> recordClass,
      Supplier<G> supplier) {
    return new ProtobufWriter<>(recordClass).encoderStrict(anoaHandler, supplier);
  }
}
