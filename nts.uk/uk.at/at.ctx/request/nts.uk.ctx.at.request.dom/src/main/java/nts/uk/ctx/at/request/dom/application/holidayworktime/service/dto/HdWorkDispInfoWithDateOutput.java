package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatus;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkHours;
import nts.uk.ctx.at.request.dom.workrecord.dailyrecordprocess.dailycreationwork.BreakTimeZoneSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeName;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;

/**
 *  休日出勤申請起動時の表示情報(申請対象日関係あり)
 * @author huylq
 *Refactor5
 */
@NoArgsConstructor
@Getter
@Setter
public class HdWorkDispInfoWithDateOutput {
	
	/**
	 * 代休管理区分
	 */
	private boolean subHdManage;
	
	/**
	 * 勤務時間
	 */
	private WorkHours workHours;
	
	/**
	 * 休憩時間帯設定リスト
	 */
	private Optional<BreakTimeZoneSetting> breakTimeZoneSettingList;
	
	/**
	 * 勤務種類リスト
	 */
	private Optional<List<WorkType>> workTypeList;
	
	/**
	 * 初期選択勤務種類
	 */
	private Optional<WorkTypeCode> initWorkType;
	
	/**
	 * 初期選択勤務種類名称
	 */
	private Optional<WorkTypeName> initWorkTypeName;
	
	/**
	 * 初期選択就業時間帯
	 */
	private Optional<WorkTimeCode> initWorkTime;
	
	/**
	 *初期選択就業時間帯名称
	 */
	private Optional<WorkTimeName> initWorkTimeName;
	
	/**
	 * 勤怠時間の超過状態
	 */
	private OvertimeStatus overtimeStatus;
	
	/**
	 * 実績の申請時間
	 */
	private Optional<ApplicationTime> actualApplicationTime;
	
	/**
	 * 月別実績の36協定時間状態
	 */
	private Optional<AgreementTimeStatusOfMonthly> actualMonthlyAgreeTimeStatus;
}
