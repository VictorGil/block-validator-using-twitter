package net.devaction.socialledger.validatorusingtwitter.token;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.devaction.socialledger.validatorusingtwitter.GlobalProperties;

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-23 
 * 
 * This class is used for quick setup when testing
 */
public class DecryptedTokenPairProviderFactory{
    private static final Log log = LogFactory.getLog(DecryptedTokenPairProviderFactory.class);
    
    public static DecryptedTokenPairProvider getInstance(){
        GlobalProperties properties = GlobalProperties.getInstance();
        
        DecryptedTokenPairProvider decryptedTokenPairProvider;
        if (properties.TWITTER_ACCESS_TOKEN == null){
            log.debug("Using encrypted config properties values for the Twitter access token and the Twitter access token secret");
            decryptedTokenPairProvider = new DecryptedTokenPairProvider(
                    properties.TWITTER_ACCESS_TOKEN_ENCRYPTED, properties.TWITTER_ACCESS_TOKEN_SECRET_ENCRYPTED,
                    properties.DECRYPT_PASSWORD_ENV_VAR_NAME);
        } else{
            log.debug("Using unencrypted config properties values for the Twitter access token and the Twitter access token secret");
            decryptedTokenPairProvider = new DecryptedTokenPairProvider(
                    properties.TWITTER_ACCESS_TOKEN, properties.TWITTER_ACCESS_TOKEN_SECRET);
        }
        
        return decryptedTokenPairProvider;        
    }
}
