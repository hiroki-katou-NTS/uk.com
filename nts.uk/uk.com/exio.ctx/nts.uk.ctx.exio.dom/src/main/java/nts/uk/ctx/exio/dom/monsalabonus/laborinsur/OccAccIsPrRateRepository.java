package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import java.util.Optional;
import java.util.List;

/**
* 労災保険料率
*/
public interface OccAccIsPrRateRepository
{

    List<OccAccIsPrRate> getAllOccAccIsPrRate();

    Optional<OccAccIsPrRate> getOccAccIsPrRateById(String ocAcIsPrRtId, String hisId);

    void add(OccAccIsPrRate domain);

    void update(OccAccIsPrRate domain);

    void remove(String ocAcIsPrRtId, String hisId);

}
