package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDateRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

//雇用から現在処理年月を取得する
@Stateless
public class AlgorithmProcessYearFromEmp {

    @Inject
    private EmpTiedProYearRepository empTiedProYearRepository;

    @Inject
    private CurrProcessDateRepository currProcessDateRepository;


    public YearMonth getProcessYear(String employmentCode){
        String cid = AppContexts.user().companyId();
        Optional<EmpTiedProYear> empTiedProYear = empTiedProYearRepository.getEmpTiedProYearByEmployment(cid, employmentCode);
        if (empTiedProYear.isPresent()){
            return currProcessDateRepository.getCurrProcessDateById(cid, empTiedProYear.get().getProcessCateNo()).get(0).getGiveCurrTreatYear();
        }else {
            return null;
        }
    }
}
