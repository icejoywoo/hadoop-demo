package serde.thrift;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.AbstractNonblockingServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import thrift.example.Twitter;

public class ThriftServer {
    private static final int SERVER_PORT = 8090;

    static TServer getSimpleServer(TProcessor processor) throws IOException, TTransportException {
        ServerSocket socket = new ServerSocket(SERVER_PORT);
        TServerSocket serverTransport = new TServerSocket(socket);
        TServer.Args tArgs = new TServer.Args(serverTransport);
        tArgs.processor(processor);
        tArgs.transportFactory(new TFramedTransport.Factory());
        tArgs.protocolFactory(new TBinaryProtocol.Factory());
        return new TSimpleServer(tArgs);
    }

    static TServer getNonblockingServer(TProcessor processor) throws IOException, TTransportException {
        TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(SERVER_PORT);
        TNonblockingServer.Args tArgs = new TNonblockingServer.Args(serverTransport);
        tArgs.processor(processor);
        tArgs.transportFactory(new TFramedTransport.Factory());
        tArgs.protocolFactory(new TBinaryProtocol.Factory());
        return new TNonblockingServer(tArgs);
    }

    public static void main(String[] args) throws IOException, TTransportException {


        Twitter.Processor processor = new Twitter.Processor(new TwitterImpl());

        TServer server = getNonblockingServer(processor);

        System.out.println("Running server...");
        server.serve();

    }
}
