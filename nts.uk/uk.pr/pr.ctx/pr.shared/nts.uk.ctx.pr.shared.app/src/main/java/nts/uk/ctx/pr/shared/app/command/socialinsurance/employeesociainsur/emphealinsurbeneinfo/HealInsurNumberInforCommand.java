package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class HealInsurNumberInforCommand
{
    
    /**
    * 健保得喪期間履歴ID
    */
    private String historyId;
    
    /**
    * 介護保険番号
    */
    private String careInsurNumber;
    
    /**
    * 健康保険番号
    */
    private String healInsNumber;
    

}
