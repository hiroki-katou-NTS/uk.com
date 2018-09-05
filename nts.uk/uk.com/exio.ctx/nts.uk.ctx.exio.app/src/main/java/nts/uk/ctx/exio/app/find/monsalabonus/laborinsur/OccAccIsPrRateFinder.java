package nts.uk.ctx.exio.app.find.monsalabonus.laborinsur;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccIsPrRateRepository;

@Stateless
/**
* 労災保険料率
*/
public class OccAccIsPrRateFinder
{

    @Inject
    private OccAccIsPrRateRepository finder;

    public OccAccIsPrRateDto getAllOccAccIsPrRate(String hisId){
        return OccAccIsPrRateDto.fromDomain(finder.getOccAccIsPrRateByHisId(hisId));
    }

}
