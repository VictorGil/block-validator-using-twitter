package net.devaction.socialledger.validatorusingtwitter.validate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.devaction.socialledger.validatorusingtwitter.TwitterProvider;
import net.devaction.socialledger.validatorusingtwitter.key.DecryptedKeyPairProvider;
import net.devaction.socialledger.validatorusingtwitter.key.DecryptedKeyPairProviderFactory;
import net.devaction.socialledger.validatorusingtwitter.key.KeyPair;
import net.devaction.socialledger.validatorusingtwitter.token.DecryptedTokenPairProvider;
import net.devaction.socialledger.validatorusingtwitter.token.DecryptedTokenPairProviderFactory;
import net.devaction.socialledger.validatorusingtwitter.token.TokenPair;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-16 
 */
public class HashcodeValidatorTester {
    private static final Log log = LogFactory.getLog(HashcodeValidatorTester.class);

    public static void main(String[] args) {
        DecryptedKeyPairProvider decryptedKeyPairProvider = DecryptedKeyPairProviderFactory.getInstance();        
        KeyPair keyPair = decryptedKeyPairProvider.provide();

        DecryptedTokenPairProvider decryptedTokenPairProvider = DecryptedTokenPairProviderFactory.getInstance();
        TokenPair tokenPair = decryptedTokenPairProvider.provide();
        
        TwitterProvider twitterProvider = new TwitterProvider(keyPair.getConsumerApiKey(), 
                keyPair.getConsumerApiSecret(), tokenPair.getAccesToken(), tokenPair.getAccessTokenSecret());
        
        Twitter twitter = twitterProvider.provide();
        
        //it seems that 200 is the highest number you can get
        Paging paging = new Paging(1, 200);
        //Paging paging = new Paging(1);
        //paging.setMaxId(Long.MAX_VALUE); //this returns 400:The request was invalid. code - 214
        //paging.setSinceId(1L); //this returns only 20
        List<Status> statuses = null;
        log.info("Going to call Twitter API");
        try{
            statuses = twitter.getUserTimeline("al0riel", paging);
            //statuses = twitter.getUserTimeline("al0riel");
        } catch(TwitterException ex){
            String errMessage = "Unable to tweet: " + ex.toString(); 
            log.error(errMessage, ex);
            throw new RuntimeException(errMessage, ex);
        }
        
        log.info("Number of tweets in the response: " + statuses.size());
        
        DateFormat formatter = new SimpleDateFormat("EEE yyyy-MM-dd HH:mm:ss.SSS Z (z)");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("EEE yyyy-MM-dd HH:mm:ss.SSS Z (z)");
        
        /*
        for(Status status : statuses){
            Date createdAt = status.getCreatedAt();
            log.info("Text of the tweet: " + status.getText());
            log.info("Created at (java.util.Date): " + formatter.format(createdAt));
            Instant instant = createdAt.toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            //ZonedDateTime zdt = instant.atZone(ZoneId.of("Asia/Kuala_Lumpur"));
            log.info("Created at (java.time.ZonedDateTime): " + formatter2.format(zdt));
        }
        */
        log.info("\nExiting");
     }
}
