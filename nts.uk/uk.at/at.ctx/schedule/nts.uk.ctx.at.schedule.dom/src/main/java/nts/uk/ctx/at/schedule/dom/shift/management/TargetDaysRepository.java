package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * @author tanlv
 *
 */
public interface TargetDaysRepository {
	Optional<TargetDaysHDCls> getTargetHoliday(HolidayCls holidayCls, GeneralDate date);
}
