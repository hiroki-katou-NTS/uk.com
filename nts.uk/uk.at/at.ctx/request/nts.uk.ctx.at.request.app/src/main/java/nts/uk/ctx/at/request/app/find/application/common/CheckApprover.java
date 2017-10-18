package nts.uk.ctx.at.request.app.find.application.common;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.app.find.application.common.appapprovalphase.AppApprovalPhaseDto;
import nts.uk.ctx.at.request.app.find.application.common.approvalframe.ApprovalFrameDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckApprover {
	
	public boolean checkApprover(ApplicationDto application,String memo) {
		String loginEmp = AppContexts.user().employeeId();
		for(AppApprovalPhaseDto appApprovalPhaseDto : application.getListPhase()) {
			for(ApprovalFrameDto approvalFrameDto :appApprovalPhaseDto.getListFrame()) {
				for(ApproveAcceptedDto approveAcceptedDto : approvalFrameDto.getListApproveAccepted() ) {
					if(approveAcceptedDto.getApproverSID().equals(loginEmp)) {
						approveAcceptedDto.setReason(memo);
						return true;
					}
				} //end accepted
			} //end frame
		}//end phase
		return false;
		
	}

}
