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
	private AttendanceTimeMonthWithMinus withinStatutoryFlexTime;
	/** 法定外フレックス時間 */
	private AttendanceTimeMonthWithMinus excessOfStatutoryFlexTime;
	/** 時系列ワーク */
	private List<FlexTimeOfTimeSeries> timeSeriesWork;
	
	/**
	 * コンストラクタ
	 */
	public FlexTime(){
		
		this.timeSeriesWork = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param flexTime フレックス時間
	 * @param beforeFlexTime 事前フレックス時間
	 * @param withinStatutoryFlexTime 法定内フレックス時間
	 * @param excessOfStatutoryFlexTime 法定外フレックス時間
	 * @return フレックス時間
	 */
	public static FlexTime of(
			TimeMonthWithCalculationAndMinus flexTime,
			AttendanceTimeMonthWithMinus beforeFlexTime,
			AttendanceTimeMonthWithMinus withinStatutoryFlexTime,
			AttendanceTimeMonthWithMinus excessOfStatutoryFlexTime){

		FlexTime domain = new FlexTime();
		domain.flexTime = flexTime;
		domain.beforeFlexTime = beforeFlexTime;
		domain.withinStatutoryFlexTime = withinStatutoryFlexTime;
		domain.excessOfStatutoryFlexTime = excessOfStatutoryFlexTime;
		return domain;
	}
}
