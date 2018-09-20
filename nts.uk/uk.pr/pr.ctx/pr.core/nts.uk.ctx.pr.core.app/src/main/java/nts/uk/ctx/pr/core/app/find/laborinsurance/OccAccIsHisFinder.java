package nts.uk.ctx.pr.core.app.find.laborinsurance;

import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsHis;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccidentInsurService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class OccAccIsHisFinder {

    @Inject
    private OccAccidentInsurService workersComInsurService;

    public List<OccAccIsHisDto> getListEmplInsurHis(){
        String companyId = AppContexts.user().companyId();
        Optional<OccAccIsHis> occAccIsHis =  workersComInsurService.initDataAcquisition(companyId);
        List<OccAccIsHisDto> occAccIsHisDtoList = new ArrayList<OccAccIsHisDto>();
        if (occAccIsHis.isPresent() && occAccIsHis.get().getHistory() != null) {
            occAccIsHisDtoList = OccAccIsHisDto.fromDomain(occAccIsHis.get());
        }
        return occAccIsHisDtoList;
    }





}
