package nts.uk.ctx.exio.app.find.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccInsurBusiBurdenRatio;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccIsPrRate;

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
