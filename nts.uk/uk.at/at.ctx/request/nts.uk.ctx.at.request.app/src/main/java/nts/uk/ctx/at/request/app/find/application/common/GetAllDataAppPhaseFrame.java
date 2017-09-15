package nts.uk.ctx.at.request.app.find.application.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.app.find.application.common.appapprovalphase.AppApprovalPhaseDto;
import nts.uk.ctx.at.request.app.find.application.common.approvalframe.ApprovalFrameDto;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 15.詳細画面申請データを取得する
 * @author tutk
 *
 */
@Stateless
public class GetAllDataAppPhaseFrame {
	
	@Inject
	private ApplicationRepository appRepo;

	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;
	
	@Inject
	private ApprovalFrameRepository approvalFrameRepository;
	
	@Inject 
	private ApproveAcceptedRepository approveAcceptedRepo;
	
	public OutputGetAllDataApp getAllDataAppPhaseFrame(String applicationID) {
		
		String companyID = AppContexts.user().companyId();
		//info app
		Optional<ApplicationDto> application = this.appRepo.getAppById(companyID, applicationID)
				.map(c->ApplicationDto.fromDomain(c));
		//list output
		List<OutputPhaseAndFrame> listOutputPhaseAndFrame = new ArrayList<>();
		//list phase
		List<AppApprovalPhaseDto> listPhaseByAppID = this.appApprovalPhaseRepository.findPhaseByAppID(companyID, applicationID)
				.stream().map(appApprovalPhase -> AppApprovalPhaseDto.fromDomain(appApprovalPhase))
				.collect(Collectors.toList());
		//duyet list phase
		for(AppApprovalPhaseDto appApprovalPhase : listPhaseByAppID) {
			//get list frame by phaseID
			List<ApprovalFrameDto> listFrame = 
					this.approvalFrameRepository.getAllApproverByPhaseID(companyID, appApprovalPhase.getPhaseID())
					.stream().map(approvalFrame -> ApprovalFrameDto.fromDomain(approvalFrame)).collect(Collectors.toList());
			//get list approve accepted
			List<ApproveAcceptedDto> listApproveAccepted = 
					this.approveAcceptedRepo.getAllApproverAccepted(companyID, appApprovalPhase.getPhaseID())
					.stream().map(c -> ApproveAcceptedDto.fromDomain(c)).collect(Collectors.toList());
			
			OutputPhaseAndFrame outputPhaseAndFrame = new OutputPhaseAndFrame(appApprovalPhase, listFrame, listApproveAccepted);
					
			listOutputPhaseAndFrame.add(outputPhaseAndFrame);
			
		}
		OutputGetAllDataApp dataApp = new OutputGetAllDataApp(application, listOutputPhaseAndFrame);
		
		if(dataApp.equals(null)) {
			throw new BusinessException("Msg_198");
		}
		return dataApp;
	}
	
}
