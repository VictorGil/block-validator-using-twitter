package net.devaction.socialledger.bestblocktwitteralgo.compare;

import net.devaction.socialledger.bestblockextradataselector.BestExtradata;
import static net.devaction.socialledger.bestblockextradataselector.BestExtradata.EXTRADATA1;
import static net.devaction.socialledger.bestblockextradataselector.BestExtradata.EXTRADATA2;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.devaction.socialledger.bestblockextradataselector.BestExtradataSelector;

/**
 * @author Víctor Gil
 * 
 * since Sun 4-MAR-2018 
 */
public class BestExtradataBasedOnTwitterSelector implements BestExtradataSelector{
    private static final Log log = LogFactory.getLog(BestExtradataBasedOnTwitterSelector.class);
            
    public static final Charset EXTRA_DATA_CHARSET = StandardCharsets.US_ASCII;
    
    private static BestExtradataSelector INSTANCE;
    
    public static BestExtradataSelector getInstance(){
        if (INSTANCE == null)
            INSTANCE = new BestExtradataBasedOnTwitterSelector();
        return INSTANCE;
    }
    
    public BestExtradata select(byte[] extradata1, byte[] extradata2){
        String extradata1Str = new String(extradata1, EXTRA_DATA_CHARSET).trim();
        String extradata2Str = new String(extradata2, EXTRA_DATA_CHARSET).trim();
        
        return select(extradata1Str, extradata2Str);
    }
    
    BestExtradata select(String extraData1, String extraData2){
        if (!extraData1.substring(0, 7).equalsIgnoreCase("twitter")){
            String errMessage = "Unsupported social network " + extraData1.substring(0, 7);
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        } 
        if (!extraData2.substring(0, 7).equalsIgnoreCase("twitter")){
            String errMessage = "Unsupported social network " + extraData2.substring(0, 7);
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }                 
            
        return EXTRADATA1;
    }
}
