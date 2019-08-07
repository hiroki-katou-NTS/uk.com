package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empfunmeminfor;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInfor;

/**
* 厚生年金基金加入期間情報: DTO
*/
@AllArgsConstructor
@Value
public class EmPensionFundPartiPeriodInforDto
{
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 履歴ID
    */
    private String hisId ;
    
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

    
    
    public static EmPensionFundPartiPeriodInforDto fromDomain(EmPensionFundPartiPeriodInfor domain)
    {
        return null;
    }
    
}
