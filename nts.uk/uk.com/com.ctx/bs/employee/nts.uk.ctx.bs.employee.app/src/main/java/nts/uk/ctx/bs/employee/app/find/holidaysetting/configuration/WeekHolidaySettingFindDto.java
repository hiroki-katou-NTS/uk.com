package nts.uk.ctx.bs.employee.app.find.holidaysetting.configuration;

import lombok.Data;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.DayOfWeek;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySettingSetMemento;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekNumberOfDay;

/**
 * The Class WeekHolidaySettingFindDto.
 */
@Data
public class WeekHolidaySettingFindDto implements WeekHolidaySettingSetMemento {
	
	/** The in legal holiday. */
	private double inLegalHoliday;
	
	/** The out legal holiday. */
	private double outLegalHoliday;
	
	/** The start day. */
	private int startDay;
	
	/**
	 * Sets the cid.
	 *
	 * @param CID the new cid
	 */
	@Override
	public void setCID(String CID) {
		// do not code
	}

	/**
	 * Sets the in legal holiday.
	 *
	 * @param inLegalHoliday the new in legal holiday
	 */
	@Override
	public void setInLegalHoliday(WeekNumberOfDay inLegalHoliday) {
		this.inLegalHoliday = inLegalHoliday.v();
	}

	/**
	 * Sets the out legal holiday.
	 *
	 * @param outLegalHoliday the new out legal holiday
	 */
	@Override
	public void setOutLegalHoliday(WeekNumberOfDay outLegalHoliday) {
		this.outLegalHoliday = outLegalHoliday.v();
	}

	/**
	 * Sets the start day.
	 *
	 * @param StartDay the new start day
	 */
	@Override
	public void setStartDay(DayOfWeek StartDay) {
		this.startDay = StartDay.value;
	}

}
