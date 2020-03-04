package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class EmplHealInsurQualifiInforCommand
{
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 履歴ID
    */
    private String hisId;
    
    /**
    * 開始日
    */
    private GeneralDate startDate  ;
    
    /**
    * 終了日
    */
    private GeneralDate  endDate;
    

}
