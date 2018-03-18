package net.devaction.socialledger.bestblocktwitteralgo.tweet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.devaction.socialledger.bestblocktwitteralgo.GlobalProperties;
import net.devaction.socialledger.bestblocktwitteralgo.TwitterProvider;
import net.devaction.socialledger.bestblocktwitteralgo.key.DecryptedKeyPairProvider;
import net.devaction.socialledger.bestblocktwitteralgo.key.KeyPair;
import net.devaction.socialledger.bestblocktwitteralgo.token.DecryptedTokenPairProvider;
import net.devaction.socialledger.bestblocktwitteralgo.token.TokenPair;
import twitter4j.Twitter;

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-16 
 */
public class TextTweeterTester{
    private static final Log log = LogFactory.getLog(TextTweeterTester.class);
    
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
        TextTweetter textTweeter = new TextTweetter(twitter);
        textTweeter.tweet("test1 - please disregard");
        
        //I get an error: Read-only application cannot POST.
    }
}

