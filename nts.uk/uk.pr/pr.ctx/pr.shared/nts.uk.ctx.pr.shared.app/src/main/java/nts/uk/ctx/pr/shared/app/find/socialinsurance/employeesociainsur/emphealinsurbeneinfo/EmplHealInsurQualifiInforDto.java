package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;

/**
* 社員健康保険資格情報: DTO
*/
@AllArgsConstructor
@Value
public class EmplHealInsurQualifiInforDto
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
    
    
    public static EmplHealInsurQualifiInforDto fromDomain(EmplHealInsurQualifiInfor domain)
    {
        return null;
    }
    
}
