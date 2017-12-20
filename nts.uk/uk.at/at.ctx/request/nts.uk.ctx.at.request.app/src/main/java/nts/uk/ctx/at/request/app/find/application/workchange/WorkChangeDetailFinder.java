package nts.uk.ctx.at.request.app.find.application.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.workchange.IWorkChangeDetailService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkChangeDetailFinder {
	
	@Inject
	IWorkChangeDetailService workChangeDetailService;
	
	public WorkChangeDetailDto getWorkChangeDetailById(String appId){
		//会社ID 
		String cid = AppContexts.user().companyId();
		return WorkChangeDetailDto.formDomain(workChangeDetailService.getWorkChangeDetailById(cid, appId));
	}
}
