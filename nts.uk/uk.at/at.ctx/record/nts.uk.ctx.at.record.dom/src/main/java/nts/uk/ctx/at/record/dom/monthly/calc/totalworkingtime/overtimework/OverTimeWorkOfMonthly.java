package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の残業時間
 * @author shuichi_ishida
 */
@Getter
public class OverTimeWorkOfMonthly {

	/** 残業合計時間 */
	private TimeMonthWithCalculation totalOverTimeWork;
	/** 事前残業時間 */
	private AttendanceTimeMonth beforeOverTimeWork;
	/** 振替残業合計時間 */
	private TimeMonthWithCalculation transferTotalOverTimeWork;
	/** 集計残業時間 */
	private List<AggregateOverTimeWork> aggregateOverTimeWorks;

	/**
	 * コンストラクタ
	 */
	public OverTimeWorkOfMonthly(){
		
		this.aggregateOverTimeWorks = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param totalOverTimeWork 残業合計時間
	 * @param beforeOverTimeWork 事前残業時間
	 * @param transferTotalOverTimeWork 振替残業時間
	 * @param aggregateOverTimeWorks 集計残業時間
	 * @return 月別実績の残業時間
	 */
	public static OverTimeWorkOfMonthly of(
			TimeMonthWithCalculation totalOverTimeWork,
			AttendanceTimeMonth beforeOverTimeWork,
			TimeMonthWithCalculation transferTotalOverTimeWork,
			List<AggregateOverTimeWork> aggregateOverTimeWorks){
		
		OverTimeWorkOfMonthly domain = new OverTimeWorkOfMonthly();
		domain.totalOverTimeWork = totalOverTimeWork;
		domain.beforeOverTimeWork = beforeOverTimeWork;
		domain.transferTotalOverTimeWork = transferTotalOverTimeWork;
		domain.aggregateOverTimeWorks = aggregateOverTimeWorks;
		return domain;
	}
}
