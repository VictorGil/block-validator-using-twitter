package net.devaction.socialledger.validatorusingtwitter.key;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimplePBEConfig;

/**
 * @author Víctor Gil
 * 
 * This class may be used just for testing or troubleshooting
 */
public class EncryptedKeyPairProvider{
    private static final Log log = LogFactory.getLog(EncryptedKeyPairProvider.class);
    
    private final String consumerApiKey;
    private final String consumerApiSecret;
    private final String decryptPasswordEnvVarName;
    
    public EncryptedKeyPairProvider(String consumerApiKey, 
            String consumerApiSecret, 
            String decryptPasswordEnvVarName){
        
        if (consumerApiKey == null || consumerApiKey.length() == 0){
            String errMessage = "The Twitter consumer API key cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }
        this.consumerApiKey = consumerApiKey;
          
        if (consumerApiSecret == null || consumerApiSecret.length() == 0){
            String errMessage = "The Twitter consumer API secret cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }        
        this.consumerApiSecret = consumerApiSecret;
        
        if (decryptPasswordEnvVarName == null || decryptPasswordEnvVarName.length() == 0) {
            String errMessage= "The name of the env var which holds the password cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }
        this.decryptPasswordEnvVarName = decryptPasswordEnvVarName;
    }
    
     public KeyPair provide(){
        SimplePBEConfig config = new SimplePBEConfig(); 
        config.setPassword(getPassword());
        
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor(); 
        encryptor.setConfig(config);
        encryptor.initialize();
        
        String consumerApiKeyEncrypted = encryptor.encrypt(consumerApiKey);
        String consumerApiSecretEncrypted = encryptor.encrypt(consumerApiSecret);
        
        return new KeyPair(consumerApiKeyEncrypted, consumerApiSecretEncrypted);
    }
    
    private String getPassword(){
        String password = System.getenv(decryptPasswordEnvVarName);
        if (password == null || password.length() == 0) {
            String errMsg = "The password cannot be null nor empty";
            log.fatal(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return password;
    }    
}
