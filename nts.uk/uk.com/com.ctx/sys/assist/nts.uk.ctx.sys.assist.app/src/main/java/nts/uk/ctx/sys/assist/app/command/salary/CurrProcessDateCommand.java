package nts.uk.ctx.sys.assist.app.command.salary;

import lombok.Value;

@Value
public class CurrProcessDateCommand
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
    * GIVE_CURR_TREAT_YEAR
    */
    private int giveCurrTreatYear;
    
    private Long version;

}
