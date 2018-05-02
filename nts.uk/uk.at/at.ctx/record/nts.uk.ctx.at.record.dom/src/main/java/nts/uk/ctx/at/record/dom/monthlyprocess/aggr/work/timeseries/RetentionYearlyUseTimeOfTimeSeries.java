package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.YearlyReservedOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 時系列の積立年休使用時間
 * @author shuichi_ishida
 */
@Getter
public class RetentionYearlyUseTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	
	/** 積立年休使用時間 */
	private YearlyReservedOfDaily retentionYearlyUseTime;

	/**
	 * コンストラクタ
	 */
	public RetentionYearlyUseTimeOfTimeSeries(GeneralDate ymd){
		
		this.ymd = ymd;
		this.retentionYearlyUseTime = new YearlyReservedOfDaily(new AttendanceTime(0));
	}

	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param retentionYearlyUseTime 積立年休使用時間
	 * @return 時系列の積立年休使用時間
	 */
	public static RetentionYearlyUseTimeOfTimeSeries of(
			GeneralDate ymd, YearlyReservedOfDaily retentionYearlyUseTime){
		
		val domain = new RetentionYearlyUseTimeOfTimeSeries(ymd);
		domain.retentionYearlyUseTime = retentionYearlyUseTime;
		return domain;
	}
}
