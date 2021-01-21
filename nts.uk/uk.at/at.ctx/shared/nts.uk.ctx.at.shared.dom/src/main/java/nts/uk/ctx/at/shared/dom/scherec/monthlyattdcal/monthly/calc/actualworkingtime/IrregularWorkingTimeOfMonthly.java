package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.actualworkingtime;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;

/**
 * 月別実績の変形労働時間
 * @author shuichi_ishida
 */
@Getter
public class IrregularWorkingTimeOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 複数月変形途中時間 */
	@Setter
	private AttendanceTimeMonthWithMinus multiMonthIrregularMiddleTime;
	/** 変形期間繰越時間 */
	@Setter
	private AttendanceTimeMonthWithMinus irregularPeriodCarryforwardTime;
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
		
		this.multiMonthIrregularMiddleTime = new AttendanceTimeMonthWithMinus(0);
		this.irregularPeriodCarryforwardTime = new AttendanceTimeMonthWithMinus(0);
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
			AttendanceTimeMonthWithMinus multiMonthIrregularMiddleTime,
			AttendanceTimeMonthWithMinus irregularPeriodCarryforwardTime,
			AttendanceTimeMonth irregularWorkingShortageTime,
			TimeMonthWithCalculation irregularLegalOverTime){

		val domain = new IrregularWorkingTimeOfMonthly();
		domain.multiMonthIrregularMiddleTime = multiMonthIrregularMiddleTime;
		domain.irregularPeriodCarryforwardTime = irregularPeriodCarryforwardTime;
		domain.irregularWorkingShortageTime = irregularWorkingShortageTime;
		domain.irregularLegalOverTime = irregularLegalOverTime;
		return domain;
	}
	
	/**
	 * 総労働対象時間の取得
	 * @return 総労働対象時間
	 */
	public AttendanceTimeMonth getTotalWorkingTargetTime(){

		if (this.irregularPeriodCarryforwardTime.lessThanOrEqualTo(0)) return new AttendanceTimeMonth(0);
		return new AttendanceTimeMonth(this.irregularPeriodCarryforwardTime.v());
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(IrregularWorkingTimeOfMonthly target){
		
		this.multiMonthIrregularMiddleTime = this.multiMonthIrregularMiddleTime.addMinutes(
				target.multiMonthIrregularMiddleTime.v());
		this.irregularPeriodCarryforwardTime = this.irregularPeriodCarryforwardTime.addMinutes(
				target.irregularPeriodCarryforwardTime.v());
		this.irregularWorkingShortageTime = this.irregularWorkingShortageTime.addMinutes(
				target.irregularWorkingShortageTime.v());
		this.irregularLegalOverTime = this.irregularLegalOverTime.addMinutes(
				target.irregularLegalOverTime.getTime().v(),
				target.irregularLegalOverTime.getCalcTime().v());
	}
}
