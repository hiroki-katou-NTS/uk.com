package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDateRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

//雇用から現在処理年月を取得する
@Stateless
public class AlgorithmProcessYearFromEmp {

    @Inject
    private EmpTiedProYearRepository empTiedProYearRepository;

    @Inject
    private CurrProcessDateRepository currProcessDateRepository;


    public void getProcessYear(){
        String cid = AppContexts.user().companyId();
    }

}
