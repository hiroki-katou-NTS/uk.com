package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empfunmeminfor;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class EmPensionFundPartiPeriodInforCommand
{
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * 開始日
    */
    private GeneralDate startDate;
    
    /**
    * 終了日
    */
    private GeneralDate endDate;
    
    /**
    * 基金加入員番号
    */

    

}
