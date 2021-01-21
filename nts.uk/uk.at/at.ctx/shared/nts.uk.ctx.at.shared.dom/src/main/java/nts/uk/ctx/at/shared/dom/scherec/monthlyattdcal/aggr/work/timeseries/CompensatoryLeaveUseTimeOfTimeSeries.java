package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.SubstituteHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

/**
 * 時系列の代休使用時間
 * @author shuichi_ishida
 */
@Getter
public class CompensatoryLeaveUseTimeOfTimeSeries implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 年月日 */
	private GeneralDate ymd;
	
	/** 代休使用時間 */
	private SubstituteHolidayOfDaily substituteHolidayUseTime;
	/** 休日区分 */
	private HolidayAtr holidayAtr;

	/**
	 * コンストラクタ
	 */
	public CompensatoryLeaveUseTimeOfTimeSeries(GeneralDate ymd){
		
		this.ymd = ymd;
		this.substituteHolidayUseTime = new SubstituteHolidayOfDaily(new AttendanceTime(0), new AttendanceTime(0));
		this.holidayAtr = HolidayAtr.STATUTORY_HOLIDAYS;
	}

	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param substituteHolidayUseTime 代休使用時間
	 * @param holidayAtr 休日区分
	 * @return 時系列の代休使用時間
	 */
	public static CompensatoryLeaveUseTimeOfTimeSeries of(
			GeneralDate ymd,
			SubstituteHolidayOfDaily substituteHolidayUseTime,
			HolidayAtr holidayAtr){
		
		val domain = new CompensatoryLeaveUseTimeOfTimeSeries(ymd);
		domain.substituteHolidayUseTime = substituteHolidayUseTime;
		domain.holidayAtr = holidayAtr;
		return domain;
	}
}