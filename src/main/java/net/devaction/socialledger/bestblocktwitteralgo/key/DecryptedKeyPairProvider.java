package net.devaction.socialledger.bestblocktwitteralgo.key;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimplePBEConfig;

/**
 * @author Víctor Gil
 */
public class DecryptedKeyPairProvider{
    private static final Log log = LogFactory.getLog(DecryptedKeyPairProvider.class);
    
    private final String consumerApiKeyEncrypted;
    private final String consumerApiSecretEncrypted;
    private final String decryptPasswordEnvVarName;
    
    private final String consumerApiKey;
    private final String consumerApiSecret;
    
    
    public DecryptedKeyPairProvider(String consumerApiKeyEncrypted, 
            String consumerApiSecretEncrypted, 
            String decryptPasswordEnvVarName){
        
        if (consumerApiKeyEncrypted == null || consumerApiKeyEncrypted.length() == 0){
            String errMessage = "The encrypted Twitter consumer API key cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }
        this.consumerApiKeyEncrypted = consumerApiKeyEncrypted;
          
        if (consumerApiSecretEncrypted == null || consumerApiSecretEncrypted.length() == 0){
            String errMessage = "The encrypted Twitter consumer API secret cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }        
        this.consumerApiSecretEncrypted = consumerApiSecretEncrypted;
        
        if (decryptPasswordEnvVarName == null || decryptPasswordEnvVarName.length() == 0) {
            String errMessage= "The name of the env var which holds the decryption password cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }
        this.decryptPasswordEnvVarName = decryptPasswordEnvVarName;
        
        consumerApiKey = null;
        consumerApiSecret = null;
        
    }
    
    /**
     * We encourage to use the other constructor method (which uses encryption) 
     * instead of this one.
     */
    public DecryptedKeyPairProvider(String consumerApiKey, String consumerApiSecret){
        if (consumerApiKey == null || consumerApiKey.length() == 0) {
            String errMessage= "The Twitter consumer API key cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }
        this.consumerApiKey = consumerApiKey;

        if (consumerApiSecret == null || consumerApiSecret.length() == 0) {
            String errMessage= "The Twitter consumer API secret cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }
        this.consumerApiSecret = consumerApiSecret;        
        
        this.consumerApiKeyEncrypted = null;
        this.consumerApiSecretEncrypted = null;
        this.decryptPasswordEnvVarName = null;
    }
    
    
    public KeyPair provide(){
        if (consumerApiKey == null){
            SimplePBEConfig config = new SimplePBEConfig(); 
            config.setPassword(getPassword());
        
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor(); 
            encryptor.setConfig(config);
            encryptor.initialize();
        
            String consumerApiKeyDecrypted = encryptor.decrypt(consumerApiKeyEncrypted);
            String consumerApiSecretDecrypted = encryptor.decrypt(consumerApiSecretEncrypted);
        
            return new KeyPair(consumerApiKeyDecrypted, consumerApiSecretDecrypted);
        }
        
        return new KeyPair(consumerApiKey, consumerApiSecret);
        
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
