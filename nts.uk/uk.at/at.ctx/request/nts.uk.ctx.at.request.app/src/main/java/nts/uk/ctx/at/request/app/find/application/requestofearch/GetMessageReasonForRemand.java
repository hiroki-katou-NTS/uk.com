package nts.uk.ctx.at.request.app.find.application.requestofearch;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.common.GetAllDataAppPhaseFrame;
import nts.uk.ctx.at.request.app.find.application.common.OutputGetAllDataApp;
import nts.uk.ctx.at.request.app.find.application.common.OutputPhaseAndFrame;
import nts.uk.ctx.at.request.app.find.application.common.approvalframe.ApprovalFrameDto;

@Stateless
public class GetMessageReasonForRemand {
	@Inject
	private GetAllDataAppPhaseFrame getAllDataAppPhaseFrame;

	public List<String> getMessageReasonForRemand(String appID){
		List<String> listReason = new ArrayList<>();
		OutputGetAllDataApp ouput = getAllDataAppPhaseFrame.getAllDataAppPhaseFrame(appID);
		//add reason B3_1 in list Reason
		if(ouput.getApplicationDto().isPresent()) {
			listReason.add( ouput.getApplicationDto().get().getApplicationReason());
		}else {
			listReason.add("");
		}
		//get list phase 
		List<OutputPhaseAndFrame> outputPhase =  ouput.getListOutputPhaseAndFrame();
		//loop listPhase get list frame
		for(OutputPhaseAndFrame outputPhaseAndFrame :outputPhase ) {
			//get list frame
			List<ApprovalFrameDto> list = outputPhaseAndFrame.getListApprovalFrameDto();
			//loop list frame get reason approver
			for(ApprovalFrameDto approvalFrameDto :list ) {
				approvalFrameDto.getListApproveAcceptedDto().forEach(x -> {
					if(x.getReason().isEmpty()) {
						listReason.add("");
					}else {
						listReason.add(x.getReason());
					}
				});
			}
		}
		//list[0] = B3_1 Reason in Domain : Application
		//list[1 ---> end] list reason in approver in frame : list Reason in domain Frame
		return listReason;
	}
}
