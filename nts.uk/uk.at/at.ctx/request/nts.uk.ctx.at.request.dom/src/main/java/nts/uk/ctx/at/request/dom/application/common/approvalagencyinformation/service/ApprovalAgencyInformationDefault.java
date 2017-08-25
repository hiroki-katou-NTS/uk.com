package nts.uk.ctx.at.request.dom.application.common.approvalagencyinformation.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.approvalagencyinformation.ApprovalAgencyInformationOutput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApprovalAgencyInformationDefault implements ApprovalAgencyInformationService {

	
//	@Inject
//	AgentRepository agentRepository;
	
	
	@Override
	public ApprovalAgencyInformationOutput getApprovalAgencyInformation(String companyID, List<String> approver) {
		String companyId = AppContexts.user().companyId();
		if(approver.size()==0)
		{
			return null;
		}
		
		for(String approveItem : approver ) {
			
		}
		return null;
	}

}
