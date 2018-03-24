package net.devaction.socialledger.validatorusingtwitter.token;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimplePBEConfig;

/**
 * @author Víctor Gil
 * 
 * since Fri 2018-Mar-16 
 */
public class DecryptedTokenPairProvider{
    private static final Log log = LogFactory.getLog(DecryptedTokenPairProvider.class);
    
    private final String accessTokenEncrypted;
    private final String accessTokenSecretEncrypted;
    private final String decryptPasswordEnvVarName;
    
    private final String accessToken;
    private final String accessTokenSecret;

    public DecryptedTokenPairProvider(String accessTokenEncrypted, String accessTokenSecretEncrypted, 
            String decryptPasswordEnvVarName){
        
        if (accessTokenEncrypted == null || accessTokenEncrypted.length() == 0){
            String errMessage = "The encrypted Twitter access token cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }
        this.accessTokenEncrypted = accessTokenEncrypted;
        
        if (accessTokenSecretEncrypted == null || accessTokenSecretEncrypted.length() == 0){
            String errMessage = "The encrypted Twitter access token secret cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }        
        this.accessTokenSecretEncrypted = accessTokenSecretEncrypted;
        
        if (decryptPasswordEnvVarName == null || decryptPasswordEnvVarName.length() == 0){
            String errMessage= "The name of the env var which holds the decryption password cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }
        this.decryptPasswordEnvVarName = decryptPasswordEnvVarName;
        
        accessToken = null;
        accessTokenSecret = null;
    }
    
    public DecryptedTokenPairProvider(String accessToken, String accessTokenSecret){
        if (accessToken == null || accessToken.length() == 0){
            String errMessage = "The Twitter access token cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }
        this.accessToken = accessToken;
        
        if (accessTokenSecret == null || accessTokenSecret.length() == 0){
            String errMessage = "The Twitter access token secret cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }        
        this.accessTokenSecret = accessTokenSecret;
        
        accessTokenEncrypted = null;        
        accessTokenSecretEncrypted = null;        
        decryptPasswordEnvVarName = null;        
    }
    
    public TokenPair provide(){
        if (accessToken == null){
            SimplePBEConfig config = new SimplePBEConfig(); 
            config.setPassword(getPassword());
        
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor(); 
            encryptor.setConfig(config);
            encryptor.initialize();
        
            String accessTokenDecrypted = encryptor.decrypt(accessTokenEncrypted);
            String accessTokenSecretDecrypted = encryptor.decrypt(accessTokenSecretEncrypted);
        
            return new TokenPair(accessTokenDecrypted, accessTokenSecretDecrypted);
        }        
        return new TokenPair(accessToken, accessTokenSecret);
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
