package net.devaction.socialledger.validatorusingtwitter.validate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.devaction.socialledger.validatorusingtwitter.TwitterProvider;
import net.devaction.socialledger.validatorusingtwitter.key.DecryptedKeyPairProvider;
import net.devaction.socialledger.validatorusingtwitter.key.DecryptedKeyPairProviderFactory;
import net.devaction.socialledger.validatorusingtwitter.key.KeyPair;
import net.devaction.socialledger.validatorusingtwitter.token.DecryptedTokenPairProvider;
import net.devaction.socialledger.validatorusingtwitter.token.DecryptedTokenPairProviderFactory;
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
        DecryptedKeyPairProvider decryptedKeyPairProvider = DecryptedKeyPairProviderFactory.getInstance();        
        KeyPair keyPair = decryptedKeyPairProvider.provide();

        DecryptedTokenPairProvider decryptedTokenPairProvider = DecryptedTokenPairProviderFactory.getInstance();
        TokenPair tokenPair = decryptedTokenPairProvider.provide();
        
        TwitterProvider twitterProvider = new TwitterProvider(keyPair.getConsumerApiKey(), 
                keyPair.getConsumerApiSecret(), tokenPair.getAccesToken(), tokenPair.getAccessTokenSecret());
        
        Twitter twitter = twitterProvider.provide();
        
        TwitterUserValidator twitterUserVerifier = new TwitterUserValidator(twitter);
        log.info("Going to test the TwitterUserVerifier");
        
        boolean isValid = twitterUserVerifier.validate("al0riel"); 
        
        log.info("Exiting");
     }
}
