package net.devaction.socialledger.validatorusingtwitter.validate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.devaction.socialledger.validatorusingtwitter.TwitterProvider;
import net.devaction.socialledger.validatorusingtwitter.key.DecryptedKeyPairProvider;
import net.devaction.socialledger.validatorusingtwitter.key.DecryptedKeyPairProviderFactory;
import net.devaction.socialledger.validatorusingtwitter.key.KeyPair;
import net.devaction.socialledger.validatorusingtwitter.token.DecryptedTokenPairProvider;
import net.devaction.socialledger.validatorusingtwitter.token.DecryptedTokenPairProviderFactory;
import net.devaction.socialledger.validatorusingtwitter.token.TokenPair;
import net.devaction.socialledger.validatorusingtwitter.validate.HashcodeValidator;
import twitter4j.Twitter;

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-16 
 */
public class HashcodeValidatorTester2 {
    private static final Log log = LogFactory.getLog(HashcodeValidatorTester2.class);

    public static void main(String[] args) {
        DecryptedKeyPairProvider decryptedKeyPairProvider = DecryptedKeyPairProviderFactory.getInstance(); 
        
        KeyPair keyPair = decryptedKeyPairProvider.provide();

        DecryptedTokenPairProvider decryptedTokenPairProvider = DecryptedTokenPairProviderFactory.getInstance();
        TokenPair tokenPair = decryptedTokenPairProvider.provide();
        
        TwitterProvider twitterProvider = new TwitterProvider(keyPair.getConsumerApiKey(), 
                keyPair.getConsumerApiSecret(), tokenPair.getAccesToken(), tokenPair.getAccessTokenSecret());
        
        Twitter twitter = twitterProvider.provide();
        
        HashcodeValidator hashcodeVerifier = new HashcodeValidator(twitter);
        log.info("Going to test the HashcodeVerifier");
        
        //hashcodeVerifier.verify("hostia", "al0riel", LocalDateTime.of(2005, 1, 1, 0, 0));
        hashcodeVerifier.validate("hosTIA", "al0riel", ZonedDateTime.of(LocalDateTime.of(2005, 1, 1, 0, 0), ZoneId.systemDefault()));
        //hashcodeVerifier.verify("hosTIA", "alzzXX0riel", LocalDateTime.of(2005, 1, 1, 0, 0));
        
        log.info("Exiting");
     }
}
