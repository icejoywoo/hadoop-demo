package com.github.icejoywoo.hadoop.rpc;

import java.io.IOException;

import org.apache.hadoop.ipc.ProtocolSignature;

public class ClientProtocolImpl implements ClientProtocol {
    @Override
    public String echo(String value) throws IOException {
        return value;
    }

    @Override
    public int add(int v1, int v2) throws IOException {
        return v1 + v2;
    }

    @Override
    public long getProtocolVersion(String protocol, long clientVersion) throws IOException {
        return ClientProtocol.versionID;
    }

    @Override
    public ProtocolSignature getProtocolSignature(String protocol, long clientVersion, int clientMethodsHash)
            throws IOException {
        return new ProtocolSignature(ClientProtocol.versionID, null);
    }
}
