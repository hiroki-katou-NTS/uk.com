package nts.uk.ctx.pr.core.app.find.laborinsurance;

import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsPrRateRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 労災保険料率
*/
public class OccAccIsPrRateFinder
{

    @Inject
    private OccAccIsPrRateRepository finder;

    public nts.uk.ctx.exio.app.find.monsalabonus.laborinsur.OccAccIsPrRateDto getAllOccAccIsPrRate(String hisId){
        return nts.uk.ctx.exio.app.find.monsalabonus.laborinsur.OccAccIsPrRateDto.fromDomain(finder.getOccAccIsPrRateByHisId(hisId));
    }

}
