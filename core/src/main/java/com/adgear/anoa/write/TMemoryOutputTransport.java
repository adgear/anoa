package com.adgear.anoa.write;

import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

class TMemoryOutputTransport extends TTransport {

  final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @Override
  public boolean isOpen() {
    return true;
  }

  @Override
  public void open() throws TTransportException {
  }

  @Override
  public void close() {
  }

  @Override
  public int read(byte[] bytes, int i, int i1) throws TTransportException {
    throw new TTransportException("Read not supported");
  }

  @Override
  public void write(byte[] bytes, int i, int i1) throws TTransportException {
    baos.write(bytes, i, i1);
  }
}
