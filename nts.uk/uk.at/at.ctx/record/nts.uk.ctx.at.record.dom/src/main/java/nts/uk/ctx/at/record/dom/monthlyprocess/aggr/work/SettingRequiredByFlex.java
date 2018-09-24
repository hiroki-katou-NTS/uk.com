package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.monthly.workform.flex.MonthlyAggrSetOfFlex;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.flex.FlexShortageLimit;
import nts.uk.ctx.at.shared.dom.calculation.holiday.flex.InsufficientFlexHolidayMnt;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriod;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.flex.GetFlexPredWorkTime;

/**
 * フレックス勤務が必要とする設定
 * @author shuichi_ishida
 */
@Getter
public class SettingRequiredByFlex {

	/** フレックス時間勤務の月の集計設定 */
	@Setter
	private FlexMonthWorkTimeAggrSet flexAggrSet;
	/** フレックス勤務の月別集計設定 */
	@Setter
	private Optional<MonthlyAggrSetOfFlex> monthlyAggrSetOfFlexOpt;
	/** フレックス勤務所定労働時間取得 */
	@Setter
	private Optional<GetFlexPredWorkTime> getFlexPredWorkTimeOpt;
	/** フレックス不足の年休補填管理 */
	@Setter
	private Optional<InsufficientFlexHolidayMnt> insufficientFlexOpt;
	/** フレックス不足の繰越上限管理 */
	@Setter
	private Optional<FlexShortageLimit> flexShortageLimitOpt;
	/** 休暇加算時間設定 */
	private Map<String, AggregateRoot> holidayAdditionMap;
	/** 月間法定労働時間 */
	@Setter
	private AttendanceTimeMonth statutoryWorkingTimeMonth;
	/** 月間所定労働時間 */
	@Setter
	private AttendanceTimeMonth prescribedWorkingTimeMonth;
	/** 休出枠の役割 */
	private Map<Integer, RoleOfOpenPeriod> roleHolidayWorkFrameMap;

	/**
	 * コンストラクタ
	 */
	public SettingRequiredByFlex(){
		
		this.flexAggrSet = null;
		this.monthlyAggrSetOfFlexOpt = Optional.empty();
		this.getFlexPredWorkTimeOpt = Optional.empty();
		this.insufficientFlexOpt = Optional.empty();
		this.flexShortageLimitOpt = Optional.empty();
		this.holidayAdditionMap = new HashMap<>();
		this.statutoryWorkingTimeMonth = new AttendanceTimeMonth(0);
		this.prescribedWorkingTimeMonth = new AttendanceTimeMonth(0);
		this.roleHolidayWorkFrameMap = new HashMap<>();
	}
}
