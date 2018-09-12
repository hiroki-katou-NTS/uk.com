package nts.uk.ctx.pr.core.app.find.laborinsurance;

import nts.uk.ctx.pr.core.dom.laborinsurance.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class AccInsurPreRateFinder {
    @Inject
    private OccAccIsPrRateRepository finder;

    @Inject
    private OccAccInsurBusFinder occAccInsurBusFinder;

    @Inject
    private WorkersComInsurService workersComInsurService;


    public List<AccInsurPreRateDto> getAccInsurPreRate(String hisId) {
        String companyId = AppContexts.user().companyId();
        Optional<OccAccInsurBus> occAccInsurBus =  workersComInsurService.getOccAccInsurBus(companyId);
        OccAccIsPrRate occAccIsPrRate = finder.getOccAccIsPrRateByHisId(hisId);
        return AccInsurPreRateDto.fromDomain(OccAccInsurBusDto.fromDomain(occAccInsurBus.get()),OccAccIsPrRateDto.fromDomain(occAccIsPrRate));
    }
}
