package nts.uk.ctx.at.record.dom.byperiod;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 期間別の月の計算
 * @author shuichu_ishida
 */
@Getter
public class MonthlyCalculationByPeriod implements Cloneable {

	/** 集計時間 */
	private TotalWorkingTimeByPeriod aggregateTime;
	/** フレックス時間 */
	private FlexTimeByPeriod flexTime;
	/** 総労働時間 */
	private AttendanceTimeMonth totalWorkingTime;
	/** 総拘束時間 */
	private AggregateTotalTimeSpentAtWork totalSpentTime;
	
	/**
	 * コンストラクタ
	 */
	public MonthlyCalculationByPeriod(){

		this.aggregateTime = new TotalWorkingTimeByPeriod();
		this.flexTime = new FlexTimeByPeriod();
		this.totalWorkingTime = new AttendanceTimeMonth(0);
		this.totalSpentTime = new AggregateTotalTimeSpentAtWork();
	}
	
	/**
	 * ファクトリー
	 * @param aggregateTime 集計時間
	 * @param flexTime フレックス時間
	 * @param totalWorkingTime 総労働時間
	 * @param totalSpentTime 総拘束時間
	 * @return 期間別の月の計算
	 */
	public static MonthlyCalculationByPeriod of(
			TotalWorkingTimeByPeriod aggregateTime,
			FlexTimeByPeriod flexTime,
			AttendanceTimeMonth totalWorkingTime,
			AggregateTotalTimeSpentAtWork totalSpentTime){
		
		MonthlyCalculationByPeriod domain = new MonthlyCalculationByPeriod();
		domain.aggregateTime = aggregateTime;
		domain.flexTime = flexTime;
		domain.totalWorkingTime = totalWorkingTime;
		domain.totalSpentTime = totalSpentTime;
		return domain;
	}
	
	@Override
	public MonthlyCalculationByPeriod clone() {
		MonthlyCalculationByPeriod cloned = new MonthlyCalculationByPeriod();
		try {
			cloned.aggregateTime = this.aggregateTime.clone();
			cloned.flexTime = this.flexTime.clone();
			cloned.totalWorkingTime = new AttendanceTimeMonth(this.totalWorkingTime.v());
			cloned.totalSpentTime = this.totalSpentTime.clone();
		}
		catch (Exception e){
			throw new RuntimeException("MonthlyCalculationByPeriod clone error.");
		}
		return cloned;
	}
}
