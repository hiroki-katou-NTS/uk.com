package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.CalcStampMiss;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.OverrideSet;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

public interface PreActualColorCheck {
	
	/**
	 * 07_事前申請・実績超過チェック
	 * @param preExcessDisplaySetting 事前超過表示設定
	 * @param performanceExcessAtr 実績超過表示設定
	 * @param appType 申請種類
	 * @param prePostAtr 事前事後区分
	 * @param calcTimeList 入力値リスト
	 * @param overTimeLst 計算値リスト
	 * @param opAppBefore 事前申請
	 * @param beforeAppStatus 事前申請状態
	 * @param actualLst 実績
	 * @param actualStatus 実績状態
	 * @return 
	 */
	public PreActualColorResult preActualColorCheck(UseAtr preExcessDisplaySetting, AppDateContradictionAtr performanceExcessAtr,
			ApplicationType appType, PrePostAtr prePostAtr, List<OvertimeInputCaculation> calcTimeList, List<OvertimeColorCheck> overTimeLst, 
			Optional<Application> opAppBefore, boolean beforeAppStatus, List<OvertimeColorCheck> actualLst, ActualStatus actualStatus);
	
	/**
	 * 07-01_事前申請状態チェック
	 * @param companyID 会社ID
	 * @param employeeID 申請者
	 * @param appDate 申請日
	 * @param appType 申請種類
	 * @return
	 */
	public PreAppCheckResult preAppStatusCheck(String companyID, String employeeID, GeneralDate appDate, ApplicationType appType);
	
	/**
	 * 07-02_実績取得・状態チェック
	 * @param companyID 会社ID
	 * @param employeeID 申請者
	 * @param appDate 申請日
	 * @param appType 申請種類
	 * @param workType 勤務種類コード
	 * @param workTime 就業時間帯コード
	 * @param overrideSet 退勤時刻優先設定
	 * @param calStampMiss 退勤打刻漏れ補正設定
	 * @return
	 */
	public ActualStatusCheckResult actualStatusCheck(String companyID, String employeeID, GeneralDate appDate, ApplicationType appType, 
			String workType, String workTime, OverrideSet overrideSet, Optional<CalcStampMiss> calStampMiss, List<DeductionTime> deductionTimeLst);
	
	/**
	 * 07-02-2-1_当日判定
	 * @param appDate 申請日
	 * @param workTime 就業時間帯コード
	 * @return
	 */
	public boolean judgmentToday(GeneralDate appDate, String workTime);

	/**
	 * 07-02-2-2_勤務分類変更の判定
	 * @param companyID 会社ID
	 * @param appType 申請種類
	 * @param actualWorkType 実績勤務種類
	 * @param screenWorkType 画面勤務種類
	 * @return
	 */
	public JudgmentWorkTypeResult judgmentWorkTypeChange(String companyID, ApplicationType appType, String actualWorkType, String screenWorkType);
	
	/**
	 * 07-02-2-3_就業時間帯変更の判定
	 * @param actualWorkTime 実績就業時間帯
	 * @param screenWorkTime 画面就業時間帯
	 * @return
	 */
	public JudgmentWorkTimeResult judgmentWorkTimeChange(String actualWorkTime, String screenWorkTime);
	
	/**
	 * 07-02-2-4_打刻漏れと退勤打刻補正の判定
	 * @param isToday 当日フラグ
	 * @param overrideSet 退勤時刻優先設定
	 * @param calStampMiss 退勤打刻漏れ補正設定
	 * @param startTime 実績出勤打刻
	 * @param endTime 実績退勤打刻
	 * @return
	 */
	public JudgmentStampResult judgmentStamp(boolean isToday, OverrideSet overrideSet, Optional<CalcStampMiss> calStampMiss, Integer startTime, Integer endTime,
			GeneralDate appDate);
	
	/**
	 * 07-02-2-4-1_退勤打刻補正の判定(実績あり)
	 * @param isToday 当日フラグ
	 * @param calStampMiss 退勤時刻優先設定
	 * @return
	 */
	public boolean judgmentStampTimeFull(boolean isToday, OverrideSet overrideSet);
	
	/**
	 * 07-02-2-4-2_退勤打刻補正の判定(打刻漏れ)
	 * @param isToday 当日フラグ
	 * @param calStampMiss 退勤打刻漏れ補正設定
	 * @return
	 */
	public boolean judgmentStampTimeMiss(boolean isToday, Optional<CalcStampMiss> calStampMiss);
	
	/**
	 * 07-02-2-5_実績状態の判定
	 * @param missStamp 打刻漏れフラグ
	 * @param stampLeaveChange 退勤打刻補正
	 * @return
	 */
	public ActualStatus judgmentActualStatus(boolean missStamp, boolean stampLeaveChange);
	
	/**
	 * 07-02-2-6_仮計算実行の判定
	 * @param actualStatus 実績状態
	 * @param workTypeChange 勤務分類変更
	 * @param stampLeaveChange 退勤打刻補正
	 * @param workTimeChange 就業時間帯変更
	 * @return
	 */
	public boolean judgmentCalculation(ActualStatus actualStatus, boolean workTypeChange, boolean stampLeaveChange, boolean workTimeChange);
	
	/**
	 * 07-01-3_枠別事前申請超過チェック
	 * @param appType 勤怠種類
	 * @param overtimeColorCheck 対象枠, 入力値
	 * @param opAppBefore 事前申請
	 */
	public void preAppErrorCheck(ApplicationType appType, OvertimeColorCheck overtimeColorCheck, Optional<Application> opAppBefore, UseAtr preAppSetCheck);
	
	/**
	 * 07-02-3_枠別実績超過チェック
	 * @param overtimeColorCheck 対象枠, 入力値
	 * @param actualLst 実績
	 */
	public void actualErrorCheck(OvertimeColorCheck overtimeColorCheck, List<OvertimeColorCheck> actualLst, AppDateContradictionAtr actualSetCheck);
}
