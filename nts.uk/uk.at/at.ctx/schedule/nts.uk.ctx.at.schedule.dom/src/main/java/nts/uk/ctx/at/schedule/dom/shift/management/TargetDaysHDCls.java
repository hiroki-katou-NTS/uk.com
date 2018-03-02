package nts.uk.ctx.at.schedule.dom.shift.management;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 対象日休日区分
 * @author tanlv
 *
 */
@AllArgsConstructor
public class TargetDaysHDCls {
	/** 休日区分 */
	public HolidayCls holidayCls;
	
	/** 対象日 */
	public GeneralDate targetDate;
}
