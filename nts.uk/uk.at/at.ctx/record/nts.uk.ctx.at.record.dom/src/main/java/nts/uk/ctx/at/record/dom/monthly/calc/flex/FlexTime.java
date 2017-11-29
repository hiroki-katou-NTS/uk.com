package nts.uk.ctx.at.record.dom.monthly.calc.flex;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculationAndMinus;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.FlexTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;

/**
 * フレックス時間
 * @author shuichi_ishida
 */
@Getter
public class FlexTime {

	/** フレックス時間 */
	private TimeMonthWithCalculationAndMinus flexTime;
	/** 事前フレックス時間 */
	private AttendanceTimeMonthWithMinus beforeFlexTime;
	/** 法定内フレックス時間 */
	private AttendanceTimeMonthWithMinus legalFlexTime;
	/** 法定外フレックス時間 */
	private AttendanceTimeMonthWithMinus illegalFlexTime;
	/** 時系列ワーク */
	private List<FlexTimeOfTimeSeries> timeSeriesWorks;
	
	/**
	 * コンストラクタ
	 */
	public FlexTime(){
		
		this.timeSeriesWorks = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param flexTime フレックス時間
	 * @param beforeFlexTime 事前フレックス時間
	 * @param legalFlexTime 法定内フレックス時間
	 * @param illegalFlexTime 法定外フレックス時間
	 * @return フレックス時間
	 */
	public static FlexTime of(
			TimeMonthWithCalculationAndMinus flexTime,
			AttendanceTimeMonthWithMinus beforeFlexTime,
			AttendanceTimeMonthWithMinus legalFlexTime,
			AttendanceTimeMonthWithMinus illegalFlexTime){

		FlexTime domain = new FlexTime();
		domain.flexTime = flexTime;
		domain.beforeFlexTime = beforeFlexTime;
		domain.legalFlexTime = legalFlexTime;
		domain.illegalFlexTime = illegalFlexTime;
		return domain;
	}
}
