package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.HolidayWorkTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;

/**
 * 集計休出時間
 * @author shuichi_ishida
 */
@Getter
public class AggregateHolidayWorkTime {

	/** 休出枠NO */
	private HolidayWorkFrameNo holidayWorkFrameNo;
	/** 休出時間 */
	private TimeMonthWithCalculation holidayWorkTime;
	/** 事前休出時間 */
	private AttendanceTimeMonth beforeHolidayWorkTime;
	/** 振替時間 */
	private TimeMonthWithCalculation transferTime;
	/** 法定内休出時間 */
	private AttendanceTimeMonth legalHolidayWorkTime;
	/** 法定内振替休出時間 */
	private AttendanceTimeMonth legalTransferHolidayWorkTime;
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
	 * @param holidayWorkFrameNo 休出枠NO
	 * @param holidayWorkTime 休出時間
	 * @param beforeHolidayWorkTime 事前休出時間
	 * @param transferTime 振替時間
	 * @param legalHolidayWorkTime 法定内休出時間
	 * @param legalTransferHolidayWorkTime 法定内振替休出時間
	 * @return 集計休出時間
	 */
	public static AggregateHolidayWorkTime of(
			HolidayWorkFrameNo holidayWorkFrameNo,
			TimeMonthWithCalculation holidayWorkTime,
			AttendanceTimeMonth beforeHolidayWorkTime,
			TimeMonthWithCalculation transferTime,
			AttendanceTimeMonth legalHolidayWorkTime,
			AttendanceTimeMonth legalTransferHolidayWorkTime){
		
		AggregateHolidayWorkTime domain = new AggregateHolidayWorkTime();
		domain.holidayWorkFrameNo = holidayWorkFrameNo;
		domain.holidayWorkTime = holidayWorkTime;
		domain.beforeHolidayWorkTime = beforeHolidayWorkTime;
		domain.transferTime = transferTime;
		domain.legalHolidayWorkTime = legalHolidayWorkTime;
		domain.legalTransferHolidayWorkTime = legalTransferHolidayWorkTime;
		return domain;
	}
}
