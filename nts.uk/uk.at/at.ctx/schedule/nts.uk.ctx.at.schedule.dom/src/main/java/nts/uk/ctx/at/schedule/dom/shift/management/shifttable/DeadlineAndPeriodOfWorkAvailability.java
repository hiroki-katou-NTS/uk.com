package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 勤務希望の締切日と期間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.補助機能.シフト表.勤務希望の締切日と期間
 * @author dan_pv
 *
 */
@Value
public class DeadlineAndPeriodOfWorkAvailability {
	
	/**
	 * 締切日
	 */
	private GeneralDate deadline;
	
	/**
	 * 期間
	 */
	private DatePeriod period;
	
}
