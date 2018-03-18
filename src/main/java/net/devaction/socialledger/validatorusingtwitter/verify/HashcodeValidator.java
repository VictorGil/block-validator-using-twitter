package net.devaction.socialledger.validatorusingtwitter.verify;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.devaction.socialledger.validatorusingtwitter.TwitterProvider;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-16 
 */
public class HashcodeValidator{
    //private static final String DATE_TIME_FORMAT = "EEE yyyy-MM-dd HH:mm:ss.SSS Z (z)";
    private static final Log log = LogFactory.getLog(HashcodeValidator.class);
    
    private final Twitter twitter;
    
    private static final int PAGE_SIZE = 200;
    //private static final int MAX_PAGE = 10;
    private static final int MAX_PAGE = 1;
    //(15 * 60) / 1500 = 0.6 seconds
    private static final int MILLIS_TO_SLEEP = 600;
    
    private static final String DATE_TIME_PATTERN = "EEE yyyy-MM-dd HH:mm:ss.SSS Z (z)";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
 
    private static HashcodeValidator INSTANCE;
    
    public static HashcodeValidator getInstance(){
        if (INSTANCE == null){
            INSTANCE= new  HashcodeValidator(TwitterProvider.getInstance().provide());
        }
        return INSTANCE;
    }
    
    public HashcodeValidator(Twitter twitter){
        this.twitter = twitter;
    }
    
    public boolean validate(String blockHashcode, String twitterUsername, LocalDateTime limitDatetime){
        
        LocalDateTime oldestTweetDateTime = LocalDateTime.now();
        int pageCount = 1;
        List<Status> statuses = null;
        
        do{ 
            Paging paging = new Paging(pageCount, PAGE_SIZE);
            try{
                log.info("Going to call Twitter API to get the timeline of " + twitterUsername);
                
                synchronized(twitter){
                    statuses = twitter.getUserTimeline(twitterUsername, paging);
                }
                
                log.debug("Number of tweets retrieved: " + statuses.size());
                
                Date oldestTweetDate = statuses.get(statuses.size() - 1).getCreatedAt();
                ZonedDateTime oldestTweetZonedDateTime = oldestTweetDate.toInstant().atZone(ZoneId.systemDefault()); 
                oldestTweetDateTime = oldestTweetZonedDateTime.toLocalDateTime();
                log.debug("\"Created at\" date time of oldest tweet retrieved so far: " + FORMATTER.format(oldestTweetZonedDateTime));
                
            } catch(TwitterException ex){
                String errMessage = "Unable to retrieve tweets: " + ex.toString(); 
                if (errMessage.contains("does not exist")){
                    log.debug("Twitter user " + twitterUsername + " does not exist");
                } else
                    log.error(errMessage, ex);
                return false;
            }
            
            
            if (StringSearcher.search(blockHashcode, statuses))
                return true;
                
            try{
                //(15 min * 60 sec/min) / 1500 = 0.6 seconds
                Thread.sleep(MILLIS_TO_SLEEP);
            } catch(InterruptedException ex){
                log.error("Thread interrupted while sleeping! " + ex.toString(), ex);
            }
            pageCount++;
        }
        while(oldestTweetDateTime.isAfter(limitDatetime) && pageCount <= MAX_PAGE);
        
        return false;
    }
}


