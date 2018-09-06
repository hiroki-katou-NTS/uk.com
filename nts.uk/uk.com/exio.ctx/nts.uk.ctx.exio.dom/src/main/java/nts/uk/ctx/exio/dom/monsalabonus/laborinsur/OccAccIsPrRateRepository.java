package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

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
