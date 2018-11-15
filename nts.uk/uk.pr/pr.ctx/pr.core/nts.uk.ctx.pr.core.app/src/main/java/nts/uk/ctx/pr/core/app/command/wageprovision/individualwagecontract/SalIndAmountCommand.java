package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class SalIndAmountCommand
{
    
    /**
    * 履歴ID
    */
    private String historyId;
    
    /**
    * 金額
    */
    private int amountOfMoney;
    

}
