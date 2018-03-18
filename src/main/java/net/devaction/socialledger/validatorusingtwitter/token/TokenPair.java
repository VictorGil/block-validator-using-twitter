package net.devaction.socialledger.validatorusingtwitter.token;

/**
 * @author Víctor Gil
 */
public class TokenPair{
    private String accesToken;
    private String accessTokenSecret;
    
    public TokenPair(String accessToken, String accessTokenSecret){
        this.accesToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;        
    } 

    public String getAccesToken() {
        return accesToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }
    
    @Override
    public String toString(){
       return "{accessToken: " + accesToken + ", accessTokenSecret: " + accessTokenSecret + "}"; 
    }
}
