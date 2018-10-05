package nts.uk.ctx.pr.core.app.find.laborinsurance;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsPrRate;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsPrRateRepository;

@Stateless
/**
* 労災保険料率
*/
public class OccAccIsPrRateFinder {

    @Inject
    private OccAccIsPrRateRepository finder;

    public List<OccAccIsPrRateDto> getAllOccAccIsPrRate(String hisId){
        OccAccIsPrRate temp = finder.getOccAccIsPrRateByHisId(hisId);
        return OccAccIsPrRateDto.fromDomain(temp);
    }

}
