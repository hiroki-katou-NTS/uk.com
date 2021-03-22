package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import lombok.Value;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.days.FourWeekDays;

/**
 * 休日取得の管理期間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.休日管理.休日の扱い.休日取得の管理期間
 * @author tutk
 *
 */
@Value
public class HolidayAcqManaPeriod {
	/**
	 * 期間
	 */
	private DatePeriod period;
	
	/**
	 * 休日日数
	 */
	private FourWeekDays holidayDays;
}
