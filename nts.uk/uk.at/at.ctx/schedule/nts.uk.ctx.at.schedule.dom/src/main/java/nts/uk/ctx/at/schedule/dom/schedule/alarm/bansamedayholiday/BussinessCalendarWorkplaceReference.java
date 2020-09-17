package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.shr.com.context.AppContexts;
/**
 * 営業日カレンダーの参照先(職場)
 * @author lan_lt
 *
 */
public class BussinessCalendarWorkplaceReference implements BussinessCalendarReference{
	
	private String workplaceID;
	
	@Override
	public BusinessDaysCalendarType getBusinessDaysCalendarType() {
		return BusinessDaysCalendarType.WORKPLACE;
	}

	@Override
	public Optional<WorkdayDivision> getWorkdayDivision(Require require, GeneralDate day) {
		return require.getCalendarWorkplaceByDay(AppContexts.user().companyId(), this.workplaceID, day)
				      .map(c -> c.getWorkDayDivision());
	}

}
