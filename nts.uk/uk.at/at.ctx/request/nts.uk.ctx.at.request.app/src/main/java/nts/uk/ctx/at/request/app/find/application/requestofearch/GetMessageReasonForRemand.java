package nts.uk.ctx.at.request.app.find.application.requestofearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.GetAllDataAppPhaseFrame;
import nts.uk.ctx.at.request.app.find.application.common.appapprovalphase.AppApprovalPhaseDto;
import nts.uk.ctx.at.request.app.find.application.common.approvalframe.ApprovalFrameDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetMessageReasonForRemand {
	@Inject
	private GetAllDataAppPhaseFrame getAllDataAppPhaseFrame;

	public List<String> getMessageReasonForRemand(String appID){
		List<String> listReason = new ArrayList<>();
		String loginEmp = AppContexts.user().employeeId();
		ApplicationDto ouput = getAllDataAppPhaseFrame.getAllDataAppPhaseFrame(appID);
		//add reason B3_1 in list Reason
		//申請.差戻し理由
		if(ouput == null) {
			listReason.add("");
		}else {
			listReason.add( ouput.getReversionReason());
		}
		//get list phase
		List<AppApprovalPhaseDto>  outputPhase  = new ArrayList<>();
		if(ouput.getListPhase() == null) {
			outputPhase = Collections.EMPTY_LIST;
		}else {
			outputPhase = ouput.getListPhase();  
		}
		
		if(ouput.getListPhase() != null) {
			outputPhase =ouput.getListPhase(); 
		}
		
		//loop listPhase get list frame
		for(AppApprovalPhaseDto outputPhaseAndFrame :outputPhase ) {
			//get list frame
			List<ApprovalFrameDto> list = outputPhaseAndFrame.getListFrame();
			//loop list frame get reason approver
			for(ApprovalFrameDto approvalFrameDto :list ) {
				approvalFrameDto.getListApproveAccepted().forEach(x -> {
					if(x.getApproverSID().equals(loginEmp)) {
						if(x.getReason().isEmpty()) {
							listReason.add("");
						}else {
							listReason.add(x.getReason());
						}
					}
					
				});
			}
		}
		//list[0] = B3_1 Reason in Domain : Application
		//list[1] reason to approver(loginer)
		return listReason;
	}
}
