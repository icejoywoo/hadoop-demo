package com.github.icejoywoo.hadoop.rpc;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

public class RpcServer {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        RPC.Server server = new RPC.Builder(conf)
                .setProtocol(ClientProtocol.class)
                .setInstance(new ClientProtocolImpl())
                .setBindAddress("0.0.0.0")
                .setPort(8000)
                .setNumHandlers(5)
                .build();
        server.start();
    }
}
