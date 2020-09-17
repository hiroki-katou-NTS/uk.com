package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.shr.com.context.AppContexts;

/**
 * 営業日カレンダーの参照先(会社)
 * 
 * @author lan_lt
 *
 */
@AllArgsConstructor
public class BussinessCalendarCompanyReference implements BussinessCalendarReference {

	@Override
	public BusinessDaysCalendarType getBusinessDaysCalendarType() {
		return BusinessDaysCalendarType.COMPANY;
	}

	@Override
	public Optional<WorkdayDivision> getWorkdayDivision(Require require, GeneralDate day) {
		return require.getCalendarCompanyByDay(AppContexts.user().companyId(), day)
				.map(c -> c.getWorkDayDivision());
	}

}
