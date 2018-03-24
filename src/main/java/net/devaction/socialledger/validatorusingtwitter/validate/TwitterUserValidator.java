package net.devaction.socialledger.validatorusingtwitter.validate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * @author Víctor Gil
 * 
 * since Sun 2018-Mar-18 
 */
public class TwitterUserValidator{
    private static final Log log = LogFactory.getLog(TwitterUserValidator.class);
    private static final String DATE_TIME_PATTERN = "EEE yyyy-MM-dd HH:mm:ss.SSS Z (z)";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    
    private final Twitter twitter;
    private static final LocalDateTime LIMIT_LDT = LocalDateTime.of(2018, 1, 1, 0, 0);
    
    public TwitterUserValidator(Twitter twitter){
        this.twitter = twitter;
    }
    
    public boolean validate(String username){
        User user = null;
        try{
            
            synchronized(twitter){
                user = twitter.showUser(username);
            }
            
            if (user == null){
                log.info("Twitter user " + username + " not found");
                return false;
            }
            //if (!user.isVerified()){
            //    log.info("Twitter user " + username + " is not verified");
            //    return false;
            //}
        } catch(TwitterException ex){
            String errMessage = "Unable to retrieve Twitter user: " + ex.toString(); 
            log.trace(errMessage, ex);
            if (errMessage.contains("does not exist")){
                log.debug("Twitter user " + username + " does not exist");
            } else
                log.error(errMessage, ex);
            return false;
        } 
        
        Date userCreatedAtDate = user.getCreatedAt();
        ZonedDateTime userCreatedAtZoned = userCreatedAtDate.toInstant().atZone(ZoneId.systemDefault());
        LocalDateTime userCreatedAt = userCreatedAtZoned.toLocalDateTime();
        Boolean isVerified = userCreatedAt.isBefore(LIMIT_LDT);
        if (isVerified){
            log.info("Twitter user " + username + " is valid. Created at: " + FORMATTER.format(userCreatedAtZoned));
        }
        return isVerified;        
    }
}
