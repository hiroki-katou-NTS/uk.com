package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeCheckResult;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeInputRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
//import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;

@Stateless
public class ErrorCheckBeforeRegisterImpl implements IErrorCheckBeforeRegister {

	/** アルゴリズム「1-1.新規画面起動前申請共通設定を取得する」を実行する */
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;

	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;

	@Inject
	private ApplicationRepository_New appRepository;
	@Inject
	private OvertimeInputRepository overtimeInputRepository;
	// @Inject
	// private PersonalLaborConditionRepository
	// personalLaborConditionRepository;
	/**
	 * 申請詳細設定
	 */

	// @Inject
	// private RequestOfEachCompanyRepository requestRepo;
	/**
	 * 03-06_計算ボタンチェック
	 */
	@Override
	public void calculateButtonCheck(int CalculateFlg, String companyID, String employeeID, int rootAtr,
			ApplicationType targetApp, GeneralDate appDate) {
		// Get setting info
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet
				.prelaunchAppCommonSetService(companyID, employeeID, rootAtr, targetApp, appDate);
		// 時刻計算利用する場合にチェックしたい
		ApprovalFunctionSetting requestSetting = appCommonSettingOutput.approvalFunctionSetting;
		if (null == requestSetting) {
			// 終了
			return;
		}
		// 申請詳細設定.時刻計算利用区分=利用する
		if (requestSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE)) {
			// 計算フラグのチェック
			if (CalculateFlg == 1) {
				// 計算フラグ=1の場合:メッセージを表示する(Msg_750)
				throw new BusinessException("Msg_750");
			}
		}
	}

	/**
	 * 03-01_事前申請超過チェック
	 */
	@Override
	public OvertimeCheckResult preApplicationExceededCheck(String companyId, GeneralDate appDate, GeneralDateTime inputDate,
			PrePostAtr prePostAtr, int attendanceId, List<OverTimeInput> overtimeInputs) {
		OvertimeCheckResult result = new OvertimeCheckResult();
		result.setFrameNo(-1);
		// 社員ID
		// String EmployeeId = AppContexts.user().employeeId();
		// チェック条件を確認
		if (!this.confirmCheck(companyId, prePostAtr)) {
			result.setErrorCode(0);
			return result;
		}
		// ドメインモデル「申請」を取得
		// 事前申請漏れチェック
		List<Application_New> beforeApplication = appRepository.getBeforeApplication(companyId, appDate, inputDate,
				ApplicationType.OVER_TIME_APPLICATION.value, PrePostAtr.PREDICT.value);
		if (beforeApplication.isEmpty()) {
			// TODO: QA Pending
			result.setErrorCode(1);
			return result;
		}
		// 事前申請否認チェック
		// 否認以外：
		// 反映情報.実績反映状態＝ 否認、差し戻し
		ReflectedState_New refPlan = beforeApplication.get(0).getReflectionInformation().getStateReflectionReal();
		if (refPlan.equals(ReflectedState_New.DENIAL) || refPlan.equals(ReflectedState_New.REMAND)) {
			// 背景色を設定する
			result.setErrorCode(1);
			return result;
		}
		String beforeCid = beforeApplication.get(0).getCompanyID();
		String beforeAppId = beforeApplication.get(0).getAppID();

		// 事前申請の申請時間
		List<OverTimeInput> beforeOvertimeInputs = overtimeInputRepository.getOvertimeInput(beforeCid, beforeAppId)
				.stream()
				.filter(item -> item.getAttendanceType() == EnumAdaptor.valueOf(attendanceId, AttendanceType.class))
				.collect(Collectors.toList());
		if (beforeOvertimeInputs.isEmpty()) {
			result.setErrorCode(0);
			return result;
		}
		// 残業時間１～１０、加給時間
		// すべての残業枠をチェック
		for (int i = 0; i < overtimeInputs.size(); i++) {
			OverTimeInput afterTime = overtimeInputs.get(i);
			int frameNo = afterTime.getFrameNo();
			Optional<OverTimeInput> beforeTimeOpt = beforeOvertimeInputs.stream()
					.filter(item -> item.getFrameNo() == frameNo).findFirst();
			if (!beforeTimeOpt.isPresent()) {
				continue;
			}
			OverTimeInput beforeTime = beforeTimeOpt.get();
			if (null == beforeTime) {
				continue;
			}
			// 事前申請の申請時間＞事後申請の申請時間
			if (beforeTime.getApplicationTime() != null && afterTime.getApplicationTime() != null && beforeTime.getApplicationTime().v() < afterTime.getApplicationTime().v()) {
				// 背景色を設定する
				result.setErrorCode(1);
				result.setFrameNo(frameNo);
				return result;
			}
		}
		result.setErrorCode(0);
		return result;
	}

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
	public void TimeUpperLimitMonthCheck() {
		// TODO Auto-generated method stub

	}

	@Override
	public void TimeUpperLimitYearCheck() {
		// TODO Auto-generated method stub

	}

	/**
	 * 03-05_事前否認チェック
	 */
	@Override
	public OvertimeCheckResult preliminaryDenialCheck(String companyId, GeneralDate appDate, GeneralDateTime inputDate,
			PrePostAtr prePostAtr,int appType) {
		OvertimeCheckResult result = new OvertimeCheckResult();
		result.setErrorCode(0);
		// ドメインモデル「申請」
		List<Application_New> beforeApplication = appRepository.getBeforeApplication(companyId, appDate, inputDate,
				appType, PrePostAtr.PREDICT.value);
		if (beforeApplication.isEmpty()) {
			return result;
		}
		//承認区分が否認かチェック
		//ドメインモデル「申請」．「反映情報」．実績反映状態をチェックする
		ReflectedState_New stateLatestApp = beforeApplication.get(0).getReflectionInformation().getStateReflectionReal();
		//否認、差戻しの場合
		if (stateLatestApp.equals(ReflectedState_New.DENIAL) || stateLatestApp.equals(ReflectedState_New.REMAND)) {
			result.setConfirm(true);
			return result;
		}
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
			if (overtimeRestAppCommonSet.get().getPreDisplayAtr().equals(UseAtr.USE)) {
				// 表示する:Trueを返す
				return true;
			}
		}
		return false;
	}
}
