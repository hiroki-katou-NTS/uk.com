package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.AppLimitSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service.ActualLockingCheck;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service.DayActualConfirmDoneCheck;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service.MonthActualConfirmDoneCheck;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service.WorkConfirmDoneCheck;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.ReceptionRestrictionSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class NewBeforeRegisterImpl implements NewBeforeRegister {
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithmService;
	
	@Inject
	private DayActualConfirmDoneCheck dayActualConfirmDoneCheck;

	@Inject
	private MonthActualConfirmDoneCheck monthActualConfirmDoneCheck;

	@Inject
	private WorkConfirmDoneCheck workConfirmDoneCheck;

	@Inject
	private ActualLockingCheck actualLockingCheck;
	
	// moi nguoi chi co the o mot cty vao mot thoi diem
	// check xem nguoi xin con trong cty k
	public void retirementCheckBeforeJoinCompany(String companyID, String employeeID, GeneralDate date){
		// Imported(就業)「社員」を取得する
		PesionInforImport pesionInforImport = employeeAdaptor.getEmployeeInfor(employeeID);
		// 入社前をチェックする
		// データが１件以上取得できた 且つ 申請対象日 < Imported(就業)「社員」．入社年月日
		if(pesionInforImport.getEntryDate() != null && date.before(pesionInforImport.getEntryDate())) {
			throw new BusinessException("Msg_235");
		}
		
		// 退職後をチェックする
		// データが１件以上取得できた 且つ 申請対象日 > Imported(就業)「社員」．退職年月日
		if(pesionInforImport.getRetiredDate() != null && date.after(pesionInforImport.getRetiredDate())) {
			throw new BusinessException("Msg_391");
		}
	}
	
	public void deadlineAppCheck(GeneralDate appStartDate, GeneralDate appEndDate,
			GeneralDate closureStartDate, GeneralDate closureEndDate, AppDispInfoWithDateOutput appDispInfoWithDateOutput){
		// INPUT．申請表示情報(基準日関係あり)．申請締め切り日利用区分をチェックする
		if(appDispInfoWithDateOutput.getAppDeadlineUseCategory()==NotUseAtr.NOT_USE) {
			return;
		}
		// INPUT．申請する開始日からINPUT．申請する終了日までループする
		for(GeneralDate loopAppDate = appStartDate; loopAppDate.beforeOrEquals(appEndDate); loopAppDate = loopAppDate.addDays(1)) {
			// 当月の申請をするかチェックする
			if(loopAppDate.afterOrEquals(closureStartDate) && loopAppDate.beforeOrEquals(closureEndDate)) {
				// システム日付とINPUT．申請表示情報(基準日関係あり)．申請締め切り日を比較する
				if(GeneralDate.today().after(appDispInfoWithDateOutput.getOpAppDeadline().get())) {
					// エラーメッセージ(Msg_327)
					throw new BusinessException("Msg_327", loopAppDate.toString());
				}
			}
		}
	}
	
	public void appAcceptanceRestrictionsCheck(PrePostAtr prePostAtr, GeneralDate appStartDate, GeneralDate appEndDate,
			ApplicationType appType, OvertimeAppAtr overtimeAppAtr, AppDispInfoNoDateOutput appDispInfoNoDateOutput){
		GeneralDate systemDate = GeneralDate.today();
		ReceptionRestrictionSetting receptionRestrictionSetting = appDispInfoNoDateOutput.getApplicationSetting().getReceptionRestrictionSettings()
				.stream().filter(x -> x.getAppType() == appType).findAny().orElse(null);
		// INPUT．事前事後区分をチェックする
		if(prePostAtr==PrePostAtr.POSTERIOR) {
			// INPUT．申請設定（基準日関係なし）．申請承認設定．申請設定．受付制限設定．事後の受付制限．未来日許可しないをチェックする
			if(!receptionRestrictionSetting.getAfterhandRestriction().isAllowFutureDay()) {
				return;
			}
			// 未来日の事後申請かチェックする
			if(appStartDate.after(systemDate) || appEndDate.after(systemDate)) {
				// エラーメッセージ(Msg_328)
				throw new BusinessException("Msg_328");
			}
			return;
		}
		// INPUT．申請表示情報(基準日関係なし)．事前申請の受付制限をチェックする
		if(appDispInfoNoDateOutput.getAdvanceAppAcceptanceLimit()==NotUseAtr.NOT_USE) {
			return;
		}
		// INPUT．申請する開始日からINPUT．申請する終了日までループする
		for(GeneralDate loopAppDate = appStartDate; loopAppDate.beforeOrEquals(appEndDate); loopAppDate = loopAppDate.addDays(1)) {
			// 対象日が申請可能かを判定する
			boolean errorFlg = receptionRestrictionSetting.applyPossibleCheck(
					appType, 
					loopAppDate, 
					overtimeAppAtr, 
					appDispInfoNoDateOutput.getOpAdvanceReceptionDate().orElse(null), 
					appDispInfoNoDateOutput.getOpAdvanceReceptionHours().orElse(null));
			if(errorFlg) {
				// エラーメッセージ(Msg_327)
				throw new BusinessException("Msg_327", loopAppDate.toString());
			}
		}
	}
	
	public void confirmationCheck(String companyID, String employeeID, GeneralDate appDate, AppDispInfoStartupOutput appDispInfoStartupOutput){
		AppLimitSetting appLimitSetting = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppLimitSetting();
		// ドメインモデル「申請制限設定」．日別実績が確認済なら申請できないをチェックする(check domain 「申請制限設定」．日別実績が確認済なら申請できない)
		boolean hasError = false;
		hasError = dayActualConfirmDoneCheck.check(appLimitSetting.isCanAppAchievementConfirm(), companyID, employeeID, appDate);
		if (hasError == true) {
			throw new BusinessException("Msg_448");
		}
		// ドメインモデル「申請制限設定」．月別実績が確認済なら申請できないをチェックする
		hasError = monthActualConfirmDoneCheck.check(false, appLimitSetting.isCanAppAchievementMonthConfirm(), companyID, employeeID, appDate);
		if (hasError == true) {
			throw new BusinessException("Msg_449");
		}
		
		// ドメインモデル「申請制限設定」．就業確定済の場合申請できないをチェックする
		hasError = workConfirmDoneCheck.check(appLimitSetting.isCanAppFinishWork(), companyID, employeeID, appDate);
		if (hasError == true) {
			throw new BusinessException("Msg_450");
		}

		// ドメインモデル「申請制限設定」．実績修正がロック状態なら申請できないをチェックする
		hasError = actualLockingCheck.check(appLimitSetting.isCanAppAchievementLock(), companyID, employeeID, appDate);
		if (hasError == true) {
			throw new BusinessException("Msg_451");
		}
	}
	public void confirmCheckOvertime(String companyID, String employeeID, GeneralDate appDate, AppDispInfoStartupOutput appDispInfoStartupOutput){
		AppLimitSetting appLimitSetting = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppLimitSetting();
		boolean hasError = false;
		// ドメインモデル「申請制限設定」．月別実績が確認済なら申請できないをチェックする
		hasError = monthActualConfirmDoneCheck.check(true, appLimitSetting.isCanAppAchievementMonthConfirm(), companyID, employeeID, appDate);
		if (hasError == true) {
			throw new BusinessException("Msg_449");
		}
		
		// ドメインモデル「申請制限設定」．就業確定済の場合申請できないをチェックする
		hasError = workConfirmDoneCheck.check(appLimitSetting.isCanAppFinishWork(), companyID, employeeID, appDate);
		if (hasError == true) {
			throw new BusinessException("Msg_450");
		}

		// ドメインモデル「申請制限設定」．実績修正がロック状態なら申請できないをチェックする
		hasError = actualLockingCheck.check(appLimitSetting.isCanAppAchievementLock(), companyID, employeeID, appDate);
		if (hasError == true) {
			throw new BusinessException("Msg_451");
		}
	}

	@Override
	public List<ConfirmMsgOutput> processBeforeRegister_New(String companyID, EmploymentRootAtr employmentRootAtr, boolean agentAtr,
			Application application, OvertimeAppAtr overtimeAppAtr, ErrorFlagImport errorFlg,
			List<GeneralDate> lstDateHd, AppDispInfoStartupOutput appDispInfoStartupOutput) {
		List<ConfirmMsgOutput> result = new ArrayList<>();
		// アルゴリズム「未入社前チェック」を実施する
		retirementCheckBeforeJoinCompany(companyID, application.getEmployeeID(), application.getAppDate().getApplicationDate());
		
		// アルゴリズム「社員の当月の期間を算出する」を実行する
		PeriodCurrentMonth periodCurrentMonth = otherCommonAlgorithmService.employeePeriodCurrentMonthCalculate(
				companyID, application.getEmployeeID(), application.getAppDate().getApplicationDate());
		
		Optional<GeneralDate> opStartDate = application.getOpAppStartDate().map(x -> x.getApplicationDate());
		Optional<GeneralDate> opEndDate = application.getOpAppEndDate().map(x -> x.getApplicationDate());
		if (opStartDate.isPresent() && opEndDate.isPresent()) {
			// 登録する期間のチェック
			//((TimeSpan)(申請する終了日 - 申請する開始日)).Days > 31がtrue
			if((ChronoUnit.DAYS.between(opStartDate.get().localDate(), opEndDate.get().localDate()) + 1)  > 31){
				throw new BusinessException("Msg_277");
			}
			// 登録可能期間のチェック(１年以内)
			//EA修正履歴 No.3210
			//hoatt 2019.03.22
			if(periodCurrentMonth.getStartDate().addYears(1).beforeOrEquals(opEndDate.get())) {
				//締め期間．開始年月日.AddYears(1) <= 申請する終了日がtrue
				// エラーメッセージメッセージ（Msg_1518）
				throw new BusinessException("Msg_1518", periodCurrentMonth.getStartDate().addYears(1).toString(DATE_FORMAT));
			}
			
			// 過去月のチェック
			if(opStartDate.get().before(periodCurrentMonth.getStartDate())) {
				throw new BusinessException("Msg_236");			
			}
		}		
		
		// キャッシュから承認ルートを取得する(Lấy comfirm root từ cache)	
		switch (errorFlg) {
		case NO_CONFIRM_PERSON:
			throw new BusinessException("Msg_238");
		case APPROVER_UP_10:
			throw new BusinessException("Msg_237");
		case NO_APPROVER:
			throw new BusinessException("Msg_324");
		default:
			break;
		}

		// アルゴリズム「申請の締め切り期限をチェック」を実施する
		deadlineAppCheck(opStartDate.get(), opEndDate.get(), 
				periodCurrentMonth.getStartDate(), periodCurrentMonth.getEndDate(), appDispInfoStartupOutput.getAppDispInfoWithDateOutput());
		
		// アルゴリズム「申請の受付制限をチェック」を実施する
		appAcceptanceRestrictionsCheck(application.getPrePostAtr(), opStartDate.get(), opEndDate.get(), 
				application.getAppType(), overtimeAppAtr, appDispInfoStartupOutput.getAppDispInfoNoDateOutput());
		
		// 申請する開始日～申請する終了日までループする
		for(GeneralDate loopDate = opStartDate.get(); loopDate.beforeOrEquals(opEndDate.get()); loopDate = loopDate.addDays(1)){
            //hoatt 2019/10/14 #109087を対応
            if(lstDateHd != null && lstDateHd.contains(loopDate)){
                continue;
            }
			if(loopDate.equals(GeneralDate.today()) && application.getPrePostAtr().equals(PrePostAtr.PREDICT) && 
					application.getAppType() == ApplicationType.OVER_TIME_APPLICATION){
				// アルゴリズム「6.確定チェック（事前残業申請用）」を実施する
				confirmCheckOvertime(companyID, application.getEmployeeID(), loopDate, appDispInfoStartupOutput);
			}else{
				// アルゴリズム「確定チェック」を実施する
				confirmationCheck(companyID, application.getEmployeeID(), loopDate, appDispInfoStartupOutput);
			}
		}
		return result;
	}
}
