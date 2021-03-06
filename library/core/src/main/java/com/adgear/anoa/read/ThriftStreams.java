package com.adgear.anoa.read;

import com.adgear.anoa.Anoa;
import com.adgear.anoa.AnoaHandler;
import com.fasterxml.jackson.core.JsonParser;

import org.apache.thrift.TBase;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.transport.TFileTransport;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Utility class for deserializing Thrift records in a {@link java.util.stream.Stream}.
 */
final public class ThriftStreams {

  private ThriftStreams() {
  }

  /**
   * Stream from Thrift compact binary representations.
   *
   * @param supplier    Thrift record instance supplier
   * @param inputStream stream from which to deserialize
   * @param <T>         Thrift record type
   */
  static public <T extends TBase> Stream<T> compact(
      Supplier<T> supplier,
      InputStream inputStream) {
    return compact(supplier, new TIOStreamTransport(inputStream));
  }

  /**
   * Stream from Thrift compact binary representations.
   *
   * @param anoaHandler {@code AnoaHandler} instance to use for exception handling
   * @param supplier    Thrift record instance supplier
   * @param inputStream stream from which to deserialize
   * @param <T>         Thrift record type
   * @param <M>         Metadata type
   */
  static public <T extends TBase, M> Stream<Anoa<T, M>> compact(
      AnoaHandler<M> anoaHandler,
      Supplier<T> supplier,
      InputStream inputStream) {
    return compact(anoaHandler, supplier, new TIOStreamTransport(inputStream));
  }

  /**
   * Stream from Thrift compact binary representations.
   *
   * @param supplier Thrift record instance supplier
   * @param fileName name of file from which to read
   * @param <T>      Thrift record type
   */
  static public <T extends TBase> Stream<T> compact(
      Supplier<T> supplier,
      String fileName) {
    try {
      return compact(supplier, new TFileTransport(fileName, true));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Stream from Thrift compact binary representations.
   *
   * @param anoaHandler {@code AnoaHandler} instance to use for exception handling
   * @param supplier    Thrift record instance supplier
   * @param fileName    name of file from which to read
   * @param <T>         Thrift record type
   * @param <M>         Metadata type
   */
  static public <T extends TBase, M> Stream<Anoa<T, M>> compact(
      AnoaHandler<M> anoaHandler,
      Supplier<T> supplier,
      String fileName) {
    try {
      return compact(anoaHandler, supplier, new TFileTransport(fileName, true));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Stream from Thrift compact binary representations.
   *
   * @param supplier   Thrift record instance supplier
   * @param tTransport Thrift TTransport instance from which to read
   * @param <T>        Thrift record type
   */
  static public <T extends TBase> Stream<T> compact(
      Supplier<T> supplier,
      TTransport tTransport) {
    return LookAheadIteratorFactory.thrift(new TCompactProtocol(tTransport), supplier)
        .asStream();
  }

  /**
   * Stream from Thrift compact binary representations.
   *
   * @param anoaHandler {@code AnoaHandler} instance to use for exception handling
   * @param supplier    Thrift record instance supplier
   * @param tTransport  Thrift TTransport instance from which to read
   * @param <T>         Thrift record type
   * @param <M>         Metadata type
   */
  static public <T extends TBase, M> Stream<Anoa<T, M>> compact(
      AnoaHandler<M> anoaHandler,
      Supplier<T> supplier,
      TTransport tTransport) {
    return LookAheadIteratorFactory.thrift(anoaHandler, new TCompactProtocol(tTransport), supplier)
        .asStream();
  }

  /**
   * Stream from Thrift standard binary representations.
   *
   * @param supplier    Thrift record instance supplier
   * @param inputStream stream from which to deserialize
   * @param <T>         Thrift record type
   */
  static public <T extends TBase> Stream<T> binary(
      Supplier<T> supplier,
      InputStream inputStream) {
    return binary(supplier, new TIOStreamTransport(new BufferedInputStream(inputStream)));
  }

  /**
   * Stream from Thrift standard binary representations.
   *
   * @param anoaHandler {@code AnoaHandler} instance to use for exception handling
   * @param supplier    Thrift record instance supplier
   * @param inputStream stream from which to deserialize
   * @param <T>         Thrift record type
   * @param <M>         Metadata type
   */
  static public <T extends TBase, M> Stream<Anoa<T, M>> binary(
      AnoaHandler<M> anoaHandler,
      Supplier<T> supplier,
      InputStream inputStream) {
    return binary(anoaHandler,
                  supplier,
                  new TIOStreamTransport(new BufferedInputStream(inputStream)));
  }

  /**
   * Stream from Thrift standard binary representations.
   *
   * @param supplier Thrift record instance supplier
   * @param fileName name of file from which to read
   * @param <T>      Thrift record type
   */
  static public <T extends TBase> Stream<T> binary(
      Supplier<T> supplier,
      String fileName) {
    try {
      return binary(supplier, new TFileTransport(fileName, true));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Stream from Thrift standard binary representations.
   *
   * @param anoaHandler {@code AnoaHandler} instance to use for exception handling
   * @param supplier    Thrift record instance supplier
   * @param fileName    name of file from which to read
   * @param <T>         Thrift record type
   * @param <M>         Metadata type
   */
  static public <T extends TBase, M> Stream<Anoa<T, M>> binary(
      AnoaHandler<M> anoaHandler,
      Supplier<T> supplier,
      String fileName) {
    try {
      return binary(anoaHandler, supplier, new TFileTransport(fileName, true));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Stream from Thrift standard binary representations.
   *
   * @param supplier   Thrift record instance supplier
   * @param tTransport Thrift TTransport instance from which to read
   * @param <T>        Thrift record type
   */
  static public <T extends TBase> Stream<T> binary(
      Supplier<T> supplier,
      TTransport tTransport) {
    return LookAheadIteratorFactory.thrift(new TBinaryProtocol(tTransport), supplier).asStream();
  }

  /**
   * Stream from Thrift standard binary representations.
   *
   * @param anoaHandler {@code AnoaHandler} instance to use for exception handling
   * @param supplier    Thrift record instance supplier
   * @param tTransport  Thrift TTransport instance from which to read
   * @param <T>         Thrift record type
   * @param <M>         Metadata type
   */
  static public <T extends TBase, M> Stream<Anoa<T, M>> binary(
      AnoaHandler<M> anoaHandler,
      Supplier<T> supplier,
      TTransport tTransport) {
    return LookAheadIteratorFactory.thrift(anoaHandler, new TBinaryProtocol(tTransport), supplier)
        .asStream();
  }

  /**
   * Stream from Thrift JSON representations.
   *
   * @param supplier    Thrift record instance supplier
   * @param inputStream stream from which to deserialize
   * @param <T>         Thrift record type
   */
  static public <T extends TBase> Stream<T> json(
      Supplier<T> supplier,
      InputStream inputStream) {
    return json(supplier, new TIOStreamTransport(new BufferedInputStream(inputStream)));
  }

  /**
   * Stream from Thrift JSON representations.
   *
   * @param anoaHandler {@code AnoaHandler} instance to use for exception handling
   * @param supplier    Thrift record instance supplier
   * @param inputStream stream from which to deserialize
   * @param <T>         Thrift record type
   * @param <M>         Metadata type
   */
  static public <T extends TBase, M> Stream<Anoa<T, M>> json(
      AnoaHandler<M> anoaHandler,
      Supplier<T> supplier,
      InputStream inputStream) {
    return json(anoaHandler,
                supplier,
                new TIOStreamTransport(new BufferedInputStream(inputStream)));
  }

  /**
   * Stream from Thrift JSON representations.
   *
   * @param supplier Thrift record instance supplier
   * @param fileName name of file from which to read
   * @param <T>      Thrift record type
   */
  static public <T extends TBase> Stream<T> json(
      Supplier<T> supplier,
      String fileName) {
    try {
      return json(supplier, new TFileTransport(fileName, true));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Stream from Thrift JSON representations.
   *
   * @param anoaHandler {@code AnoaHandler} instance to use for exception handling
   * @param supplier    Thrift record instance supplier
   * @param fileName    name of file from which to read
   * @param <T>         Thrift record type
   * @param <M>         Metadata type
   */
  static public <T extends TBase, M> Stream<Anoa<T, M>> json(
      AnoaHandler<M> anoaHandler,
      Supplier<T> supplier,
      String fileName) {
    try {
      return json(anoaHandler, supplier, new TFileTransport(fileName, true));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Stream from Thrift JSON representations.
   *
   * @param supplier   Thrift record instance supplier
   * @param tTransport Thrift TTransport instance from which to read
   * @param <T>        Thrift record type
   */
  static public <T extends TBase> Stream<T> json(
      Supplier<T> supplier,
      TTransport tTransport) {
    return LookAheadIteratorFactory.thrift(new TJSONProtocol(tTransport), supplier).asStream();
  }

  /**
   * Stream from Thrift JSON representations.
   *
   * @param anoaHandler {@code AnoaHandler} instance to use for exception handling
   * @param supplier    Thrift record instance supplier
   * @param tTransport  Thrift TTransport instance from which to read
   * @param <T>         Thrift record type
   * @param <M>         Metadata type
   */
  static public <T extends TBase, M> Stream<Anoa<T, M>> json(
      AnoaHandler<M> anoaHandler,
      Supplier<T> supplier,
      TTransport tTransport) {
    return LookAheadIteratorFactory.thrift(anoaHandler, new TJSONProtocol(tTransport), supplier)
        .asStream();
  }

  /**
   * Stream with 'natural' object-mapping from JsonParser instance.
   *
   * @param recordClass   Thrift record class object
   * @param jacksonParser JsonParser instance from which to read
   * @param <T>           Thrift record type
   */
  static public <T extends TBase> Stream<T> jackson(
      Class<T> recordClass,
      JsonParser jacksonParser) {
    return new ThriftReader<>(recordClass).stream(jacksonParser);
  }

  /**
   * Stream with 'natural' object-mapping from JsonParser instance, with stricter type checking.
   *
   * @param recordClass   Thrift record class object
   * @param jacksonParser JsonParser instance from which to read
   * @param <T>           Thrift record type
   */
  static public <T extends TBase> Stream<T> jacksonStrict(
      Class<T> recordClass,
      JsonParser jacksonParser) {
    return new ThriftReader<>(recordClass).streamStrict(jacksonParser);
  }

  /**
   * Stream with 'natural' object-mapping from JsonParser instance.
   *
   * @param anoaHandler   {@code AnoaHandler} instance to use for exception handling
   * @param recordClass   Thrift record class object
   * @param jacksonParser JsonParser instance from which to read
   * @param <T>           Thrift record type
   * @param <M>           Metadata type
   */
  static public <T extends TBase, M> Stream<Anoa<T, M>> jackson(
      AnoaHandler<M> anoaHandler,
      Class<T> recordClass,
      JsonParser jacksonParser) {
    return new ThriftReader<>(recordClass).stream(anoaHandler, jacksonParser);
  }

  /**
   * Stream with 'natural' object-mapping from JsonParser instance, with stricter type checking.
   *
   * @param anoaHandler   {@code AnoaHandler} instance to use for exception handling
   * @param recordClass   Thrift record class object
   * @param jacksonParser JsonParser instance from which to read
   * @param <T>           Thrift record type
   * @param <M>           Metadata type
   */
  static public <T extends TBase, M> Stream<Anoa<T, M>> jacksonStrict(
      AnoaHandler<M> anoaHandler,
      Class<T> recordClass,
      JsonParser jacksonParser) {
    return new ThriftReader<>(recordClass).streamStrict(anoaHandler, jacksonParser);
  }
}
