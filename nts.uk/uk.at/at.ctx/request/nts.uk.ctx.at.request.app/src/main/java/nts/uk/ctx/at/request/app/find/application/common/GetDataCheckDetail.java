package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InputGetDetailCheck;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetDataCheckDetail {

	@Inject
	private BeforePreBootMode beforePreBootMode;
	
	@Inject
	private ApplicationRepository appRepo;
	
	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;
	
	@Inject
	private ApprovalFrameRepository approvalFrameRepository;
	
	@Inject 
	private ApproveAcceptedRepository approveAcceptedRepo;
	
	public OutputDetailCheckDto getDataCheckDetail(InputGetDetailCheck inputGetDetailCheck) {
		String companyID = AppContexts.user().companyId();
		OutputDetailCheckDto ouput = null;
		Application app = appRepo.getAppById(companyID, inputGetDetailCheck.getApplicationID()).get();
		List<AppApprovalPhase> listPhaseByAppID = this.appApprovalPhaseRepository.findPhaseByAppID(companyID, inputGetDetailCheck.getApplicationID());
		app.changeListPhase(listPhaseByAppID);
		if(listPhaseByAppID != null) {
			for(AppApprovalPhase appApprovalPhase: listPhaseByAppID) {
				List<ApprovalFrame> listFrame = approvalFrameRepository.getAllApproverByPhaseID(companyID, appApprovalPhase.getPhaseID());
				appApprovalPhase.setListFrame(listFrame);
				if(listFrame != null) {
					for(ApprovalFrame approvalFrame:listFrame) {
						List<ApproveAccepted> listApproveAccepted = approveAcceptedRepo.getAllApproverAccepted(companyID, approvalFrame.getFrameID());
						approvalFrame.setListApproveAccepted(listApproveAccepted);
					}
				}
			}
		}
		
		GeneralDate generalDate = GeneralDate.fromString(inputGetDetailCheck.getBaseDate(), "yyyy/MM/dd");
		if(app!= null) {
			ouput = OutputDetailCheckDto.fromDomain(beforePreBootMode.getDetailedScreenPreBootMode(app, generalDate))	;
		}
		return ouput;
	}
	
	
}
