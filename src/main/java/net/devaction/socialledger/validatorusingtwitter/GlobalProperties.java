package net.devaction.socialledger.validatorusingtwitter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-16 
 */
public class GlobalProperties{
    private static final Log log = LogFactory.getLog(GlobalProperties.class);
    
    private static final String FILENAME = "application.properties";
    
    private static GlobalProperties INSTANCE;
    
    public final String TWITTER_CONSUMER_API_KEY_ENCRYPTED;
    public final String TWITTER_CONSUMER_API_SECRET_ENCRYPTED;
    public final String DECRYPT_PASSWORD_ENV_VAR_NAME;
    
    public final String TWITTER_CONSUMER_API_KEY;
    public final String TWITTER_CONSUMER_API_SECRET;
    
    public final String TWITTER_ACCESS_TOKEN_ENCRYPTED;
    public final String TWITTER_ACCESS_TOKEN_SECRET_ENCRYPTED;
    
    public final String TWITTER_ACCESS_TOKEN;
    public final String TWITTER_ACCESS_TOKEN_SECRET;    
    
    public static GlobalProperties getInstance(){
        if (INSTANCE == null)
            INSTANCE = new GlobalProperties();
        return INSTANCE;
    }
    
    private GlobalProperties(){
        final Properties appProperties = new Properties();
        final InputStream inputStream = ClassLoader.class.getResourceAsStream("/" + FILENAME);
        
        try{
           appProperties.load(inputStream); 
        } catch(IOException ex) {
            String errMessage = "Unable to load properties file from class path: " + FILENAME;
            log.fatal(errMessage);
            throw new RuntimeException(errMessage, ex);
        }
        TWITTER_CONSUMER_API_KEY_ENCRYPTED = appProperties.getProperty("twitterConsumerApiKeyEncrypted");
        TWITTER_CONSUMER_API_SECRET_ENCRYPTED = appProperties.getProperty("twitterConsumerApiSecretEncrypted");
        DECRYPT_PASSWORD_ENV_VAR_NAME = appProperties.getProperty("decryptPasswordEnvVarName");
        
        TWITTER_CONSUMER_API_KEY = appProperties.getProperty("twitterConsumerApiKey");
        TWITTER_CONSUMER_API_SECRET = appProperties.getProperty("twitterConsumerApiSecret");

        
        TWITTER_ACCESS_TOKEN_ENCRYPTED = appProperties.getProperty("twitterAccessTokenEncrypted");
        TWITTER_ACCESS_TOKEN_SECRET_ENCRYPTED = appProperties.getProperty("twitterAccessTokenSecretEncrypted");
        
        TWITTER_ACCESS_TOKEN = appProperties.getProperty("twitterAccessToken");
        TWITTER_ACCESS_TOKEN_SECRET = appProperties.getProperty("twitterAccessTokenSecret");
        
        
    }    
}
