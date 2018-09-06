package nts.uk.ctx.pr.core.dom.laborinsurance;

import java.util.List;
import java.util.Optional;

/**
* 労災保険料率
*/
public interface OccAccIsPrRateRepository
{

    OccAccIsPrRate getOccAccIsPrRateByHisId(String hisId);
    void remove(int occAccInsurBusNo, String hisId);

}
