package net.devaction.socialledger.validatorusingtwitter.tweet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-16 
 */
public class TextTweetter{
    private static final Log log = LogFactory.getLog(TextTweetter.class);
    
    private final Twitter twitter;
    
    public TextTweetter(Twitter twitter){
        this.twitter = twitter;
    }
    
    public void tweet(String text){
        synchronized(twitter){
            Status status = null;
            try{
                status = twitter.updateStatus(text);
                log.info("Tweet tweeted, id: " + status.getId() + ". Text: " + status.getText());
            } catch(TwitterException ex){
                String errMessage = "Unable to tweet: " + ex.toString(); 
                log.error(errMessage, ex);
                throw new RuntimeException(errMessage, ex);
            }
        }        
    }
}

