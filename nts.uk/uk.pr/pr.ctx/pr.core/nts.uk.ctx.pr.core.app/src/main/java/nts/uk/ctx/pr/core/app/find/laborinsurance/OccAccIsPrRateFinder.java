package nts.uk.ctx.pr.core.app.find.laborinsurance;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsPrRate;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsPrRateRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
* 労災保険料率
*/
public class OccAccIsPrRateFinder {

    @Inject
    private OccAccIsPrRateRepository finder;

    public List<OccAccIsPrRateDto> getAllOccAccIsPrRate(String hisId){
    	String cId = AppContexts.user().companyId();
        OccAccIsPrRate temp = finder.getOccAccIsPrRateByHisId(cId, hisId);
        return OccAccIsPrRateDto.fromDomain(temp);
    }

}
