package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.shr.com.context.AppContexts;
/**
 * 営業日カレンダーの参照先(分類)
 * @author lan_lt
 *
 */
public class BussinessCalendarClssReference implements BussinessCalendarReference{
	
	private ClassificationCode clsCode;

	
	@Override
	public BusinessDaysCalendarType getBusinessDaysCalendarType() {
		return BusinessDaysCalendarType.CLASSSICATION;
	}

	@Override
	public Optional<WorkdayDivision> getWorkdayDivision(Require require, GeneralDate day) {
		return require.getCalendarClassByDay(AppContexts.user().companyId(), this.clsCode.v(), day)
				      .map(c -> c.getWorkDayDivision());
	}

}
