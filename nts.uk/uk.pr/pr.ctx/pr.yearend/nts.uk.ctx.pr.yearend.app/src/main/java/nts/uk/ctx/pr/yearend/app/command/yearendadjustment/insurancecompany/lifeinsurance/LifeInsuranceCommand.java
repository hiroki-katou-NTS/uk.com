package nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.lifeinsurance;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class LifeInsuranceCommand
{
    /**
    * コード
    */
    private String lifeInsuranceCode;
    
    /**
    * 名称
    */
    private String lifeInsuranceName;
    

}
