package nts.uk.ctx.pr.core.app.find.laborinsurance;

import nts.uk.ctx.pr.core.dom.laborinsurance.*;
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
        occAccIsHisDtoList = occAccIsHis.isPresent() ?  OccAccIsHisDto.fromDomain(occAccIsHis.get()) :  occAccIsHisDtoList;
        return occAccIsHisDtoList;
    }





}
