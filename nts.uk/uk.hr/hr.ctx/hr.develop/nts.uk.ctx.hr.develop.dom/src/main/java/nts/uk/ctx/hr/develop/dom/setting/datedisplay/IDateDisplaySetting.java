package nts.uk.ctx.hr.develop.dom.setting.datedisplay;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author anhdt
 *
 */
public interface IDateDisplaySetting {
	GeneralDate getDate();
	DatePeriod getStartDateEndDate();
}
