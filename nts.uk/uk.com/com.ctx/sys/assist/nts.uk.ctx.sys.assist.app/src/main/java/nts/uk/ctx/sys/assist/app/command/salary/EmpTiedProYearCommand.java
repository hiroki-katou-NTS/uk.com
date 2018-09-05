package nts.uk.ctx.sys.assist.app.command.salary;

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
