package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.StartupErrorCheckService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class LateOrLeaveEarlyFinder {
	
//	@Inject
//	private ApplicationReasonRepository applicationReasonRepository;
	
	@Inject 
	private EmployeeRequestAdapter employeeAdapter;
	
	/** アルゴリズム「1-1.新規画面起動前申請共通設定を取得する」を実行する (Thực thi 「1-1.新規画面起動前申請共通設定を取得する」) */
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	/** アルゴリズム「1-4.新規画面起動時の承認ルート取得パターン」を実行する (Thực thi 「1-4.新規画面起動時の承認ルート取得パターン」) */
	@Inject
	private CollectApprovalRootPatternService collectApprovalRootPatternService;
	
	/** アルゴリズム「1-5.新規画面起動時のエラーチェック」を実行する (Thực thi 「1-5.新規画面起動時のエラーチェック」) */
	@Inject
	private  StartupErrorCheckService startupErrorCheckService;
	
	/** ドメインモデル「複数回勤務」を取得 (Lấy 「複数回勤務」) */
	@Inject
	private WorkManagementMultipleRepository workManagementMultipleRepository ;
	
	@Inject
	private LateOrLeaveEarlyRepository lateOrLeaveEarlyRepository;

	@Inject
	private ApplicationRepository appRepository;
	
	public ScreenLateOrLeaveEarlyDto getLateOrLeaveEarly(String appID) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		String applicantName = "";
	
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID, employeeID, 1, ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION, null);
		 
		
		ApprovalRootPattern approvalRootPattern = collectApprovalRootPatternService.getApprovalRootPatternService(
				companyID, 
				employeeID, 
				EmploymentRootAtr.APPLICATION, 
				ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION, 
				appCommonSettingOutput.generalDate, 
				appID, 
				true);
		startupErrorCheckService.startupErrorCheck(
				appCommonSettingOutput.generalDate, 
				ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION.value,
				approvalRootPattern.getApprovalRootContentImport());
		
		/** ドメインモデル「申請定型理由」を取得 (Lấy 「申請定型理由」) */
		
		// List<ApplicationReason> applicationReasons = applicationReasonRepository.getReasonByAppType(companyID, ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION.value);
		
		/** ドメインモデル「複数回勤務」を取得 (Lấy 「複数回勤務」) */
		
		Optional<WorkManagementMultiple> workManagementMultiple  = workManagementMultipleRepository.findByCode(companyID);
//		List<ApplicationReasonDto> listApplicationReasonDto = applicationReasons.stream()
//																.map(r -> new ApplicationReasonDto(r.getReasonID(), r.getReasonTemp().v(), r.getDefaultFlg().value))
//																.collect(Collectors.toList());
		LateOrLeaveEarlyDto lateOrLeaveEarlyDto = null;
		if(Strings.isNotEmpty(appID)) {
			Optional<LateOrLeaveEarly> lateOrLeaveEarlyOp = lateOrLeaveEarlyRepository.findByCode(companyID, appID);
			if(lateOrLeaveEarlyOp.isPresent()){
				LateOrLeaveEarly lateOrLeaveEarly =lateOrLeaveEarlyOp.get();
				//Get application data
				// lateOrLeaveEarly.setApplication(appRepository.findByID(companyID, appID).get());
//				lateOrLeaveEarlyDto = LateOrLeaveEarlyDto.fromDomain(lateOrLeaveEarly,lateOrLeaveEarly.getApplication().getVersion()); 
//				employeeID = lateOrLeaveEarly.getApplication().getEmployeeID();
//				applicantName = employeeAdapter.getEmployeeName(employeeID);
			}
		} else {
			employeeID = AppContexts.user().employeeId();
			applicantName = employeeAdapter.getEmployeeName(employeeID);
		}

//		return new ScreenLateOrLeaveEarlyDto(lateOrLeaveEarlyDto, listApplicationReasonDto, 
//				employeeID, 
//				applicantName,AppCommonSettingDto.convertToDto(appCommonSettingOutput),
//				workManagementMultiple.isPresent() ? WorkManagementMultipleDto.convertoDto(workManagementMultiple.get()) : null);
		return null;
		
	}
}