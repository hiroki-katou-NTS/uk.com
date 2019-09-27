package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInfor;

/**
* 健保組合情報: DTO
*/
@AllArgsConstructor
@Value
public class HealthCarePortInforDto
{
    
    /**
    * 履歴ID
    */
    private String historyId;
    
    /**
    * 健康保険組合番号
    */
    private int healInsurUnionNumber;
    
    
    public static HealthCarePortInforDto fromDomain(HealthCarePortInfor domain)
    {
        return null;
    }
    
}
