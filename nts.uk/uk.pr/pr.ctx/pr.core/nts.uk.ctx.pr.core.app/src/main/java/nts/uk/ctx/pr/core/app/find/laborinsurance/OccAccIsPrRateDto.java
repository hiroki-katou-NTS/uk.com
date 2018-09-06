package nts.uk.ctx.pr.core.app.find.laborinsurance;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBusiBurdenRatio;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsPrRate;

import java.util.List;

/**
* 労災保険料率
*/
@AllArgsConstructor
@Value
public class OccAccIsPrRateDto
{
    /**
    * 履歴ID
    */
    private String hisId;
    /**
     * 各事業負担率
     */
    private List<OccAccInsurBusiBurdenRatio> eachBusBurdenRatio;
    
    
    public static OccAccIsPrRateDto fromDomain(OccAccIsPrRate domain)
    {
        return new OccAccIsPrRateDto(domain.getHisId(), domain.getEachBusBurdenRatio());
    }
    
}
