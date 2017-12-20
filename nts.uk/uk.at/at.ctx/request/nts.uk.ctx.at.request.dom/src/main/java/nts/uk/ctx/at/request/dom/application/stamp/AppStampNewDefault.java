package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartCheckErrorService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampNewPreOutput;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppStampNewDefault implements AppStampNewDomainService {
	
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	@Inject
	private StartCheckErrorService startCheckErrorService;
	
	@Inject
	private NewBeforeRegister newBeforeRegister; 
	
	@Inject
	private RegisterAtApproveReflectionInfoService registerAtApproveReflectionInfoService;
	
	@Inject
	private AppStampRepository appStampRepository;
	
	@Inject
	private NewAfterRegister newAfterRegister;
	
	@Inject
	private AppStampCommonDomainService appStampCommonDomainService;
	
	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;

	@Override
	public AppStampNewPreOutput appStampPreProcess(String companyID, String employeeID, GeneralDate appDate) {
		AppStampNewPreOutput appStampNewPreOutput = new AppStampNewPreOutput();
		appStampNewPreOutput.appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(
															companyID, 
															employeeID, 
															1, // EmploymentRootAtr.APPLICATION 就業ルート区分.申請
															ApplicationType.STAMP_APPLICATION, 
															appDate);
		startCheckErrorService.checkError(ApplicationType.STAMP_APPLICATION.value);
		appStampNewPreOutput.appStampSetOutput = appStampCommonDomainService.appStampSet(companyID);
		appStampNewPreOutput.employeeName = appStampCommonDomainService.getEmployeeName(employeeID);
		return appStampNewPreOutput;
	}

	@Override
	public String appStampRegister(String applicationReason, AppStamp appStamp, List<AppApprovalPhase> appApprovalPhases) {
		appStampCommonDomainService.appReasonCheck(applicationReason, appStamp);
		appStampCommonDomainService.validateReason(appStamp);
		return appStampRegistration(appStamp, appApprovalPhases);
	}
	
	// 打刻申請の新規登録
	private String appStampRegistration(AppStamp appStamp, List<AppApprovalPhase> appApprovalPhases) {
		appStamp.setListPhase(appApprovalPhases);
		newBeforeRegister.processBeforeRegister(appStamp);
		registerAtApproveReflectionInfoService.newScreenRegisterAtApproveInfoReflect(appStamp.getApplicantSID(), appStamp);
		appStampRepository.addStamp(appStamp);
		approvalRegistration(appApprovalPhases, appStamp.getApplicationID());
		return newAfterRegister.processAfterRegister(appStamp);
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
