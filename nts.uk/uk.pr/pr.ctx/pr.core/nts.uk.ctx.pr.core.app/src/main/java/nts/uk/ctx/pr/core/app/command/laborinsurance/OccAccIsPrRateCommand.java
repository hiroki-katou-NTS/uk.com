package nts.uk.ctx.pr.core.app.command.laborinsurance;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class OccAccIsPrRateCommand {
    /**
    * 労災保険事業No
    */
    private int occAccInsurBusNo;
    
    /**
    * 端数区分
    */
    private int fracClass;
    
    /**
    * 事業主負担率
    */
    private BigDecimal empConRatio;

}
