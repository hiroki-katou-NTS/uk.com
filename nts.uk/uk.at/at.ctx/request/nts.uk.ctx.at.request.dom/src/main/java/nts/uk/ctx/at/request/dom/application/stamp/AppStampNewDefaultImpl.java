package nts.uk.ctx.at.request.dom.application.stamp;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampNewPreOutput;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSetting_Old;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppStampNewDefaultImpl implements AppStampNewDomainService {
	
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	@Inject
	private NewBeforeRegister newBeforeRegister; 
	
	@Inject
	private RegisterAtApproveReflectionInfoService registerAtApproveReflectionInfoService;
	
	@Inject
	private AppStampRepository_Old appStampRepository;
	
	@Inject
	private NewAfterRegister newAfterRegister;
	
	@Inject
	private AppStampCommonDomainService appStampCommonDomainService;
	
	@Inject
	private ApplicationApprovalService applicationApprovalService;
	
	@Inject
	private StampRequestSettingRepository stampRequestSettingRepository;

	@Override
	public AppStampNewPreOutput appStampPreProcess(String companyID, String employeeID, GeneralDate appDate) {
		AppStampNewPreOutput appStampNewPreOutput = new AppStampNewPreOutput();
		appStampNewPreOutput.appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(
															companyID, 
															employeeID, 
															EmploymentRootAtr.APPLICATION.value,
															ApplicationType.STAMP_APPLICATION, 
															appDate);
		appStampNewPreOutput.appStampSetOutput = appStampCommonDomainService.appStampSet(companyID);
		appStampNewPreOutput.employeeName = appStampCommonDomainService.getEmployeeName(employeeID);
		return appStampNewPreOutput;
	}

	@Override
	public ProcessResult appStampRegister(String applicationReason, AppStamp_Old appStamp, boolean checkOver1Year) {
		appStampCommonDomainService.appReasonCheck(applicationReason, appStamp);
		appStampCommonDomainService.validateReason(appStamp);
		return appStampRegistration(appStamp, checkOver1Year);
	}
	
	// 打刻申請の新規登録
	private ProcessResult appStampRegistration(AppStamp_Old appStamp, boolean checkOver1Year) {
		/*
		StampRequestSetting_Old stampRequestSetting = stampRequestSettingRepository.findByCompanyID(appStamp.getApplication().getCompanyID()).get();
		// newBeforeRegister.processBeforeRegister(appStamp.getApplication_New(), OverTimeAtr.ALL, checkOver1Year, Collections.emptyList());
		appStamp.customValidate(stampRequestSetting.getStampPlaceDisp());
		appStampRepository.addStamp(appStamp);
		// error EA refactor 4
		/*applicationApprovalService.insert(appStamp.getApplication_New());*/
		// error EA refactor 4
		/*registerAtApproveReflectionInfoService.newScreenRegisterAtApproveInfoReflect(
				appStamp.getApplication_New().getEmployeeID(), 
				appStamp.getApplication_New());*/
		/*return newAfterRegister.processAfterRegister(appStamp.getApplication_New());*/
		return null;
	}
}
