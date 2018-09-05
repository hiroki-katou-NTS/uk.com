package nts.uk.ctx.sys.assist.app.command.salary;

import lombok.Value;

@Value
public class ValPayDateSetCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 処理区分NO
    */
    private int processCateNo;
    
    private Long version;

}
