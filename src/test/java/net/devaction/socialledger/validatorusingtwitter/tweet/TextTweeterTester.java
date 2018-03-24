package net.devaction.socialledger.validatorusingtwitter.tweet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.devaction.socialledger.validatorusingtwitter.TwitterProvider;
import net.devaction.socialledger.validatorusingtwitter.key.DecryptedKeyPairProvider;
import net.devaction.socialledger.validatorusingtwitter.key.DecryptedKeyPairProviderFactory;
import net.devaction.socialledger.validatorusingtwitter.key.KeyPair;
import net.devaction.socialledger.validatorusingtwitter.token.DecryptedTokenPairProvider;
import net.devaction.socialledger.validatorusingtwitter.token.DecryptedTokenPairProviderFactory;
import net.devaction.socialledger.validatorusingtwitter.token.TokenPair;
import net.devaction.socialledger.validatorusingtwitter.tweet.TextTweetter;

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-16 
 */
public class TextTweeterTester{
    private static final Log log = LogFactory.getLog(TextTweeterTester.class);
    
    public static void main(String[] args) {
        DecryptedKeyPairProvider decryptedKeyPairProvider = DecryptedKeyPairProviderFactory.getInstance(); 
                
        KeyPair keyPair = decryptedKeyPairProvider.provide();

        DecryptedTokenPairProvider decryptedTokenPairProvider = DecryptedTokenPairProviderFactory.getInstance();
        TokenPair tokenPair = decryptedTokenPairProvider.provide();
        
        TwitterProvider twitterProvider = new TwitterProvider(keyPair.getConsumerApiKey(), 
                keyPair.getConsumerApiSecret(), tokenPair.getAccesToken(), tokenPair.getAccessTokenSecret());
        TextTweetter textTweeter = new TextTweetter(twitterProvider.provide());
        textTweeter.tweet("test1 - please disregard");
    }
}

