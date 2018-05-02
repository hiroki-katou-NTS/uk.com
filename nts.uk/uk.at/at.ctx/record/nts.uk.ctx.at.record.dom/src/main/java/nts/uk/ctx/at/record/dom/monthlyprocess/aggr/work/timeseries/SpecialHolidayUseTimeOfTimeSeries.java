package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.SpecialHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 時系列の特別休暇使用時間
 * @author shuichi_ishida
 */
@Getter
public class SpecialHolidayUseTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	
	/** 特別休暇使用時間 */
	private SpecialHolidayOfDaily specialHolidayUseTime;

	/**
	 * コンストラクタ
	 */
	public SpecialHolidayUseTimeOfTimeSeries(GeneralDate ymd){
		
		this.ymd = ymd;
		this.specialHolidayUseTime = new SpecialHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0));
	}

	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param specialHolidayUseTime 特別休暇使用時間
	 * @return 時系列の特別休暇使用時間
	 */
	public static SpecialHolidayUseTimeOfTimeSeries of(
			GeneralDate ymd, SpecialHolidayOfDaily specialHolidayUseTime){
		
		val domain = new SpecialHolidayUseTimeOfTimeSeries(ymd);
		domain.specialHolidayUseTime = specialHolidayUseTime;
		return domain;
	}
}
