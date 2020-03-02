package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import lombok.Value;

@Value
public class SociInsuStanDateCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 処理区分NO
    */
    private int processCateNo;
    
    /**
    * 基準月
    */
    private int baseMonth;
    
    /**
    * 基準年
    */
    private int baseYear;
    
    /**
    * 基準日
    */
    private int refeDate;
    
    private Long version;

}
