package nts.uk.ctx.pr.core.app.find.laborinsurance;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBus;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsPrRate;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsPrRateRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccidentInsurService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AccInsurPreRateFinder {

    @Inject
    private OccAccIsPrRateRepository finder;

    @Inject
    private OccAccidentInsurService workersComInsurService;

    public List<AccInsurPreRateDto> getAccInsurPreRate(String hisId) {
        String companyId = AppContexts.user().companyId();
        Optional<OccAccInsurBus> occAccInsurBus =  workersComInsurService.getOccAccInsurBus(companyId);
        OccAccIsPrRate occAccIsPrRate = finder.getOccAccIsPrRateByHisId(companyId, hisId);
        return AccInsurPreRateDto.fromDomain(OccAccInsurBusDto.fromDomain(occAccInsurBus.get()),OccAccIsPrRateDto.fromDomain(occAccIsPrRate));
    }
}
