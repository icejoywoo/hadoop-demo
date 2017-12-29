package serde.thrift;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TIOStreamTransport;

import thrift.example.Location;
import thrift.example.Tweet;
import thrift.example.TweetType;

public class ThriftDemo {
    public static void main(String[] args) throws TException, IOException {

        {
            Location location = new Location().setLatitude(22.5333333).setLongitude(114.1333333);
            Tweet tweet = new Tweet().setUserId(1).setUserName("Tom").setText("Hello!").setTweetType(TweetType.TWEET);
            tweet.setLoc(location);

            TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory());
            TDeserializer deserializer = new TDeserializer(new TBinaryProtocol.Factory());
            Tweet out = new Tweet();
            deserializer.deserialize(out, serializer.serialize(tweet));
            System.out.println(out);
        }

        String datafile = "thrift.dat";

        {
            Location location = new Location().setLatitude(22.5333333).setLongitude(114.1333333);
            Tweet tweet = new Tweet().setUserId(1).setUserName("Tom").setText("Hello!").setTweetType(TweetType.TWEET);
            tweet.setLoc(location);
            System.out.println(tweet);

            FileOutputStream fos = new FileOutputStream(new File(datafile));
            tweet.write(new TBinaryProtocol(new TIOStreamTransport(fos)));
            fos.close();
        }

        {
            FileInputStream fis = new FileInputStream(new File(datafile));
            Tweet tweet = new Tweet();
            tweet.read(new TBinaryProtocol(new TIOStreamTransport(fis)));

            System.out.println(tweet);
        }
    }
}
