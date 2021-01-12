package nts.uk.ctx.at.request.dom.application.common.service.print;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonInputMethod;

/**
 * 休日出勤申請の印刷内容
 * @author huylq
 * Refactor5
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrintContentOfHolidayWork {
	
//	/**
//	 * 乖離理由の入力を利用する
//	 */
//	private boolean useInputDivergenceReason;
//	
//	/**
//	 * 乖離理由の選択肢を利用する
//	 */
//	private boolean useComboDivergenceReason;
	
	/**
	 * 乖離時間枠
	 */
	private List<DivergenceTimeRoot> divergenceTimeRoots = Collections.emptyList();
	
	/**
	 * 乖離理由を反映する
	 */
	private NotUseAtr divergenceReasonReflect;
	
	/**
	 * 休出枠
	 */
	private List<WorkdayoffFrame> workdayoffFrameList;
	
	/**
	 * 休憩を反映する
	 */
	private NotUseAtr breakReflect;
	
	/**
	 * 利用する乖離理由
	 */
	private List<DivergenceReasonInputMethod> divergenceReasonInputMethod = Collections.emptyList();
	
	/**
	 * 勤務種類コード
	 */
	private WorkTypeCode workTypeCode;
	
	/**
	 * 就業時間帯コード
	 */
	private WorkTimeCode workTimeCode;
	
	/**
	 * 時間外深夜利用区分
	 */
	private NotUseAtr overtimeMidnightUseAtr;
	
	/**
	 * 残業時間枠
	 */
	private List<OvertimeWorkFrame> overtimeFrameList;
	
	/**
	 * 休憩時間帯
	 */
	private Optional<List<TimeZoneWithWorkNo>> breakTimeList = Optional.empty();
	
	/**
	 * 勤務時間帯
	 */
	private Optional<List<TimeZoneWithWorkNo>> workingTimeList = Optional.empty();
	
	/**
	 *勤務種類名
	 */
	private Optional<WorkTypeName> workTypeName = Optional.empty();
	
	/**
	 *就業時間帯名
	 */
	private Optional<WorkTimeName> workTimeName = Optional.empty();
	
	/**
	 * 申請時間
	 */
	private ApplicationTime applicationTime;
}
