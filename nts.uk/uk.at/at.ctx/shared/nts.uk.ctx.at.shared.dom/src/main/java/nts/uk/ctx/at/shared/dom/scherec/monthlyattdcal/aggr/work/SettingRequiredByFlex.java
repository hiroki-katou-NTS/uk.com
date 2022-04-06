package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.FlexAggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.FlexShortageLimit;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMnt;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.MonthlyAggrSetOfFlex;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;

/**
 * フレックス勤務が必要とする設定
 * @author shuichi_ishida
 */
@Setter
@Getter
public class SettingRequiredByFlex {

	/** フレックス時間勤務の月の集計設定 */
	private FlexMonthWorkTimeAggrSet flexAggrSet;
	/** フレックス勤務の月別集計設定 */
	private Optional<MonthlyAggrSetOfFlex> monthlyAggrSetOfFlexOpt;
	/** フレックス勤務の日別計算設定 */
	private FlexSet dailyCalcSetOfFlex;
	/** 会社別フレックス勤務集計方法 */
	private Optional<ComFlexMonthActCalSet> comFlexSetOpt;
	/** フレックス不足の年休補填管理 */
	private Optional<InsufficientFlexHolidayMnt> insufficientFlexOpt;
	/** フレックス不足の繰越上限管理 */
	private Optional<FlexShortageLimit> flexShortageLimitOpt;
	/** 休暇加算時間設定 */
	private Map<String, AggregateRoot> holidayAdditionMap;
	/** 月間法定労働時間 */
	private AttendanceTimeMonth statutoryWorkingTimeMonth;
	/** 月間所定労働時間 */
	private AttendanceTimeMonth prescribedWorkingTimeMonth;
	/** 週平均時間 */
	private AttendanceTimeMonth weekAverageTime;
	/** 翌月繰越可能時間 */
	private AttendanceTimeMonth canNextCarryforwardTimeMonth;
	/** 休出枠の役割 */
	private Map<Integer, WorkdayoffFrame> roleHolidayWorkFrameMap;
	/** 月別実績集計のフレックス集計方法 */
	private FlexAggregateMethodOfMonthly flexAggregateMethodMonthly;
	
	/**
	 * コンストラクタ
	 */
	public SettingRequiredByFlex() {
		
		this.flexAggrSet = null;
		this.monthlyAggrSetOfFlexOpt = Optional.empty();
		this.dailyCalcSetOfFlex = new FlexSet();
		this.comFlexSetOpt = Optional.empty();
		this.insufficientFlexOpt = Optional.empty();
		this.flexShortageLimitOpt = Optional.empty();
		this.holidayAdditionMap = new HashMap<>();
		this.statutoryWorkingTimeMonth = new AttendanceTimeMonth(0);
		this.prescribedWorkingTimeMonth = new AttendanceTimeMonth(0);
		this.weekAverageTime = new AttendanceTimeMonth(0);
		this.canNextCarryforwardTimeMonth = new AttendanceTimeMonth(0);
		this.roleHolidayWorkFrameMap = new HashMap<>();
		this.flexAggregateMethodMonthly = new FlexAggregateMethodOfMonthly();
	}
}
