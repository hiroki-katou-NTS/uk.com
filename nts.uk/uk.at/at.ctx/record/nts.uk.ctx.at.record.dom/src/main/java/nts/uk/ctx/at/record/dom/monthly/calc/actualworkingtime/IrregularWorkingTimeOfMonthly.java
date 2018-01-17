package nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の変形労働時間
 * @author shuichi_ishida
 */
@Getter
public class IrregularWorkingTimeOfMonthly {

	/** 複数月変形途中時間 */
	@Setter
	private AttendanceTimeMonth multiMonthIrregularMiddleTime;
	/** 変形期間繰越時間 */
	@Setter
	private AttendanceTimeMonth irregularPeriodCarryforwardTime;
	/** 変形労働不足時間 */
	@Setter
	private AttendanceTimeMonth irregularWorkingShortageTime;
	/** 変形法定内残業時間 */
	@Setter
	private TimeMonthWithCalculation irregularLegalOverTime;

	/**
	 * コンストラクタ
	 */
	public IrregularWorkingTimeOfMonthly(){
		
		this.multiMonthIrregularMiddleTime = new AttendanceTimeMonth(0);
		this.irregularPeriodCarryforwardTime = new AttendanceTimeMonth(0);
		this.irregularWorkingShortageTime = new AttendanceTimeMonth(0);
		this.irregularLegalOverTime = TimeMonthWithCalculation.ofSameTime(0);
	}

	/**
	 * ファクトリー
	 * @param multiMonthIrregularMiddleTime 複数月変形途中時間
	 * @param irregularPeriodCarryforwardTime 変形期間繰越時間
	 * @param irregularWorkingShortageTime 変形労働不足時間
	 * @param irregularLegalOverTime 変形法定内残業時間
	 * @return 月別実績の変形労働時間
	 */
	public static IrregularWorkingTimeOfMonthly of(
			AttendanceTimeMonth multiMonthIrregularMiddleTime,
			AttendanceTimeMonth irregularPeriodCarryforwardTime,
			AttendanceTimeMonth irregularWorkingShortageTime,
			TimeMonthWithCalculation irregularLegalOverTime){

		val domain = new IrregularWorkingTimeOfMonthly();
		domain.multiMonthIrregularMiddleTime = multiMonthIrregularMiddleTime;
		domain.irregularPeriodCarryforwardTime = irregularPeriodCarryforwardTime;
		domain.irregularWorkingShortageTime = irregularWorkingShortageTime;
		domain.irregularLegalOverTime = irregularLegalOverTime;
		return domain;
	}
}
