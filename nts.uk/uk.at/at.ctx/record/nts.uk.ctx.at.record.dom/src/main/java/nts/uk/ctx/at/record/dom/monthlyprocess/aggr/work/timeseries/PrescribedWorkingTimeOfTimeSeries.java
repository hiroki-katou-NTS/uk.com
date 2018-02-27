package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule.WorkScheduleTimeOfDaily;

/**
 * 時系列の所定労働時間
 * @author shuichi_ishida
 */
@Getter
public class PrescribedWorkingTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	
	/** 所定労働時間 */
	private WorkScheduleTimeOfDaily prescribedWorkingTime;
	
	/**
	 * コンストラクタ
	 */
	public PrescribedWorkingTimeOfTimeSeries(GeneralDate ymd){
		
		this.ymd = ymd;
		this.prescribedWorkingTime = new WorkScheduleTimeOfDaily();
	}

	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param prescribedWorkingTime 日別実績の勤務予定時間
	 * @return 時系列の所定労働時間
	 */
	public static PrescribedWorkingTimeOfTimeSeries of(
			GeneralDate ymd, WorkScheduleTimeOfDaily prescribedWorkingTime){
		
		val domain = new PrescribedWorkingTimeOfTimeSeries(ymd);
		domain.prescribedWorkingTime = prescribedWorkingTime;
		return domain;
	}
}
