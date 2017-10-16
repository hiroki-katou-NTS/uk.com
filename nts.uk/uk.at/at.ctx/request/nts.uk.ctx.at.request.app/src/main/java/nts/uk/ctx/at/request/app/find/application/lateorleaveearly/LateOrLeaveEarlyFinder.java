package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppCommonSettingDto;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartApprovalRootService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartCheckErrorService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class LateOrLeaveEarlyFinder {
	
	@Inject
	private LateOrLeaveEarlyRepository lateOrLeaveEarlyRepository;
	
	@Inject
	private ApplicationReasonRepository applicationReasonRepository;
	
	@Inject 
	private EmployeeAdapter employeeAdapter;
	
	/** アルゴリズム「1-1.新規画面起動前申請共通設定を取得する」を実行する (Thực thi 「1-1.新規画面起動前申請共通設定を取得する」) */
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	/** アルゴリズム「1-4.新規画面起動時の承認ルート取得パターン」を実行する (Thực thi 「1-4.新規画面起動時の承認ルート取得パターン」) */
	@Inject
	private StartApprovalRootService startApprovalRootService;
	
	/** アルゴリズム「1-5.新規画面起動時のエラーチェック」を実行する (Thực thi 「1-5.新規画面起動時のエラーチェック」) */
	@Inject
	private  StartCheckErrorService  startCheckErrorService;

	public ScreenLateOrLeaveEarlyDto getLateOrLeaveEarly(String appID) {
		String companyID = AppContexts.user().companyId();
		String employeeID =  AppContexts.user().employeeId();
		String applicantName = employeeAdapter.getEmployeeName(employeeID);
	
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID, employeeID, 1, ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION, null);
		 
		
		startApprovalRootService.getApprovalRootPattern(companyID, employeeID, 1, ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION.value, null);
		
		startCheckErrorService.checkError(ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION.value);
		
		List<ApplicationReason> applicationReasons = applicationReasonRepository.getReasonByAppType(companyID, ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION.value);
		
		List<ApplicationReasonDto> listApplicationReasonDto = applicationReasons.stream()
																.map(r -> new ApplicationReasonDto(r.getReasonID(), r.getReasonTemp()))
																.collect(Collectors.toList());
		Optional<LateOrLeaveEarly> lateOrLeaveEarly = Optional.empty();
		if (!StringUtil.isNullOrEmpty(appID, true)) {
			lateOrLeaveEarly = this.lateOrLeaveEarlyRepository.findByCode(companyID, appID);
		}
		
		if (!lateOrLeaveEarly.isPresent()) {
			return new ScreenLateOrLeaveEarlyDto(null, listApplicationReasonDto, employeeID, applicantName,AppCommonSettingDto.convertToDto(appCommonSettingOutput));
		}
		else {
			LateOrLeaveEarly result = lateOrLeaveEarly.get();
			LateOrLeaveEarlyDto lateOrLeaveEarlyDto = LateOrLeaveEarlyDto.fromDomain(result);
			return new ScreenLateOrLeaveEarlyDto(lateOrLeaveEarlyDto, listApplicationReasonDto, employeeID, applicantName,AppCommonSettingDto.convertToDto(appCommonSettingOutput));
		}
	}
}