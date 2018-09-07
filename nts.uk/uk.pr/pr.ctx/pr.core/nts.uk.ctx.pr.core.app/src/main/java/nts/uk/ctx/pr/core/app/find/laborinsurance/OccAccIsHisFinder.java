package nts.uk.ctx.pr.core.app.find.laborinsurance;

import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHis;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHisRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsHisRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class OccAccIsHisFinder {

    @Inject
    private OccAccIsHisRepository occAccIsHisRepository;

    public List<EmpInsurHisDto> getListEmplInsurHis(){

        return null;
    }


}
