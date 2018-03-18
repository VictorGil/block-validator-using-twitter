package net.devaction.socialledger.validatorusingtwitter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.devaction.socialledger.validatorusingtwitter.key.DecryptedKeyPairProvider;
import net.devaction.socialledger.validatorusingtwitter.key.KeyPair;
import net.devaction.socialledger.validatorusingtwitter.token.DecryptedTokenPairProvider;
import net.devaction.socialledger.validatorusingtwitter.token.TokenPair;
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
    
    private static TwitterProvider INSTANCE;
    
    private final KeyPair keyPair;
    private final TokenPair tokenPair;
    
    public static TwitterProvider getInstance(){
        if (INSTANCE == null)
            INSTANCE = new TwitterProvider();
        return INSTANCE;
    }
        
    private TwitterProvider(){        
        keyPair = constructDecryptedKeyPairProvider().provide();
        tokenPair = constructDecryptedTokenPairProvider().provide();        
    }
    
    public Twitter provide(){
        return provide(keyPair, tokenPair);
    }
    
    static DecryptedKeyPairProvider constructDecryptedKeyPairProvider(){
        GlobalProperties properties = GlobalProperties.getInstance();
        
        DecryptedKeyPairProvider decryptedKeyPairProvider;
        if (properties.TWITTER_CONSUMER_API_KEY == null){
            decryptedKeyPairProvider = new DecryptedKeyPairProvider(
                    properties.TWITTER_CONSUMER_API_KEY_ENCRYPTED, properties.TWITTER_CONSUMER_API_SECRET_ENCRYPTED,
                    properties.DECRYPT_PASSWORD_ENV_VAR_NAME);
        } else{            
            decryptedKeyPairProvider = new DecryptedKeyPairProvider(
                    properties.TWITTER_CONSUMER_API_KEY, properties.TWITTER_CONSUMER_API_SECRET); 
        }
        
        return decryptedKeyPairProvider;
    }
    
    static DecryptedTokenPairProvider constructDecryptedTokenPairProvider(){
        GlobalProperties properties = GlobalProperties.getInstance();
        
        DecryptedTokenPairProvider decryptedTokenPairProvider;
        if (properties.TWITTER_ACCESS_TOKEN == null) {
            decryptedTokenPairProvider = new DecryptedTokenPairProvider(
                    properties.TWITTER_ACCESS_TOKEN_ENCRYPTED, properties.TWITTER_ACCESS_TOKEN_SECRET_ENCRYPTED,
                    properties.DECRYPT_PASSWORD_ENV_VAR_NAME);
        } else{
            decryptedTokenPairProvider = new DecryptedTokenPairProvider(
                    properties.TWITTER_ACCESS_TOKEN, properties.TWITTER_ACCESS_TOKEN_SECRET);
        }
        
        return decryptedTokenPairProvider;
    }
    
    static Twitter provide(String consumerApiKey, String consumerApiSecret, 
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
