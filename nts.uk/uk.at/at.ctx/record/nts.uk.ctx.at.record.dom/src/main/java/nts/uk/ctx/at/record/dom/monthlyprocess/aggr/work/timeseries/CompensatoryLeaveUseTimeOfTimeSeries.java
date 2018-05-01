package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.SubstituteHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 時系列の代休使用時間
 * @author shuichi_ishida
 */
@Getter
public class CompensatoryLeaveUseTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	
	/** 代休使用時間 */
	private SubstituteHolidayOfDaily substituteHolidayUseTime;

	/**
	 * コンストラクタ
	 */
	public CompensatoryLeaveUseTimeOfTimeSeries(GeneralDate ymd){
		
		this.ymd = ymd;
		this.substituteHolidayUseTime = new SubstituteHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0));
	}

	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param substituteHolidayUseTime 代休使用時間
	 * @return 時系列の代休使用時間
	 */
	public static CompensatoryLeaveUseTimeOfTimeSeries of(
			GeneralDate ymd, SubstituteHolidayOfDaily substituteHolidayUseTime){
		
		val domain = new CompensatoryLeaveUseTimeOfTimeSeries(ymd);
		domain.substituteHolidayUseTime = substituteHolidayUseTime;
		return domain;
	}
}