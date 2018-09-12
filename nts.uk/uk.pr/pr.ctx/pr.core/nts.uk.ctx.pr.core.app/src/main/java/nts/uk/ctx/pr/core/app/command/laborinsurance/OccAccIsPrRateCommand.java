package nts.uk.ctx.pr.core.app.command.laborinsurance;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class OccAccIsPrRateCommand
{
    /**
    * 労災保険事業No
    */
    private int occAccIsBusNo;
    
    /**
    * 端数区分
    */
    private int fracClass;
    
    /**
    * 事業主負担率
    */
    private Long empConRatio;



}
