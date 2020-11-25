package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatusCheckResult;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreAppCheckResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonSelect;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;

/**
 * 休日出勤申請起動時の表示情報
 * @author huylq
 *Refactor5
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppHdWorkDispInfoOutput {
	
	/**
	 * フレックス時間を表示する区分
	 */
	private NotUseAtr dispFlexTime;
	
	/**
	 * 乖離理由の入力を利用する
	 */
	private boolean useInputDivergenceReason;
	
	/**
	 * 乖離理由の選択肢を利用する
	 */
	private boolean useComboDivergenceReason;
	
	/**
	 * 休出時間枠
	 */
	private List<WorkdayoffFrame> workdayoffFrameList;
	
	/**
	 * 休出申請設定
	 */
	private HolidayWorkAppSet holidayWorkAppSet;

	/**
	 * 休日出勤申請起動時の表示情報(申請対象日関係あり)
	 */
	private HdWorkDispInfoWithDateOutput hdWorkDispInfoWithDateOutput;
	
	/**
	 * 残業休日出勤申請の反映
	 */
	private AppReflectOtHdWork hdWorkOvertimeReflect;
	
	/**
	 * 残業時間枠
	 */
	private List<OvertimeWorkFrame> overtimeFrameList;
	
	/**
	 * 申請用時間外労働時間
	 */
	private AgreeOverTimeOutput otWorkHoursForApplication;
	
	/**
	 * 申請表示情報
	 */
	private AppDispInfoStartupOutput appDispInfoStartupOutput;
	
	/**
	 * 乖離理由の選択肢
	 */
	private Optional<DivergenceReasonSelect> comboDivergenceReason = Optional.empty();
	
	/**
	 * 計算結果
	 */
	private Optional<HolidayWorkCalculationResult> calculationResult = Optional.empty();
}
