package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import lombok.Value;

@Value
public class EmpTiedProYearCommand
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
    * EMPLOYMENT_CODE
    */
    private String employmentCode;
    
    private Long version;

}
