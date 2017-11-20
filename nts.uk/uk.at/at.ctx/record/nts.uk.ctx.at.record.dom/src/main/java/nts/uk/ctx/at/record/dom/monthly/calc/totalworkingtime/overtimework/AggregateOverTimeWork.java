package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.OverTimeWorkOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 集計残業時間
 * @author shuichi_ishida
 */
@Getter
public class AggregateOverTimeWork {

	/** 残業枠NO */
	private int overTimeWorkFrameNo;
	/** 残業時間 */
	private TimeMonthWithCalculation overTimeWork;
	/** 事前残業時間 */
	private AttendanceTimeMonth beforeOverTimeWork;
	/** 振替残業時間 */
	private TimeMonthWithCalculation transferOverTimeWork;
	/** 法定内残業時間 */
	private AttendanceTimeMonth withinStatutoryOverTimeWork;
	/** 法定内振替残業時間 */
	private AttendanceTimeMonth withinStatutoryTransferOverTimeWork;
	/** 時系列ワーク */
	private List<OverTimeWorkOfTimeSeries> timeSeriesWorks;
	
	/**
	 * コンストラクタ
	 */
	public AggregateOverTimeWork(){
		
		this.timeSeriesWorks = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param overTimeWorkFrameNo 残業枠NO
	 * @param overTimeWork 残業時間
	 * @param beforeOverTimeWork 事前残業時間
	 * @param transferOverTimeWork 振替残業時間
	 * @param withinStatutoryOverTimeWork 法定内残業時間
	 * @param withinStatutoryTransferOverTimeWork 法定内振替残業時間
	 * @return 集計残業時間
	 */
	public static AggregateOverTimeWork of(
			int overTimeWorkFrameNo,
			TimeMonthWithCalculation overTimeWork,
			AttendanceTimeMonth beforeOverTimeWork,
			TimeMonthWithCalculation transferOverTimeWork,
			AttendanceTimeMonth withinStatutoryOverTimeWork,
			AttendanceTimeMonth withinStatutoryTransferOverTimeWork){

		AggregateOverTimeWork domain = new AggregateOverTimeWork();
		domain.overTimeWorkFrameNo = overTimeWorkFrameNo;
		domain.overTimeWork = overTimeWork;
		domain.beforeOverTimeWork = beforeOverTimeWork;
		domain.transferOverTimeWork = transferOverTimeWork;
		domain.withinStatutoryOverTimeWork = withinStatutoryOverTimeWork;
		domain.withinStatutoryTransferOverTimeWork = withinStatutoryTransferOverTimeWork;
		return domain;
	}
}
