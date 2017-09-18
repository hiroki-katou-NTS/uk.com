package nts.uk.ctx.at.request.dom.application.stamp;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartApprovalRootService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartCheckErrorService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.AfterProcessRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeProcessRegister;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampSetOutput;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
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
	private NewBeforeProcessRegister processBeforeRegisterService; 
	
	@Inject
	private RegisterAtApproveReflectionInfoService registerAtApproveReflectionInfoService;
	
	@Inject
	private AppStampRepository appStampRepository;
	
	@Inject
	private AfterProcessRegister processAfterRegisterService;
	
	@Inject
	private StartApprovalRootService startApprovalRootService;
	
	@Inject
	private AppStampCommonDomainService appStampCommonDomainService;

	@Override
	public AppStampSetOutput appStampPreProcess(String companyID, String employeeID, GeneralDate appDate) {
		this.prelaunchAppCommonSetService.prelaunchAppCommonSetService(
				companyID, 
				employeeID, 
				1, // EmploymentRootAtr.APPLICATION 就業ルート区分.申請
				ApplicationType.STAMP_APPLICATION, 
				appDate);
		this.startApprovalRootService.getApprovalRootPattern(
				companyID, 
				employeeID, 
				1, // EmploymentRootAtr.APPLICATION 就業ルート区分.申請
				ApplicationType.STAMP_APPLICATION.value, 
				appDate);
		this.newScreenStartCheckErrorService.checkError(ApplicationType.STAMP_APPLICATION.value);
		return this.appStampCommonDomainService.appStampSet(companyID);
	}

	@Override
	public void appStampGoOutPermitRegister(String titleReason, String detailReason, AppStamp appStamp) {
		this.appStampCommonDomainService.appReasonCheck(titleReason, detailReason, appStamp);
		this.appStampCommonDomainService.validateReason(appStamp);
		this.appStampRegistration(appStamp);
	}

	@Override
	public void appStampWorkRegister(String titleReason, String detailReason, AppStamp appStamp) {
		this.appStampCommonDomainService.appReasonCheck(titleReason, detailReason, appStamp);
		this.appStampCommonDomainService.validateReason(appStamp);
		this.appStampRegistration(appStamp);
	}

	@Override
	public void appStampCancelRegister(String titleReason, String detailReason, AppStamp appStamp) {
		this.appStampCommonDomainService.appReasonCheck(titleReason, detailReason, appStamp);
		this.appStampCommonDomainService.validateReason(appStamp);
		this.appStampRegistration(appStamp);
	}

	@Override
	public void appStampOnlineRecordRegister(String titleReason, String detailReason, AppStamp appStamp) {
		this.appStampCommonDomainService.appReasonCheck(titleReason, detailReason, appStamp);
		this.appStampCommonDomainService.validateReason(appStamp);
		this.appStampRegistration(appStamp);
	}

	@Override
	public void appStampOtherRegister(String titleReason, String detailReason, AppStamp appStamp) {
		
	}
	
	// 打刻申請の新規登録
	private void appStampRegistration(AppStamp appStamp) {
		StampRequestMode StampRequestMode = appStamp.getStampRequestMode();
		this.processBeforeRegisterService.processBeforeRegister(
				appStamp.getCompanyID(), 
				appStamp.getApplicantSID(), 
				appStamp.getApplicationDate(), 
				appStamp.getPrePostAtr(), 
				1, // EmploymentRootAtr.APPLICATION 就業ルート区分.申請
				appStamp.getApplicationType().value);
		registerAtApproveReflectionInfoService.newScreenRegisterAtApproveInfoReflect(appStamp.getApplicantSID(), appStamp);
		switch(StampRequestMode){
			case STAMP_GO_OUT_PERMIT: appStampRepository.addStampGoOutPermit(appStamp);break;
			case STAMP_ADDITIONAL: appStampRepository.addStampWork(appStamp);break;
			case STAMP_CANCEL: appStampRepository.addStampCancel(appStamp);break;
			case STAMP_ONLINE_RECORD: appStampRepository.addStampOnlineRecord(appStamp);break;
			default: break;
		}
		this.processAfterRegisterService.processAfterRegister(appStamp.getCompanyID(), appStamp.getApplicationID());
	}
}
