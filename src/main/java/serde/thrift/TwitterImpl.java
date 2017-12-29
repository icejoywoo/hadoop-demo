package serde.thrift;

import org.apache.thrift.TException;

import thrift.example.Location;
import thrift.example.Tweet;
import thrift.example.TweetSearchResult;
import thrift.example.TweetType;
import thrift.example.Twitter;

public class TwitterImpl implements Twitter.Iface {
    @Override
    public String echo(String s) throws TException {
        System.out.println("echo: " + s);
        return s;
    }

    @Override
    public void ping() throws TException {
        System.out.println("pong");
    }

    @Override
    public boolean postTweet(Tweet tweet) throws TException {
        return true;
    }

    @Override
    public TweetSearchResult searchTweets(String query) throws TException {
        System.out.println("searchTweets");
        TweetSearchResult result = new TweetSearchResult();
        Location location = new Location().setLatitude(22.5333333).setLongitude(114.1333333);
        Tweet tweet = new Tweet().setUserId(1).setUserName("Tom").setText("Hello!").setTweetType(TweetType.TWEET);
        tweet.setLoc(location);
        result.addToTweets(tweet);
        return result;
    }

    @Override
    public void zip() throws TException {
        System.out.println("zip");
    }
}
