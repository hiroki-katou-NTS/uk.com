package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatusCheckResult;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreAppCheckResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;

/**
 * 休日出勤申請起動時の表示情報
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@Getter
@Setter
public class AppHdWorkDispInfoOutput_Old {
	
	/**
	 * 申請表示情報
	 */
	private AppDispInfoStartupOutput appDispInfoStartupOutput;
	
	/**
	 * 休出申請指示
	 */
	private WithdrawalAppSet withdrawalAppSet;
	
	/**
	 * 申請用時間外労働時間
	 */
	private AgreeOverTimeOutput agreeOverTimeOutput;
	
	/**
	 * 休出時間枠
	 */
	private List<WorkdayoffFrame> breaktimeFrames;
	
	/**
	 * 乖離理由の入力を利用する
	 */
	private boolean useInputDivergenceReason;
	
	/**
	 * 乖離理由の選択肢を利用する
	 */
	private boolean useComboDivergenceReason;
	
	/**
	 * 休日出勤申請起動時の表示情報(申請対象日関係あり)
	 */
	private HdWorkDispInfoWithDateOutput_Old hdWorkDispInfoWithDateOutput;
	
	/**
	 * 残業時間枠
	 */
	private String overtimeFrame;
	
	/**
	 * フレックス時間を表示する区分
	 */
	private boolean dispFlexTime;
	
	/**
	 * 乖離理由の選択肢
	 */
	private Optional<List<DivergenceReason>> comboDivergenceReason;
	
	private OvertimeRestAppCommonSetting overtimeRestAppCommonSetting;
	
	private PreAppCheckResult preAppCheckResult;
	
	private ActualStatusCheckResult actualStatusCheckResult;
	
}
