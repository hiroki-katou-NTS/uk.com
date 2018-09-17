package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class DeleteProcessInformation {

	@Inject
	private ProcessInformationRepository processInformationRepository;
	
	public void processInformationDelete(int processCateNo){
	       String cid= AppContexts.user().companyId();
	       processInformationRepository.remove(cid,processCateNo);
	    }
	
	
	
}
