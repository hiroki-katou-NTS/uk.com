package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import lombok.Value;

@Value
public class ProcessInformationCommand
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
    * DEPRECAT_CATE
    */
    private int deprecatCate;
    
    /**
    * PROCESS_DIVISION_NAME
    */
    private String processCls;
    


}
