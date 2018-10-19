package nts.uk.ctx.pr.core.app.find.laborinsurance;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsHis;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccidentInsurService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class OccAccIsHisFinder {

    @Inject
    private OccAccidentInsurService workersComInsurService;

    public List<OccAccIsHisDto> getListEmplInsurHis(){
        String companyId = AppContexts.user().companyId();
        OccAccIsHis occAccIsHis =  workersComInsurService.initDataAcquisition(companyId);
        List<OccAccIsHisDto> occAccIsHisDtoList = new ArrayList<OccAccIsHisDto>();
        if (occAccIsHis.getHistory() != null && !occAccIsHis.getHistory().isEmpty()) {
            occAccIsHisDtoList = OccAccIsHisDto.fromDomain(occAccIsHis);
        }
        return occAccIsHisDtoList;
    }





}
