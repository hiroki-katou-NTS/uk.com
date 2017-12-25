package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.OverTimeWorkOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 集計残業時間
 * @author shuichi_ishida
 */
@Getter
public class AggregateOverTime {

	/** 残業枠NO */
	private OverTimeFrameNo overTimeFrameNo;
	/** 残業時間 */
	private TimeMonthWithCalculation overTime;
	/** 事前残業時間 */
	private AttendanceTimeMonth beforeOverTime;
	/** 振替残業時間 */
	private TimeMonthWithCalculation transferOverTime;
	/** 法定内残業時間 */
	private AttendanceTimeMonth legalOverTime;
	/** 法定内振替残業時間 */
	private AttendanceTimeMonth legalTransferOverTime;
	/** 時系列ワーク */
	private List<OverTimeWorkOfTimeSeries> timeSeriesWorks;
	
	/**
	 * コンストラクタ
	 */
	public AggregateOverTime(){
		
		this.timeSeriesWorks = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param overTimeFrameNo 残業枠NO
	 * @param overTime 残業時間
	 * @param beforeOverTime 事前残業時間
	 * @param transferOverTime 振替残業時間
	 * @param legalOverTime 法定内残業時間
	 * @param legalTransferOverTime 法定内振替残業時間
	 * @return 集計残業時間
	 */
	public static AggregateOverTime of(
			OverTimeFrameNo overTimeFrameNo,
			TimeMonthWithCalculation overTime,
			AttendanceTimeMonth beforeOverTime,
			TimeMonthWithCalculation transferOverTime,
			AttendanceTimeMonth legalOverTime,
			AttendanceTimeMonth legalTransferOverTime){

		AggregateOverTime domain = new AggregateOverTime();
		domain.overTimeFrameNo = overTimeFrameNo;
		domain.overTime = overTime;
		domain.beforeOverTime = beforeOverTime;
		domain.transferOverTime = transferOverTime;
		domain.legalOverTime = legalOverTime;
		domain.legalTransferOverTime = legalTransferOverTime;
		return domain;
	}
}
