package nts.uk.ctx.at.shared.infra.repository.holidaysetting.configuration;

import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.WeekHolidaySettingGetMemento;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.WeekNumberOfDay;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.configuration.KshmtWeekHdSet;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaWeekHolidaySettingGetMemento.
 */
public class JpaWeekHolidaySettingGetMemento implements WeekHolidaySettingGetMemento {
	
	/** The kshmt week hd set. */
	private KshmtWeekHdSet kshmtWeekHdSet;
	
	/**
	 * Instantiates a new jpa week holiday setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaWeekHolidaySettingGetMemento(KshmtWeekHdSet entity){
		this.kshmtWeekHdSet = entity;
	}

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
		return new WeekNumberOfDay(this.kshmtWeekHdSet.getInLegalHd().doubleValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySettingGetMemento#getOutLegalHoliday()
	 */
	@Override
	public WeekNumberOfDay getOutLegalHoliday() {
		return new WeekNumberOfDay(this.kshmtWeekHdSet.getOutLegalHd().doubleValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySettingGetMemento#getStartDay()
	 */
	@Override
	public DayOfWeek getStartDay() {
		return DayOfWeek.valueOf(this.kshmtWeekHdSet.getStartDay());
	}

}
