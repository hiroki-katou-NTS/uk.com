package nts.uk.ctx.pr.core.dom.laborinsurance;

import java.util.List;

/**
* 労災保険料率
*/
public interface OccAccIsPrRateRepository
{

    OccAccIsPrRate getOccAccIsPrRateByHisId(String hisId);
    
    void remove(int occAccInsurBusNo, String hisId);
    
    void remove(String hisId);
    
    void add(List<OccAccInsurBusiBurdenRatio> domain,String hisId);

    void update(List<OccAccInsurBusiBurdenRatio> domain,String newhisId);
}
