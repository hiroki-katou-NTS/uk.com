package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.CalcStampMiss;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.OverrideSet;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

public interface PreActualColorCheck {
	
	
	
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
	 * Refactor5 07-02_実績取得・状態チェック
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム(残業・休出).07-02_実績取得・状態チェック
	 * @param companyId
	 * @param employeeId
	 * @param date
	 * @param appType
	 * @param workTypeCode
	 * @param workTimeCode
	 * @param overrideSet
	 * @param calOptional
	 * @param breakTimes
	 * @param acuActualContentDisplay
	 * @return
	 */
	public ApplicationTime checkStatus(
			String companyId,
			String employeeId,
			GeneralDate date,
			ApplicationType appType,
			WorkTypeCode workTypeCode,
			WorkTimeCode workTimeCode,
			OverrideSet overrideSet,
			Optional<CalcStampMiss> calOptional,
			List<DeductionTime> breakTimes,
			Optional<ActualContentDisplay> acuActualContentDisplay
			
			);
	
	
}
