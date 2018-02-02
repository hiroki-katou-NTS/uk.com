package nts.uk.ctx.at.record.dom.daily.breaktimegoout;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の休憩時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class BreakTimeOfDaily {
	/** 計上用合計時間: 控除合計時間 */
	private DeductionTotalTime toRecordTotalTime;
	/** 控除用合計時間: 控除合計時間 */
	private DeductionTotalTime deductionTotalTime;
	/** 休憩回数: 休憩外出回数 */
	private BreakTimeGoOutTimes gooutTimes;
	/** 勤務間時間: 勤怠時間 */
	private AttendanceTime workTime;
	/** 補正後時間帯: 休憩時間帯 */
	private List<BreakTimeSheet> breakTimeSheet;
	
	
	
	/**
	 * Constructor
	 * @param record 計上用
	 * @param deduction 控除用
	 */
	private BreakTimeOfDaily(DeductionTotalTime record,DeductionTotalTime deduction) {
		this.toRecordTotalTime = record;
		this.deductionTotalTime = deduction;
	}
	
	public BreakTimeOfDaily(DeductionTotalTime toRecordTotalTime, DeductionTotalTime deductionTotalTime,
			BreakTimeGoOutTimes gooutTimes, AttendanceTime workTime, List<BreakTimeSheet> breakTimeSheet) {
		super();
		this.toRecordTotalTime = toRecordTotalTime;
		this.deductionTotalTime = deductionTotalTime;
		this.gooutTimes = gooutTimes;
		this.workTime = workTime;
		this.breakTimeSheet = breakTimeSheet;
	}
	
	/**
	 * 控除、計上用両方を受け取った時間に入れ替える
	 * @return 日別実績の休憩時間
	 */
	public static BreakTimeOfDaily sameTotalTime(DeductionTotalTime deductionTime) {
		return new BreakTimeOfDaily(deductionTime,deductionTime);
	}
	
	/**
	 * 日別実績の休憩時間
	 * @param oneDay 1日の計算範囲
	 * @return 日別実績の休憩時間
	 */
	public static BreakTimeOfDaily calcTotalBreakTime(CalculationRangeOfOneDay oneDay) {
		return new BreakTimeOfDaily(oneDay.getTemporaryDeductionTimeSheet().get().getTotalBreakTime(DeductionAtr.Deduction),
									oneDay.getTemporaryDeductionTimeSheet().get().getTotalBreakTime(DeductionAtr.Appropriate));
	}


	
}
