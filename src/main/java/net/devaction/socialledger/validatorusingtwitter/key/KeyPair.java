package net.devaction.socialledger.validatorusingtwitter.key;

/**
 * @author Víctor Gil
 */
public class KeyPair{

    private String consumerApiKey;
    private String consumerApiSecret;
    
    public KeyPair(String consumerApiKey, String consumerApiSecret){
        this.consumerApiKey = consumerApiKey;
        this.consumerApiSecret = consumerApiSecret;        
    } 
    
    public String getConsumerApiKey() {
        return consumerApiKey;
    }

    public String getConsumerApiSecret() {
        return consumerApiSecret;
    }   
    
    @Override
    public String toString(){
       return "{consumerApiKey: " + consumerApiKey + ",\nconsumerApiSecret: " + consumerApiSecret + "}"; 
    }
}
