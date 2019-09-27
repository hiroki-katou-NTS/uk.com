package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class HealInsurPortPerIntellCommand
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
    private GeneralDate startDate;
    
    /**
    * 終了日
    */
    private GeneralDate endDate;
    
    /**
    * 健康保険組合番号
    */
    private int healInsurUnionNumber;
    

}
