package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeFrameTime;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.OverTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 集計残業時間
 * @author shuichi_ishida
 */
@Getter
public class AggregateOverTime {

	/** 残業枠NO */
	private final OverTimeFrameNo overTimeFrameNo;
	
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
	private Map<GeneralDate, OverTimeOfTimeSeries> timeSeriesWorks;
	
	/**
	 * コンストラクタ
	 * @param overTimeFrameNo 残業枠NO
	 */
	public AggregateOverTime(OverTimeFrameNo overTimeFrameNo){
		
		this.overTimeFrameNo = overTimeFrameNo;
		
		this.overTime = TimeMonthWithCalculation.ofSameTime(0);
		this.beforeOverTime = new AttendanceTimeMonth(0);
		this.transferOverTime = TimeMonthWithCalculation.ofSameTime(0);
		this.legalOverTime = new AttendanceTimeMonth(0);
		this.legalTransferOverTime = new AttendanceTimeMonth(0);
		
		this.timeSeriesWorks = new HashMap<>();
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

		val domain = new AggregateOverTime(overTimeFrameNo);
		domain.overTime = overTime;
		domain.beforeOverTime = beforeOverTime;
		domain.transferOverTime = transferOverTime;
		domain.legalOverTime = legalOverTime;
		domain.legalTransferOverTime = legalTransferOverTime;
		return domain;
	}
	
	/**
	 * 時系列ワークを取得する
	 * @param ymd 年月日
	 * @return 時系列ワーク
	 */
	public OverTimeOfTimeSeries getTimeSeriesWork(GeneralDate ymd){
		
		this.timeSeriesWorks.putIfAbsent(ymd, new OverTimeOfTimeSeries(ymd, this.overTimeFrameNo));
		return this.timeSeriesWorks.get(ymd);
	}
	
	/**
	 * 残業時間（時系列ワーク）に加算する
	 * @param ymd 年月日
	 * @param overTime 残業時間　（加算元）
	 */
	public void addOverTimeInTimeSeriesWork(GeneralDate ymd, OverTimeFrameTime overTime){
		
		this.timeSeriesWorks.putIfAbsent(ymd, new OverTimeOfTimeSeries(ymd, overTime.getOverWorkFrameNo()));
		val targetTimeSeriesWork = this.timeSeriesWorks.get(ymd);
		
		targetTimeSeriesWork.addOverTime(overTime);
	}
	
	/**
	 * 法定内残業時間（時系列ワーク）に加算する
	 * @param ymd 年月日
	 * @param legalOverTime 法定内残業時間　（加算元）
	 */
	public void addLegalOverTimeInTimeSeriesWork(GeneralDate ymd, OverTimeFrameTime legalOverTime){
		
		this.timeSeriesWorks.putIfAbsent(ymd, new OverTimeOfTimeSeries(ymd, legalOverTime.getOverWorkFrameNo()));
		val targetTimeSeriesWork = this.timeSeriesWorks.get(ymd);
		
		targetTimeSeriesWork.addLegalOverTime(legalOverTime);
	}
	
	/**
	 * 集計する
	 * @param datePeriod 期間
	 */
	public void aggregate(DatePeriod datePeriod){

		this.overTime = TimeMonthWithCalculation.ofSameTime(0);
		this.beforeOverTime = new AttendanceTimeMonth(0);
		this.transferOverTime = TimeMonthWithCalculation.ofSameTime(0);
		this.legalOverTime = new AttendanceTimeMonth(0);
		this.legalTransferOverTime = new AttendanceTimeMonth(0);
		
		for (val timeSeriesWork : this.timeSeriesWorks.values()){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			this.overTime = this.overTime.addMinutes(
					timeSeriesWork.getOverTime().getOverTimeWork().getTime().v(),
					timeSeriesWork.getOverTime().getOverTimeWork().getCalcTime().v());
			this.beforeOverTime = this.beforeOverTime.addMinutes(
					timeSeriesWork.getOverTime().getBeforeApplicationTime().v());
			this.transferOverTime = this.transferOverTime.addMinutes(
					timeSeriesWork.getOverTime().getTransferTime().getTime().v(),
					timeSeriesWork.getOverTime().getTransferTime().getCalcTime().v());
			this.legalOverTime = this.legalOverTime.addMinutes(
					timeSeriesWork.getLegalOverTime().getOverTimeWork().getTime().v());
			this.legalTransferOverTime = this.legalTransferOverTime.addMinutes(
					timeSeriesWork.getLegalOverTime().getTransferTime().getTime().v());
		}
	}
}
