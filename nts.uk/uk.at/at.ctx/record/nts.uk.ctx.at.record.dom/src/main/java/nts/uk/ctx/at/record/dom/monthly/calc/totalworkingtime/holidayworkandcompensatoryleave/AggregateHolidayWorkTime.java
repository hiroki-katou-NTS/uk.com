package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.HolidayWorkTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 集計休出時間
 * @author shuichi_ishida
 */
@Getter
public class AggregateHolidayWorkTime {

	/** 休出枠NO */
	private int holidayWorkTimeFrameNo;
	/** 休出時間 */
	private TimeMonthWithCalculation holidayWorkTime;
	/** 事前休出時間 */
	private AttendanceTimeMonth beforeHolidayWorkTime;
	/** 振替休出時間 */
	private TimeMonthWithCalculation transferHolidayWorkTime;
	/** 法定内休出時間 */
	private AttendanceTimeMonth withinStatutoryHolidayWorkTime;
	/** 法定内振替休出時間 */
	private AttendanceTimeMonth withinStatutoryTransferHolidayWorkTime;
	/** 時系列ワーク */
	private List<HolidayWorkTimeOfTimeSeries> timeSeriesWorks;
	
	/**
	 * コンストラクタ
	 */
	public AggregateHolidayWorkTime(){
		
		this.timeSeriesWorks = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param holidayWorkTimeFrameNo 休出枠NO
	 * @param holidayWorkTime 休出時間
	 * @param beforeHolidayWorkTime 事前休出時間
	 * @param transferHolidayWorkTime 振替休出時間
	 * @param withinStatutoryHolidayWorkTime 法定内休出時間
	 * @param withinStatutoryTransferHolidayWorkTime 法定内振替休出時間
	 * @return 集計休出時間
	 */
	public static AggregateHolidayWorkTime of(
			int holidayWorkTimeFrameNo,
			TimeMonthWithCalculation holidayWorkTime,
			AttendanceTimeMonth beforeHolidayWorkTime,
			TimeMonthWithCalculation transferHolidayWorkTime,
			AttendanceTimeMonth withinStatutoryHolidayWorkTime,
			AttendanceTimeMonth withinStatutoryTransferHolidayWorkTime){
		
		AggregateHolidayWorkTime domain = new AggregateHolidayWorkTime();
		domain.holidayWorkTimeFrameNo = holidayWorkTimeFrameNo;
		domain.holidayWorkTime = holidayWorkTime;
		domain.beforeHolidayWorkTime = beforeHolidayWorkTime;
		domain.transferHolidayWorkTime = transferHolidayWorkTime;
		domain.withinStatutoryHolidayWorkTime = withinStatutoryHolidayWorkTime;
		domain.withinStatutoryTransferHolidayWorkTime = withinStatutoryTransferHolidayWorkTime;
		return domain;
	}
}
