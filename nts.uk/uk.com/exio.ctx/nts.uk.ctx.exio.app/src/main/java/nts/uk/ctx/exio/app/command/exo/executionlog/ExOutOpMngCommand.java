package nts.uk.ctx.exio.app.command.exo.executionlog;

import lombok.Value;


@Value
public class ExOutOpMngCommand
{
    
    /**
    * 
    */
    private String exOutProId;
    
    /**
    * 
    */
    private int proCnt;
    
    /**
    * 
    */
    private int errCnt;
    
    /**
    * 
    */
    private int totalProCnt;
    
    /**
    * 
    */
    private int doNotInterrupt;
    
    /**
    * 
    */
    private String proUnit;
    
    /**
    * 
    */
    private int opCond;
    
    private Long version;

}
