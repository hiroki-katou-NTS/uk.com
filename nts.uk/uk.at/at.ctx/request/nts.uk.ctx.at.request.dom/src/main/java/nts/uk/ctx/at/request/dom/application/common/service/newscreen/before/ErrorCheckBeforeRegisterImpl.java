package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.overtime.primitivevalue.OvertimeAppPrimitiveTime;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCommon;

@Stateless
public class ErrorCheckBeforeRegisterImpl implements IErrorCheckBeforeRegister {

	/** アルゴリズム「1-1.新規画面起動前申請共通設定を取得する」を実行する */
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;

	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;

	//@Inject
	//private PersonalLaborConditionRepository personalLaborConditionRepository;
	/**
	 * 申請詳細設定
	 */
	//@Inject
	//private RequestOfEachCompanyRepository requestRepo;
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
		RequestOfEachCommon requestSetting = appCommonSettingOutput.requestOfEachCommon;
		if (null == requestSetting) {
			// 終了
			return;
		}
		for (RequestAppDetailSetting appSetting : requestSetting.getRequestAppDetailSettings()) {
			// 申請詳細設定.時刻計算利用区分=利用する
			if (appSetting.getTimeCalUseAtr().equals(UseAtr.USE)) {
				// 計算フラグのチェック
				if (CalculateFlg == 1) {
					// 計算フラグ=1の場合:メッセージを表示する(Msg_750)
					throw new BusinessException("Msg_750");
				}
				// 終了
				break;
			}
		}
	}

	/**
	 * 03-01_事前申請超過チェック
	 */
	@Override
	public int preApplicationExceededCheck(String companyId, ReflectPlanPerState refPlan,
			List<OvertimeAppPrimitiveTime> preAppTimeInputs, List<OvertimeAppPrimitiveTime> afterAppTimeInputs) {
		PrePostAtr prePostAtr1= PrePostAtr.POSTERIOR;
		String EmployeeId = "";
		// チェック条件を確認		
		if (!this.confirmCheck(companyId, prePostAtr1)) {
			return 0;
		}
		// ドメインモデル「残業申請」を取得
		
		// 事前申請否認チェック
		// 否認以外：
		// 反映情報.実績反映状態＝未反映、反映済、反映待ち
		if (refPlan.equals(ReflectPlanPerState.NOTREFLECTED) || refPlan.equals(ReflectPlanPerState.NOTREFLECTED)
				|| refPlan.equals(ReflectPlanPerState.WAITREFLECTION)) {
			// 背景色を設定する
			return 1;
		}
		// 残業時間１～１０、加給時間
		// すべての残業枠をチェック
		if (preAppTimeInputs.isEmpty() || afterAppTimeInputs.isEmpty()
				|| (preAppTimeInputs.size() != afterAppTimeInputs.size())) {
			return 0;
		}
		for (int i = 0; i < preAppTimeInputs.size(); i++) {
			// 事前申請の申請時間＞事後申請の申請時間
			if (preAppTimeInputs.get(i).v() > afterAppTimeInputs.get(i).v()) {
				// 背景色を設定する
				return 1;
			}
		}
		return 0;
	}
	/**
	 * 03-02_実績超過チェック
	 */
	@Override
	public void OvercountCheck(String companyId, GeneralDate appDate, UseAtr usAtr) {
		//当日の場合
		GeneralDate systemDate = GeneralDate.today();
		//1. チェック条件
		PrePostAtr prePostAtr1= PrePostAtr.POSTERIOR;
		if (!this.confirmCheck(companyId, prePostAtr1)) {
			//Falseの場合
			return ;
		}
		//TODO: 2. 就業時間帯を取得(Wait common function)
		//TODO: ドメインモデル「申請詳細設定」.時刻計算利用区分
		//3. 申請日の判断
		//当日の場合
		if(appDate.compareTo(systemDate) == 0){
			//TODO: Wait request
			//当日の場合
			this.onDayCheck();
		}else{
			//TODO: Wait request
			//当日以外の場合
			//メッセージを表示する(Msg_423)
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

	@Override
	public void preliminaryDenialCheck() {
		// TODO Auto-generated method stub

	}
	/**
	 * 当日以外の場合
	 */
	private void outsideDayCheck(){}
	/**
	 * 当日の場合
	 */
	private void onDayCheck(){ }
	/**
	 * チェック条件
	 * @return True：チェックをする, False：チェックをしない
	 */
	private boolean confirmCheck(String companyId, PrePostAtr prePostAtr) {
		// 事前事後区分チェック
		if (prePostAtr.equals(PrePostAtr.PREDICT)) {
			return false;
		}
		// ドメインモデル「残業休出申請共通設定」を取得
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyId,prePostAtr.value);
		if (overtimeRestAppCommonSet.isPresent()) {
			//残業休出申請共通設定.事前表示区分＝表示する
			if (overtimeRestAppCommonSet.get().getPreDisplayAtr().equals(UseAtr.USE)) {
				// 表示する:Trueを返す
				return true;
			}
		}
		return false;
	}
}
