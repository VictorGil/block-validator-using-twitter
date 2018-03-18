package net.devaction.socialledger.bestblocktwitteralgo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.devaction.socialledger.bestblocktwitteralgo.key.KeyPair;
import net.devaction.socialledger.validatorusingtwitter.token.TokenPair;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-16 
 */
public class TwitterProvider {
    private static final Log log = LogFactory.getLog(TwitterProvider.class);
    
   
    public static Twitter provide(String consumerApiKey, String consumerApiSecret, 
            String accessTokenString, String accessTokenSecretString){
        
        Twitter twitter = TwitterFactory.getSingleton();
        
        twitter.setOAuthConsumer(consumerApiKey, consumerApiSecret);
        
        AccessToken accessToken = new AccessToken(accessTokenString, accessTokenSecretString);
        twitter.setOAuthAccessToken(accessToken);
        
        return twitter;
    }
    
    public static Twitter provide(KeyPair keyPair, TokenPair tokenPair){        
        return provide(keyPair.getConsumerApiKey(), keyPair.getConsumerApiSecret(), 
                tokenPair.getAccesToken(), tokenPair.getAccessTokenSecret());
    }
}


