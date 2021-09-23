package nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.requestperiodchange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;

/**
 * 勤務予定の所属情報
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffInfoForWorkSchedule {
	
	/**社員ID*/
	private String employeeId;
	
	/**年月日*/
	private GeneralDate ymd;
	
	/**所属情報*/
	private AffiliationInforOfDailyAttd affiliationInforOfDailyAttd;
}
