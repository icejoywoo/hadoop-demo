package com.github.icejoywoo.hadoop.rpc;

import java.io.IOException;

import org.apache.hadoop.ipc.VersionedProtocol;

interface ClientProtocol extends VersionedProtocol {
    public static final long versionID = 1L;
    String echo(String value) throws IOException;
    int add(int v1, int v2) throws IOException;
}
