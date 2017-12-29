package serde.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import thrift.example.TweetSearchResult;
import thrift.example.Twitter;

public class ThriftClient {
    public static void main(String[] args) {
        final String SERVER_IP = "localhost";
        final int SERVER_PORT = 8090;
        final int TIMEOUT = 30000;

        TTransport transport = null;
        try {
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            TProtocol protocol = new TBinaryProtocol(new TFramedTransport(transport));
            Twitter.Client client = new Twitter.Client(protocol);
            transport.open();
            String result = client.echo("hello");
            System.out.println("Thrify client result =: " + result);

            client.ping();

            TweetSearchResult searchResult = client.searchTweets("dummy");
            System.out.println("searchTweets result =:" + searchResult);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }
}
