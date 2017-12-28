package nts.uk.ctx.bs.employee.app.command.holidaysetting.configuration;

import lombok.Data;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.DayOfWeek;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySettingGetMemento;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekNumberOfDay;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WeekHolidaySettingCommandDto.
 */
@Data
public class WeekHolidaySettingCommandDto implements WeekHolidaySettingGetMemento{
	
	/** The in legal holiday. */
	private double inLegalHoliday;
	
	/** The out legal holiday. */
	private double outLegalHoliday;
	
	/** The start day. */
	private int startDay;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySettingGetMemento#getCID()
	 */
	@Override
	public String getCID() {
		return AppContexts.user().companyId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySettingGetMemento#getInLegalHoliday()
	 */
	@Override
	public WeekNumberOfDay getInLegalHoliday() {
		return new WeekNumberOfDay(this.inLegalHoliday);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySettingGetMemento#getOutLegalHoliday()
	 */
	@Override
	public WeekNumberOfDay getOutLegalHoliday() {
		return new WeekNumberOfDay(this.outLegalHoliday);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySettingGetMemento#getStartDay()
	 */
	@Override
	public DayOfWeek getStartDay() {
		return DayOfWeek.valueOf(this.startDay);
	}

}
