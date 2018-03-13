package nts.uk.ctx.at.request.app.find.application.appabsence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceDto;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ApplicationReasonDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
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
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.InitValueAtr;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppAbsenceFinder {
	
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
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	
	public AppAbsenceDto getAppForLeave(String appDate, String employeeID){
		
		AppAbsenceDto result = new AppAbsenceDto();
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
		// get employeeName, employeeID
		String employeeName = "";
		employeeName = employeeAdapter.getEmployeeName(employeeID);
		result.setEmployeeID(employeeID);
		result.setEmployeeName(employeeName);
		return result;
	}
	
	/**
	 * @param startAppDate
	 * @param endAppDate
	 * @param workType
	 * @param employeeID
	 * @param holidayType
	 * @param alldayHalfDay
	 * @return
	 */
	public AppAbsenceDto getAllDisplay(String startAppDate, String endAppDate, String workType,String employeeID,Integer holidayType,int alldayHalfDay){
		
		return null;
	}
	public AppAbsenceDto getChangeAppDate(String startAppDate, String endAppDate,String employeeID){
		AppAbsenceDto result = new AppAbsenceDto();
		ApplicationDto_New application = new ApplicationDto_New();
		if(employeeID == null){
			employeeID = AppContexts.user().employeeId();
		}
		String companyID = AppContexts.user().companyId();
		
		// 1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(
				companyID, employeeID, EmploymentRootAtr.APPLICATION.value,
				EnumAdaptor.valueOf(ApplicationType.ABSENCE_APPLICATION.value, ApplicationType.class),
				startAppDate == null ? null : GeneralDate.fromString(startAppDate, DATE_FORMAT));
				
		// ドメインモデル「申請表示設定」．事前事後区分表示をチェックする
		if (appCommonSettingOutput.applicationSetting.getDisplayPrePostFlg().value == AppDisplayAtr.NOTDISPLAY.value) {
			result.setPrePostFlg(AppDisplayAtr.NOTDISPLAY.value == 1 ? true : false);
			// 3.事前事後の判断処理(事前事後非表示する場合)
			PrePostAtr prePostAtrJudgment = otherCommonAlgorithm.preliminaryJudgmentProcessing(
					EnumAdaptor.valueOf(ApplicationType.BREAK_TIME_APPLICATION.value, ApplicationType.class),
					appCommonSettingOutput.generalDate);
			if (prePostAtrJudgment != null) {
				int prePostAtr = prePostAtrJudgment.value;
				application.setPrePostAtr(prePostAtr);
			}
		} else {
			result.setPrePostFlg(AppDisplayAtr.DISPLAY.value == 1 ? true : false);
		}
		// ドメインモデル「申請設定」．承認ルートの基準日をチェックする ( Domain model "application setting". Check base date of approval route )
		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
		if(appCommonSettingOutput.applicationSetting.getBaseDateFlg().value == BaseDateFlg.APP_DATE.value){
			// 1.勤務種類を取得する（新規） :TODO
				
		}
		result.setApplication(application);
		return null;
	}
	/**
	 * 1-2.初期データの取得
	 * @param appDate
	 * @param employeeID
	 * @param companyID
	 * @param result
	 */
	private void getData(String appDate, String employeeID,String companyID,AppAbsenceDto result,boolean checkCaller,AppCommonSettingOutput appCommonSettingOutput){
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
		result.setApplication(applicationDto);
		getAppReason(result,companyID);
		//
		
	}
	/**
	 * ドメインモデル「申請定型理由」を取得する(lấy dữ liệu domain 「申請定型理由」) (hien thị A7_2)
	 * @param result
	 * @param companyID
	 */
	private void getAppReason(AppAbsenceDto result,String companyID){
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
