package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SalaryInsuColMon;

/**
* 給与社会保険徴収月
*/
@AllArgsConstructor
@Value
public class SalaryInsuColMonDto
{
    

    /**
    * 徴収月
    */
    private int monthCollected;
    
    
    public static SalaryInsuColMonDto fromDomain(SalaryInsuColMon domain)
    {
        return new SalaryInsuColMonDto(domain.getMonthCollected().value);
    }
    
}
