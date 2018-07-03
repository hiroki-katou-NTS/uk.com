package nts.uk.ctx.exio.dom.exo.executionlog;

import lombok.AllArgsConstructor;
import lombok.Getter;

import nts.arc.layer.dom.AggregateRoot;


/**
* 外部出力動作管理
*/
@AllArgsConstructor
@Getter
public class ExOutOpMng extends AggregateRoot
{
    
    /**
    * 
    */
    private ExOutProcessingId exOutProId;
    
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
    private NotUseAtr doNotInterrupt;
    
    /**
    * 
    */
    private String proUnit;
    
    /**
    * 
    */
    private ExIoOperationState opCond;
    
    
}
