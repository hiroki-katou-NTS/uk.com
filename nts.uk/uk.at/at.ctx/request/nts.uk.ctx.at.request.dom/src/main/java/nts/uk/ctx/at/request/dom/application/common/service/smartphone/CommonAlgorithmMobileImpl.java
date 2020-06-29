package nts.uk.ctx.at.request.dom.application.common.service.smartphone;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.EnvAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.MailServerSetImport;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.AppReasonOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReason;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandardRepository;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CommonAlgorithmMobileImpl implements CommonAlgorithmMobile {
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private ApplicationSettingRepository applicationSettingRepository;
	
	@Inject
	private DisplayReasonRepository displayReasonRepository;
	
	@Inject
	private AppReasonStandardRepository appReasonStandardRepository;
	
	@Inject
	private EnvAdapter envAdapter;
	
	@Inject
	private WorkManagementMultipleRepository workManagementMultipleRepository;

	@Override
	public AppDispInfoStartupOutput appCommonStartProcess(boolean mode, String companyID, String employeeID,
			ApplicationType appType, Optional<HolidayAppType> opHolidayAppType, List<GeneralDate> dateLst,
			Optional<OverTimeAtr> opOverTimeAtr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppDispInfoNoDateOutput getAppCommonSetInfo(boolean mode, String companyID, String employeeID,
			ApplicationType appType, Optional<HolidayAppType> opHolidayAppType) {
		List<EmployeeInfoImport> employeeInfoLst = Collections.emptyList();
		// INPUT．「起動モード」をチェックする
		if(!mode) {
			// 申請者情報を取得する
			employeeInfoLst = commonAlgorithm.getEmployeeInfoLst(Arrays.asList(employeeID));
		}
		// 申請別の申請設定の取得
		ApplicationSetting applicationSetting = applicationSettingRepository.findByAppType(companyID, appType);
		// 申請理由を取得する
		AppReasonOutput appReasonOutput = this.getAppReasonDisplay(companyID, appType, opHolidayAppType);
		// メールサーバを設定したかチェックする
		MailServerSetImport mailServerSetImport = envAdapter.checkMailServerSet(companyID);
		// 複数回勤務を取得
		Optional<WorkManagementMultiple> opWorkManagementMultiple = workManagementMultipleRepository.findByCode(companyID);
		// 取得した内容を返す
		return new AppDispInfoNoDateOutput(
				mailServerSetImport.isMailServerSet(), 
				NotUseAtr.NOT_USE, 
				employeeInfoLst, 
				applicationSetting, 
				Collections.emptyList(), 
				appReasonOutput.getDisplayAppReason(), 
				appReasonOutput.getDisplayStandardReason(), 
				appReasonOutput.getReasonTypeItemLst(), 
				opWorkManagementMultiple.isPresent());
	}

	@Override
	public AppReasonOutput getAppReasonDisplay(String companyID, ApplicationType appType,
			Optional<HolidayAppType> opHolidayAppType) {
		// INPUT．「申請種類」をチェックする
		if(appType == ApplicationType.ABSENCE_APPLICATION) {
			// INPUT．「休暇申請の種類」をチェックする
			if(!opHolidayAppType.isPresent()) {
				// OUTPUTを返す
				return new AppReasonOutput(
						DisplayAtr.NOT_DISPLAY, 
						DisplayAtr.NOT_DISPLAY, 
						Collections.emptyList());
			}
			// ドメインモデル「申請定型理由」を取得する
			Optional<DisplayReason> opDisplayReason = displayReasonRepository.findByHolidayAppType(companyID, opHolidayAppType.get());
			// 取得できたかチェックする
			if(!opDisplayReason.isPresent()) {
				// OUTPUTを返す
				return new AppReasonOutput(
						DisplayAtr.NOT_DISPLAY, 
						DisplayAtr.NOT_DISPLAY, 
						Collections.emptyList());
			}
			// 取得した「申請理由表示．定型理由の表示」をチェックする
			if(opDisplayReason.get().getDisplayFixedReason() == DisplayAtr.NOT_DISPLAY) {
				// OUTPUTを返す
				return new AppReasonOutput(
						opDisplayReason.get().getDisplayFixedReason(), 
						opDisplayReason.get().getDisplayAppReason(), 
						Collections.emptyList());
			}
			// ドメインモデル「申請定型理由」を取得する
			AppReasonStandard appReasonStandard = appReasonStandardRepository.findByHolidayAppType(companyID, opHolidayAppType.get());
			// OUTPUTを返す
			return new AppReasonOutput(
					opDisplayReason.get().getDisplayFixedReason(), 
					opDisplayReason.get().getDisplayAppReason(), 
					appReasonStandard.getReasonTypeItemLst());
			
		}
		// ドメインモデル「申請定型理由」を取得する
		Optional<DisplayReason> opDisplayReason = displayReasonRepository.findByAppType(companyID, appType);
		// 取得できたかチェックする
		if(!opDisplayReason.isPresent()) {
			// OUTPUTを返す
			return new AppReasonOutput(
					DisplayAtr.NOT_DISPLAY, 
					DisplayAtr.NOT_DISPLAY, 
					Collections.emptyList());
		}
		// 取得した「申請理由表示．定型理由の表示」をチェックする
		if(opDisplayReason.get().getDisplayFixedReason() == DisplayAtr.NOT_DISPLAY) {
			// OUTPUTを返す
			return new AppReasonOutput(
					opDisplayReason.get().getDisplayFixedReason(), 
					opDisplayReason.get().getDisplayAppReason(), 
					Collections.emptyList());
		}
		// ドメインモデル「申請定型理由」を取得する
		AppReasonStandard appReasonStandard = appReasonStandardRepository.findByAppType(companyID, appType);
		// OUTPUTを返す
		return new AppReasonOutput(
				opDisplayReason.get().getDisplayFixedReason(), 
				opDisplayReason.get().getDisplayAppReason(), 
				appReasonStandard.getReasonTypeItemLst());
	}

}
