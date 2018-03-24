package net.devaction.socialledger.validatorusingtwitter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-16 
 */
public class TwitterProvider{
    private static final Log log = LogFactory.getLog(TwitterProvider.class);
    
    private final Twitter twitter;    

    public TwitterProvider(String twitterConsumerApiKey, String twitterConsumerApiSecret,
            String twitterAccessToken, String twitterAccessTokenSecret){
        Twitter twitter = TwitterFactory.getSingleton();
        
        twitter.setOAuthConsumer(twitterConsumerApiKey, twitterConsumerApiSecret);
        
        AccessToken accessToken = new AccessToken(twitterAccessToken, twitterAccessTokenSecret);
        twitter.setOAuthAccessToken(accessToken);  
        
        this.twitter = twitter;
    }
    
    public Twitter provide(){
        return twitter;
    }    
}
