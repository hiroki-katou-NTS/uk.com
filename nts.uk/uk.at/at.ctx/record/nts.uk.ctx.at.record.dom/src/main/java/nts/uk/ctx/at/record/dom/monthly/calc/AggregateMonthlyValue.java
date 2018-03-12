package nts.uk.ctx.at.record.dom.monthly.calc;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside.ExcessOutsideWorkMng;

/**
 * 戻り値：月別実績を集計する
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AggregateMonthlyValue {

	/** 集計総労働時間 */
	private AggregateTotalWorkingTime aggregateTotalWorkingTime;
	/** 時間外超過管理 */
	private ExcessOutsideWorkMng excessOutsideWorkMng;

	/**
	 * ファクトリー
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param excessOutsideWorkMng 時間外超過管理
	 * @return 戻り値：月別実績を集計する
	 */
	public static AggregateMonthlyValue of(
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			ExcessOutsideWorkMng excessOutsideWorkMng){
		
		AggregateMonthlyValue returnClass = new AggregateMonthlyValue();
		returnClass.aggregateTotalWorkingTime = aggregateTotalWorkingTime;
		returnClass.excessOutsideWorkMng = excessOutsideWorkMng;
		return returnClass;
	}
}
