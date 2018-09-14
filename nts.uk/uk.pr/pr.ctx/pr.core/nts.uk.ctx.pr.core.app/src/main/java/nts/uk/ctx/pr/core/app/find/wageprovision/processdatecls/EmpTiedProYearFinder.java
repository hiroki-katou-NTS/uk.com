package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmploymentCode;

@Stateless
/**
 * 処理年月に紐づく雇用
 */
public class EmpTiedProYearFinder {

    @Inject
    private EmpTiedProYearRepository finder;

//    public Optional<EmpTiedProYearDto> getEmpTiedProYearById(String cid, int processCateNo) {
//        return finder.getEmpTiedProYearById(cid, processCateNo);
//
//
//    }

}
