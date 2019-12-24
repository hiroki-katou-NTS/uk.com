package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpInsurStanDate;

/**
* 雇用保険基準日
*/
@AllArgsConstructor
@Value
public class EmpInsurStanDateDto
{

    
    /**
    * 基準日
    */
    private int refeDate;
    
    /**
    * 基準月
    */
    private int baseMonth;
    
    
    public static EmpInsurStanDateDto fromDomain(EmpInsurStanDate domain)
    {
        return new EmpInsurStanDateDto( domain.getEmpInsurRefeDate().value, domain.getEmpInsurBaseMonth().value);
    }
    
}
