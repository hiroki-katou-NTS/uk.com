package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RemoveEmpTiedProYearCommandHandler
{
    
    @Inject
    private EmpTiedProYearRepository repository;
    

    public void removeEmpTiedProYear(int processCateNo) {
        String cid = AppContexts.user().companyId();
        repository.remove(cid, processCateNo);
    }
}
