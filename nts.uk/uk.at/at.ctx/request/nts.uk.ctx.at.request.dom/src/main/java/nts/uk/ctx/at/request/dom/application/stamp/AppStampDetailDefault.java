package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppStampDetailDefault implements AppStampDetailDomainService {

	@Inject
	private AppStampRepository appStampRepository; 
	
	@Inject
	private BeforePreBootMode beforePreBootMode;
	
	@Inject
	private AppStampCommonDomainService appStampCommonDomainService;
	
	/*@Inject
	private PreLaunchScreenSetting preLaunchScreenSetting;*/
	
	@Inject
	private DetailBeforeUpdate detailBeforeProcessRegister;
	
	@Inject
	private DetailAfterUpdate afterProcessDetail;
	
	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;
	
	@Inject
	private ApprovalFrameRepository approvalFrameRepository;
	
	@Inject
	private ApproveAcceptedRepository approveAcceptedRepository; 
	
	@Override
	public void appStampPreProcess(AppStamp appStamp) {
		this.beforePreBootMode.getDetailedScreenPreBootMode(appStamp, appStamp.getApplicationDate());
		// this.preLaunchScreenSetting
		this.appStampCommonDomainService.appStampSet(appStamp.getCompanyID());
		// 13.実績を取得する
	}

	@Override
	public void appStampUpdate(String titleReason, String detailReason, AppStamp appStamp, List<AppApprovalPhase> appApprovalPhases) {
		this.appStampCommonDomainService.appReasonCheck(titleReason, detailReason, appStamp);
		// this.appStampCommonDomainService.validateReason(appStamp);
		this.appStampUpdateProcess(appStamp);
		// this.approvalUpdate(appApprovalPhases, appStamp.getApplicationID());
	}
	
	private void appStampUpdateProcess(AppStamp appStamp) {
		/*this.detailBeforeProcessRegister.processBeforeDetailScreenRegistration(
				appStamp.getCompanyID(), 
				appStamp.getApplicantSID(), 
				appStamp.getApplicationDate(), 
				1, 
				appStamp.getApplicationID(), 
				PrePostAtr.PREDICT);*/
		appStampRepository.updateStamp(appStamp);
		// this.afterProcessDetail.processAfterDetailScreenRegistration(appStamp.getCompanyID(), appStamp.getApplicationID());
	}
	
	private void approvalUpdate(List<AppApprovalPhase> appApprovalPhases, String appID){
		appApprovalPhases.forEach(appApprovalPhase -> {
			appApprovalPhase.setAppID(appID);
			String phaseID = UUID.randomUUID().toString();
			appApprovalPhase.setPhaseID(phaseID);
			appApprovalPhaseRepository.update(appApprovalPhase);
			appApprovalPhase.getListFrame().forEach(approvalFrame -> {
				String frameID = UUID.randomUUID().toString();
				approvalFrame.setFrameID(frameID);
				approvalFrameRepository.update(approvalFrame, phaseID);
				approvalFrame.getListApproveAccepted().forEach(appAccepted -> {
					String appAcceptedID = UUID.randomUUID().toString();
					appAccepted.setAppAcceptedID(appAcceptedID);
					approveAcceptedRepository.updateApproverAccepted(appAccepted, frameID);
				});
			});
		});
	}
}
