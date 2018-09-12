package nts.uk.ctx.pr.core.app.find.laborinsurance;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.pr.core.app.find.laborinsurance.OccAccIsPrRateDto;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsPrRate;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsPrRateRepository;

import java.util.ArrayList;
import java.util.List;

@Stateless
/**
* 労災保険料率
*/
public class OccAccIsPrRateFinder
{

    @Inject
    private OccAccIsPrRateRepository finder;

    public List<OccAccIsPrRateDto> getAllOccAccIsPrRate(String hisId){
        OccAccIsPrRate temp = finder.getOccAccIsPrRateByHisId(hisId);
        if(temp.getEachBusBurdenRatio()==null){
            return null;
        }
        return OccAccIsPrRateDto.fromDomain(temp);
    }

}
