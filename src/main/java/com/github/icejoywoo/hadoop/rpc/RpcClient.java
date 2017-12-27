package com.github.icejoywoo.hadoop.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.ProtocolProxy;
import org.apache.hadoop.ipc.RPC;

public class RpcClient {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        InetSocketAddress addr = new InetSocketAddress("localhost", 8000);
        ClientProtocol proxy = RPC.getProxy(ClientProtocol.class, ClientProtocol.versionID, addr, conf);
        int result = proxy.add(1, 2);
        System.out.println(result);
        System.out.println(proxy.echo("hello"));
    }
}
