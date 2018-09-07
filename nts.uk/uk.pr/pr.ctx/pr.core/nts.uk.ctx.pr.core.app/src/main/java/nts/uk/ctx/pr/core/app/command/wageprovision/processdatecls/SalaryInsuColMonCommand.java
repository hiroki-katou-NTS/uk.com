package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import lombok.Value;

@Value
public class SalaryInsuColMonCommand
{
    
    /**
    * 処理区分NO
    */
    private int processCateNo;
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 徴収月
    */
    private int monthCollected;
    
    private Long version;

}
