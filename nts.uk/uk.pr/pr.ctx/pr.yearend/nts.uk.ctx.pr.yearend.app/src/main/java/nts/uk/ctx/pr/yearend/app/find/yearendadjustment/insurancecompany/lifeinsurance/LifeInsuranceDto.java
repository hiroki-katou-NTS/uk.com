package nts.uk.ctx.pr.yearend.app.find.yearendadjustment.insurancecompany.lifeinsurance;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance.LifeInsurance;

/**
* 生命保険情報: DTO
*/
@AllArgsConstructor
@Value
public class LifeInsuranceDto
{
    /**
    * コード
    */
    private String lifeInsuranceCode;
    
    /**
    * 名称
    */
    private String lifeInsuranceName;
    
    
    public static LifeInsuranceDto fromDomain(LifeInsurance domain)
    {
        return new LifeInsuranceDto( domain.getLifeInsuranceCode().v(), domain.getLifeInsuranceName().v());
    }
    
}
