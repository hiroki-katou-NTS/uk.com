package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.midnight.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.daily.withinworktime.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 時系列の就業時間
 * @author shuichi_ishida
 */
@Getter
public class WorkTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	
	/** 法定内時間 */
	private WithinStatutoryTimeOfDaily legalTime;
	
	/**
	 * コンストラクタ
	 * @param ymd 年月日
	 */
	public WorkTimeOfTimeSeries(GeneralDate ymd){
		
		this.ymd = ymd;
		this.legalTime = WithinStatutoryTimeOfDaily.createWithinStatutoryTimeOfDaily(
				new AttendanceTime(0),
				new AttendanceTime(0),
				new AttendanceTime(0),
				new WithinStatutoryMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(0))),
				new AttendanceTime(0));
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param legalTime 法定内時間
	 * @return 時系列の就業時間
	 */
	public static WorkTimeOfTimeSeries of(
			GeneralDate ymd,
			WithinStatutoryTimeOfDaily legalTime){
		
		val domain = new WorkTimeOfTimeSeries(ymd);
		domain.legalTime = legalTime;
		return domain;
	}
}
