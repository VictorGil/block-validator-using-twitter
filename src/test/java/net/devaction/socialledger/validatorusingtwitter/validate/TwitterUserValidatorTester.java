package net.devaction.socialledger.validatorusingtwitter.validate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.devaction.socialledger.validatorusingtwitter.GlobalProperties;
import net.devaction.socialledger.validatorusingtwitter.TwitterProvider;
import net.devaction.socialledger.validatorusingtwitter.key.DecryptedKeyPairProvider;
import net.devaction.socialledger.validatorusingtwitter.key.KeyPair;
import net.devaction.socialledger.validatorusingtwitter.token.DecryptedTokenPairProvider;
import net.devaction.socialledger.validatorusingtwitter.token.TokenPair;
import net.devaction.socialledger.validatorusingtwitter.validate.TwitterUserValidator;
import twitter4j.Twitter;

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-16 
 */
public class TwitterUserValidatorTester{
    private static final Log log = LogFactory.getLog(TwitterUserValidatorTester.class);

    public static void main(String[] args){
        GlobalProperties properties = GlobalProperties.getInstance();
        
        DecryptedKeyPairProvider decryptedKeyPairProvider = new DecryptedKeyPairProvider(
                properties.TWITTER_CONSUMER_API_KEY_ENCRYPTED, properties.TWITTER_CONSUMER_API_SECRET_ENCRYPTED,
                properties.DECRYPT_PASSWORD_ENV_VAR_NAME);        
        KeyPair keyPair = decryptedKeyPairProvider.provide();
        
        DecryptedTokenPairProvider decryptedTokenPairProvider = new DecryptedTokenPairProvider(
                properties.TWITTER_ACCESS_TOKEN_ENCRYPTED, properties.TWITTER_ACCESS_TOKEN_SECRET_ENCRYPTED,
                properties.DECRYPT_PASSWORD_ENV_VAR_NAME);
        TokenPair tokenPair = decryptedTokenPairProvider.provide();
        
        Twitter twitter = TwitterProvider.provide(keyPair, tokenPair);
        
        TwitterUserValidator twitterUserVerifier = new TwitterUserValidator(twitter);
        log.info("Going to test the TwitterUserVerifier");
        
        boolean isValid = twitterUserVerifier.validate("al0riel"); 
        
        log.info("Exiting");
     }
}
