package nts.uk.ctx.at.request.app.find.application.holidaywork;

import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHolidayWorkDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.StartupErrorCheckService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.shr.com.context.AppContexts;

public class AppHolidayWorkFinder {
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
	
	public AppHolidayWorkDto getAppHolidayWork(String appDateInput){
		
		AppHolidayWorkDto result = new AppHolidayWorkDto();
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		int rootAtr = 1;
		
		//1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeID,
				rootAtr, EnumAdaptor.valueOf(ApplicationType.BREAK_TIME_APPLICATION.value, ApplicationType.class),appDateInput == null ? null : GeneralDate.fromString(appDateInput, DATE_FORMAT));
		result.setManualSendMailAtr(appCommonSettingOutput.applicationSetting.getManualSendMailAtr().value  ==1 ?true : false);
		//アルゴリズム「1-4.新規画面起動時の承認ルート取得パターン」を実行する
		ApprovalRootPattern approvalRootPattern = collectApprovalRootPatternService.getApprovalRootPatternService(companyID, employeeID, EmploymentRootAtr.APPLICATION, EnumAdaptor.valueOf(ApplicationType.BREAK_TIME_APPLICATION.value, ApplicationType.class), appCommonSettingOutput.generalDate, "", true);
		//アルゴリズム「1-5.新規画面起動時のエラーチェック」を実行する 
		 startupErrorCheckService.startupErrorCheck(appCommonSettingOutput.generalDate, ApplicationType.BREAK_TIME_APPLICATION.value, approvalRootPattern.getApprovalRootContentImport());
		 // 申請対象日のパラメータがあるかチェックする
		 

		 if(appDateInput != null){
			 //13.実績の取得
			 AchievementOutput achievementOutput = collectAchievement.getAchievement(companyID, employeeID,  GeneralDate.fromString(appDateInput, DATE_FORMAT));
		 }
		 // アルゴリズム「初期データの取得」を実行する
		 getData(companyID,employeeID,appDateInput,appCommonSettingOutput,result);
		return null;
	}
	
	/**
	 * 01_初期データ取得
	 * @param companyID
	 * @param employeeID
	 * @param appDateHolidayWork
	 * @param appCommonSettingOutput
	 * @param result
	 */
	public void getData(String companyID,String employeeID,String appDateHolidayWork,AppCommonSettingOutput appCommonSettingOutput,AppHolidayWorkDto result){
		ApplicationDto_New applicationDto = new ApplicationDto_New();
		//01-12_申請日付取得
		applicationDto.setApplicationDate(appDateHolidayWork);
		// 01-01_休出通知情報を取得
		
		
	}
}
