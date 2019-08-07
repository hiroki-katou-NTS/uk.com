package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;

/**
* 社員社保事業所所属履歴: DTO
*/
@AllArgsConstructor
@Value
public class EmpCorpHealthOffHisDto
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
    
    
    public static EmpCorpHealthOffHisDto fromDomain(EmpCorpHealthOffHis domain)
    {
        return null;
    }
    
}
