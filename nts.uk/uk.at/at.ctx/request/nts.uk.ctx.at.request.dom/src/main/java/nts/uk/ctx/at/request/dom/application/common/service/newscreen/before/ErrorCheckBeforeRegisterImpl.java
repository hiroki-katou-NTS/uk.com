package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeCheckResult;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;

@Stateless
public class ErrorCheckBeforeRegisterImpl implements IErrorCheckBeforeRegister {

	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	
	@Inject
	private ApplicationRepository appRepository;
	
	// @Inject
	// private PersonalLaborConditionRepository
	// personalLaborConditionRepository;
	/**
	 * 申請詳細設定
	 */

	/**
	 * 03-02_実績超過チェック
	 */
	@Override
	public void OvercountCheck(String companyId, GeneralDate appDate, PrePostAtr prePostAtr) {
		// 当日の場合
		GeneralDate systemDate = GeneralDate.today();
		// 1. チェック条件
		if (!this.confirmCheck(companyId, prePostAtr)) {
			// Falseの場合
			return;
		}
		// TODO: 2. 就業時間帯を取得(Wait common function)
		// TODO: ドメインモデル「申請詳細設定」.時刻計算利用区分
		// 3. 申請日の判断
		// 当日の場合
		if (appDate.compareTo(systemDate) == 0) {
			// TODO: Wait request
			// 当日の場合
			this.onDayCheck();
		} else {
			// TODO: Wait request
			// 当日以外の場合
			// メッセージを表示する(Msg_423)
			this.outsideDayCheck();
		}
	}

	@Override
	public void TimeUpperLimitYearCheck() {
		// TODO Auto-generated method stub

	}

	/**
	 * 03-05_事前否認チェック
	 */
	@Override
	public OvertimeCheckResult preliminaryDenialCheck(String companyId, String employeeID, GeneralDate appDate, GeneralDateTime inputDate,
			PrePostAtr prePostAtr,int appType) {
		OvertimeCheckResult result = new OvertimeCheckResult();
		result.setErrorCode(0);
		// ドメインモデル「申請」
//		List<Application_New> beforeApplication = appRepository.getBeforeApplication(companyId, employeeID, appDate,
//				appType, PrePostAtr.PREDICT.value);
//		if (beforeApplication.isEmpty()) {
//			return result;
//		}
//		//承認区分が否認かチェック
//		//ドメインモデル「申請」．「反映情報」．実績反映状態をチェックする
//		ReflectedState_New stateLatestApp = beforeApplication.get(0).getReflectionInformation().getStateReflectionReal();
//		//否認、差戻しの場合
//		if (stateLatestApp.equals(ReflectedState_New.DENIAL) || stateLatestApp.equals(ReflectedState_New.REMAND)) {
//			result.setConfirm(true);
//			return result;
//		}
		//その以外
		return result;
	}

	/**
	 * 当日以外の場合
	 */
	private void outsideDayCheck() {
	}

	/**
	 * 当日の場合
	 */
	private void onDayCheck() {
	}

	/**
	 * チェック条件
	 * 
	 * @return True：チェックをする, False：チェックをしない
	 */
	private boolean confirmCheck(String companyId, PrePostAtr prePostAtr) {
		// 事前事後区分チェック
		if (prePostAtr.equals(PrePostAtr.PREDICT)) {
			return false;
		}
		// ドメインモデル「残業休出申請共通設定」を取得
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyId, ApplicationType.OVER_TIME_APPLICATION.value);
		if (overtimeRestAppCommonSet.isPresent()) {
			// 残業休出申請共通設定.事前表示区分＝表示する
			if (overtimeRestAppCommonSet.get().getPreExcessDisplaySetting().equals(UseAtr.USE)) {
				// 表示する:Trueを返す
				return true;
			}
		}
		return false;
	}
}
