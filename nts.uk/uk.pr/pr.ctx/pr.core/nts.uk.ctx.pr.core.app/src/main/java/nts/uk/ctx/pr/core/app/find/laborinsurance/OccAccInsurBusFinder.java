package nts.uk.ctx.pr.core.app.find.laborinsurance;

import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBus;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccidentInsurService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class OccAccInsurBusFinder {

    @Inject
    private OccAccidentInsurService workersComInsurService;

    public List<OccAccInsurBusDto> getOccAccInsurBus(){
        String companyId = AppContexts.user().companyId();
        Optional<OccAccInsurBus> occAccInsurBus =  workersComInsurService.getOccAccInsurBus(companyId);
        List<OccAccInsurBusDto> occAccIsHisDtoList = new ArrayList<OccAccInsurBusDto>();
        occAccIsHisDtoList = occAccInsurBus.isPresent() ?  OccAccInsurBusDto.fromDomain(occAccInsurBus.get()) :  occAccIsHisDtoList;
        return occAccIsHisDtoList;
    }
}
