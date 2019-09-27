package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntell;

/**
* 健保組合加入期間情報: DTO
*/
@AllArgsConstructor
@Value
public class HealInsurPortPerIntellDto
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
    private GeneralDate startDate ;
    
    /**
    * 終了日
    */
    private GeneralDate endDate ;
    
    /**
    * 健康保険組合番号
    */
//    private int ;
    
    
    public static HealInsurPortPerIntellDto fromDomain(HealInsurPortPerIntell domain)
    {
        return null;
    }
    
}
