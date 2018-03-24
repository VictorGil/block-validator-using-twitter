package net.devaction.socialledger.validatorusingtwitter.validate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.Status;

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-16 
 */
public class StringSearcher{
    private static final Log log = LogFactory.getLog(StringSearcher.class);
    
    private static final String DATE_TIME_FORMAT = "EEE yyyy-MM-dd HH:mm:ss.SSS Z (z)";
    private static final DateFormat FORMATTER = new SimpleDateFormat(DATE_TIME_FORMAT);
    
    public static boolean search(String string, List<Status> tweets){
        for (Status tweet : tweets){
            if (tweet.getText().toLowerCase().contains(string.toLowerCase())){
                log.info("String " + string + " was found in tweet: " + tweet.getText());
                log.info("Tweet created: " + FORMATTER.format(tweet.getCreatedAt()));
                return true;
            } else{
                //log.trace("Tweet: " + tweet.getText() + " does not contain the String");
            }
        }
        log.debug("String " + string + " not found among " + tweets.size() + " tweets");
        return false;
    }    
}
