package nts.uk.ctx.sys.assist.app.command.salary;

import lombok.Value;

@Value
public class EmpInsurStanDateCommand
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
    * 基準日
    */
    private int refeDate;
    
    /**
    * 基準月
    */
    private int baseMonth;
    
    private Long version;

}
