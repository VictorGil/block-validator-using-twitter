package net.devaction.socialledger.validatorusingtwitter.verify;

import java.time.LocalDateTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.devaction.socialledger.validatorusingtwitter.GlobalProperties;
import net.devaction.socialledger.validatorusingtwitter.TwitterProvider;
import net.devaction.socialledger.validatorusingtwitter.key.DecryptedKeyPairProvider;
import net.devaction.socialledger.validatorusingtwitter.key.KeyPair;
import net.devaction.socialledger.validatorusingtwitter.token.DecryptedTokenPairProvider;
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
        
        HashcodeValidator hashcodeVerifier = new HashcodeValidator(twitter);
        log.info("Going to test the HashcodeVerifier");
        
        //hashcodeVerifier.verify("hostia", "al0riel", LocalDateTime.of(2005, 1, 1, 0, 0));
        hashcodeVerifier.validate("hosTIA", "al0riel", LocalDateTime.of(2005, 1, 1, 0, 0));
        //hashcodeVerifier.verify("hosTIA", "alzzXX0riel", LocalDateTime.of(2005, 1, 1, 0, 0));
        
        log.info("Exiting");
     }
}
