package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class DeleteValPayDateSetCommand {
    @Inject
    ValPayDateSetRepository valPayDateSetRepository;



    public void valPayDateSetDelete(int processCateNo){
       String cid= AppContexts.user().companyId();
       valPayDateSetRepository.remove(cid,processCateNo);
    }
}
