package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartApprovalRootService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartCheckErrorService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampNewPreOutput;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppStampNewDefault implements AppStampNewDomainService {
	
	@Inject
	private BeforePrelaunchAppCommonSet prelaunchAppCommonSetService;
	
	@Inject
	private StartCheckErrorService newScreenStartCheckErrorService;
	
	@Inject
	private NewBeforeRegister processBeforeRegisterService; 
	
	@Inject
	private RegisterAtApproveReflectionInfoService registerAtApproveReflectionInfoService;
	
	@Inject
	private AppStampRepository appStampRepository;
	
	@Inject
	private NewAfterRegister processAfterRegisterService;
	
	@Inject
	private StartApprovalRootService startApprovalRootService;
	
	@Inject
	private AppStampCommonDomainService appStampCommonDomainService;
	
	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;
	
	@Inject
	private ApprovalFrameRepository approvalFrameRepository;
	
	@Inject
	private ApproveAcceptedRepository approveAcceptedRepository; 

	@Override
	public AppStampNewPreOutput appStampPreProcess(String companyID, String employeeID, GeneralDate appDate) {
		AppStampNewPreOutput appStampNewPreOutput = new AppStampNewPreOutput();
		appStampNewPreOutput.appCommonSettingOutput = this.prelaunchAppCommonSetService.prelaunchAppCommonSetService(
															companyID, 
															employeeID, 
															1, // EmploymentRootAtr.APPLICATION 就業ルート区分.申請
															ApplicationType.STAMP_APPLICATION, 
															appDate);
		this.newScreenStartCheckErrorService.checkError(ApplicationType.STAMP_APPLICATION.value);
		appStampNewPreOutput.appStampSetOutput = this.appStampCommonDomainService.appStampSet(companyID);
		return appStampNewPreOutput;
	}

	@Override
	public void appStampRegister(String titleReason, String detailReason, AppStamp appStamp, List<AppApprovalPhase> appApprovalPhases) {
		this.appStampCommonDomainService.appReasonCheck(titleReason, detailReason, appStamp);
		// this.appStampCommonDomainService.validateReason(appStamp);
		this.appStampRegistration(appStamp);
		this.approvalRegistration(appApprovalPhases, appStamp.getApplicationID());
	}
	
	// 打刻申請の新規登録
	private void appStampRegistration(AppStamp appStamp) {
		/*this.processBeforeRegisterService.processBeforeRegister(
				appStamp.getCompanyID(), 
				appStamp.getApplicantSID(), 
				appStamp.getApplicationDate(), 
				appStamp.getPrePostAtr(), 
				1, // EmploymentRootAtr.APPLICATION 就業ルート区分.申請
				appStamp.getApplicationType().value);
		registerAtApproveReflectionInfoService.newScreenRegisterAtApproveInfoReflect(appStamp.getApplicantSID(), appStamp);*/
		appStampRepository.addStamp(appStamp);
		// this.processAfterRegisterService.processAfterRegister(appStamp.getCompanyID(), appStamp.getApplicationID());
	}
	
	private void approvalRegistration(List<AppApprovalPhase> appApprovalPhases, String appID){
		appApprovalPhases.forEach(appApprovalPhase -> {
			appApprovalPhase.setAppID(appID);
			String phaseID = UUID.randomUUID().toString();
			appApprovalPhase.setPhaseID(phaseID);
			appApprovalPhase.getListFrame().forEach(approvalFrame -> {
				String frameID = UUID.randomUUID().toString();
				approvalFrame.setFrameID(frameID);
				approvalFrame.getListApproveAccepted().forEach(appAccepted -> {
					String appAcceptedID = UUID.randomUUID().toString();
					appAccepted.setAppAcceptedID(appAcceptedID);
				});
			});
			appApprovalPhaseRepository.create(appApprovalPhase);
		});
	}
}
