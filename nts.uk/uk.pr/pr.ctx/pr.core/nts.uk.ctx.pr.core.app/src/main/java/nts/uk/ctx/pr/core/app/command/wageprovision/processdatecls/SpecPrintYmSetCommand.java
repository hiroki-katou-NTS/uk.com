package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import lombok.Value;

@Value
public class SpecPrintYmSetCommand
{
    
    /**
    * CID
    */
    private String cid;
    
    /**
    * PROCESS_CATE_NO
    */
    private int processCateNo;
    
    /**
    * PROCESS_DATE
    */
    private int processDate;
    
    /**
    * PRINT_DATE
    */
    private int printDate;
    
    private Long version;

}
