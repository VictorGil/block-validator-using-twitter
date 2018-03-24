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

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-16 
 */
public class HashcodeValidatorTester2{
    private static final Log log = LogFactory.getLog(HashcodeValidatorTester2.class);

    public static void main(String[] args) {
        DecryptedKeyPairProvider decryptedKeyPairProvider = DecryptedKeyPairProviderFactory.getInstance();         
        KeyPair keyPair = decryptedKeyPairProvider.provide();

        DecryptedTokenPairProvider decryptedTokenPairProvider = DecryptedTokenPairProviderFactory.getInstance();
        TokenPair tokenPair = decryptedTokenPairProvider.provide();
        
        TwitterProvider twitterProvider = new TwitterProvider(keyPair.getConsumerApiKey(), 
                keyPair.getConsumerApiSecret(), tokenPair.getAccesToken(), tokenPair.getAccessTokenSecret());
        
        HashcodeValidator hashcodeVerifier = new HashcodeValidator(twitterProvider.provide());
        log.info("Going to test the HashcodeVerifier");
        
        String blockHashcode = "ddf2523f1717b2027d322c5f237d7729967f9082a41a70a93c085b6445d1c538";
        hashcodeVerifier.validate(blockHashcode, "devactionnet", ZonedDateTime.of(LocalDateTime.of(2005, 1, 1, 0, 0), ZoneId.systemDefault()));
        
        log.info("Exiting");
     }
}
