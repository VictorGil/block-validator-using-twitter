package net.devaction.socialledger.validatorusingtwitter.key;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.devaction.socialledger.validatorusingtwitter.GlobalProperties;

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-23 
 * 
 * this class is used for quick setup when testing
 */
public class DecryptedKeyPairProviderFactory {
    private static final Log log = LogFactory.getLog(DecryptedKeyPairProviderFactory.class);
    
    public static DecryptedKeyPairProvider getInstance(){
        GlobalProperties properties = GlobalProperties.getInstance();
        
        DecryptedKeyPairProvider decryptedKeyPairProvider;
        if (properties.TWITTER_ACCESS_TOKEN == null) {
            decryptedKeyPairProvider = new DecryptedKeyPairProvider(
                    properties.TWITTER_CONSUMER_API_KEY_ENCRYPTED, properties.TWITTER_CONSUMER_API_SECRET_ENCRYPTED,
                    properties.DECRYPT_PASSWORD_ENV_VAR_NAME);
        } else{
            decryptedKeyPairProvider = new DecryptedKeyPairProvider(
                    properties.TWITTER_CONSUMER_API_KEY, properties.TWITTER_CONSUMER_API_SECRET_ENCRYPTED);
        }
        
        return decryptedKeyPairProvider;  
    }    
}
