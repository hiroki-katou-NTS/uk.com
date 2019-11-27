package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;


import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.PerProcessClsSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PerProcesClsSetFinder {

    @Inject
    PerProcessClsSetRepository finder;

    public PerProcesClsSetDto getPerProcesClsSetbyUIDAndCID(){
        String companyId = AppContexts.user().companyId();
        String userId = AppContexts.user().userId();
        return PerProcesClsSetDto.fromDomain(finder.getPerProcessClsSetByUIDAndCID(userId,companyId).orElse(null));
    }

}
