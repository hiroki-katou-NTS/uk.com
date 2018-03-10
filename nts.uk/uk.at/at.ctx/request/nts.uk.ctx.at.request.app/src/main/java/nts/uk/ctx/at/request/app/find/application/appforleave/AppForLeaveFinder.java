package nts.uk.ctx.at.request.app.find.application.appforleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.appforleave.dto.AppForLeaveDto;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ApplicationReasonDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.dto.BusinessDayCalendarImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.StartupErrorCheckService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.InitValueAtr;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppForLeaveFinder {
	
	final static String DATE_FORMAT = "yyyy/MM/dd";
	final static String ZEZO_TIME = "00:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	final static String SPACE = " ";
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	@Inject
	private CollectApprovalRootPatternService collectApprovalRootPatternService;
	@Inject
	private StartupErrorCheckService startupErrorCheckService;
	@Inject
	private CollectAchievement collectAchievement;
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	@Inject
	private HdAppSetRepository hdAppSetRepository;
	@Inject
	private ApplicationReasonRepository applicationReasonRepository;
	
	public AppForLeaveDto getAppForLeave(String appDate, String employeeID){
		
		AppForLeaveDto result = new AppForLeaveDto();
		boolean checkCaller = true;
		if(employeeID == null){
			employeeID = AppContexts.user().employeeId();
			checkCaller = false;
		}
		String companyID = AppContexts.user().companyId();
		// 1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(
				companyID, employeeID, EmploymentRootAtr.APPLICATION.value,
				EnumAdaptor.valueOf(ApplicationType.ABSENCE_APPLICATION.value, ApplicationType.class),
				appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT));
		result.setManualSendMailFlg(
				appCommonSettingOutput.applicationSetting.getManualSendMailAtr().value == 1 ? true : false);
		// アルゴリズム「1-4.新規画面起動時の承認ルート取得パターン」を実行する
		ApprovalRootPattern approvalRootPattern = collectApprovalRootPatternService.getApprovalRootPatternService(
				companyID, employeeID, EmploymentRootAtr.APPLICATION,
				EnumAdaptor.valueOf(ApplicationType.ABSENCE_APPLICATION.value, ApplicationType.class),
				appCommonSettingOutput.generalDate, "", true);
		// アルゴリズム「1-5.新規画面起動時のエラーチェック」を実行する
		startupErrorCheckService.startupErrorCheck(appCommonSettingOutput.generalDate,
				ApplicationType.ABSENCE_APPLICATION.value, approvalRootPattern.getApprovalRootContentImport());
		// 1-1.起動時のエラーチェック
		List<Integer> holidayAppTypes = new ArrayList<>();
		
		if(!CollectionUtil.isEmpty(appCommonSettingOutput.appEmploymentWorkType)){
			for(AppEmploymentSetting appEmploymentSetting : appCommonSettingOutput.appEmploymentWorkType){
				if(!appEmploymentSetting.getHolidayTypeUseFlg()){
					holidayAppTypes.add(appEmploymentSetting.getHolidayOrPauseType());
				}
			}
		}
		if(CollectionUtil.isEmpty(holidayAppTypes)){
			throw new BusinessException("Msg_473");
		}
		result.setHolidayAppTypes(holidayAppTypes);
		if(appDate != null){
			 //13.実績の取得
			 AchievementOutput achievementOutput = collectAchievement.getAchievement(companyID, employeeID,  GeneralDate.fromString(appDate, DATE_FORMAT));
			
		}
		// 1-2.初期データの取得
		getData(appDate,employeeID,companyID,result,checkCaller,appCommonSettingOutput);
		return result;
	}
	/**
	 * 1-2.初期データの取得
	 * @param appDate
	 * @param employeeID
	 * @param companyID
	 * @param result
	 */
	private void getData(String appDate, String employeeID,String companyID,AppForLeaveDto result,boolean checkCaller,AppCommonSettingOutput appCommonSettingOutput){
		ApplicationDto_New applicationDto = new ApplicationDto_New();
		// show and hide A3_3 -> A3_6
		boolean displayPrePostFlg = false;
		if(appCommonSettingOutput.applicationSetting.getDisplayPrePostFlg().DISPLAY.equals(AppDisplayAtr.DISPLAY)){
			displayPrePostFlg = true;
		}
		result.setPrePostFlg(displayPrePostFlg);
		// 5.事前事後区分の判断
		InitValueAtr initValueAtr = this.otherCommonAlgorithm.judgmentPrePostAtr(ApplicationType.ABSENCE_APPLICATION,
				appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT), checkCaller);
		applicationDto.setPrePostAtr(initValueAtr.value);
		// ドメインモデル「休暇申請設定」を取得する(lấy dữ liệu domain 「休暇申請設定」)
		Optional<HdAppSet> hdAppSet = this.hdAppSetRepository.getAll();
		// ドメインモデル「申請定型理由」を取得する(lấy dữ liệu domain 「申請定型理由」) (hien thị A7_2)
		getAppReason(result,companyID);
		//
		
	}
	/**
	 * ドメインモデル「申請定型理由」を取得する(lấy dữ liệu domain 「申請定型理由」) (hien thị A7_2)
	 * @param result
	 * @param companyID
	 */
	private void getAppReason(AppForLeaveDto result,String companyID){
		List<ApplicationReason> applicationReasons = applicationReasonRepository.getReasonByAppType(companyID,
				ApplicationType.ABSENCE_APPLICATION.value);
		List<ApplicationReasonDto> applicationReasonDtos = new ArrayList<>();
		for (ApplicationReason applicationReason : applicationReasons) {
			ApplicationReasonDto applicationReasonDto = new ApplicationReasonDto(applicationReason.getReasonID(),
					applicationReason.getReasonTemp(), applicationReason.getDefaultFlg().value);
			applicationReasonDtos.add(applicationReasonDto);
		}
		result.setApplicationReasonDtos(applicationReasonDtos);
	}

}
