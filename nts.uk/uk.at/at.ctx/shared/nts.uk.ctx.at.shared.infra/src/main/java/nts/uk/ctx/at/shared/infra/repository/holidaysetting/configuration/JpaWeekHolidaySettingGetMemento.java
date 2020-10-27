package nts.uk.ctx.at.shared.infra.repository.holidaysetting.configuration;

import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.WeekHolidaySettingGetMemento;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.WeekNumberOfDay;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.configuration.KshmtHdpubPerWeek;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaWeekHolidaySettingGetMemento.
 */
public class JpaWeekHolidaySettingGetMemento implements WeekHolidaySettingGetMemento {
	
	/** The kshmt week hd set. */
	private KshmtHdpubPerWeek kshmtHdpubPerWeek;
	
	/**
	 * Instantiates a new jpa week holiday setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaWeekHolidaySettingGetMemento(KshmtHdpubPerWeek entity){
		this.kshmtHdpubPerWeek = entity;
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
		return new WeekNumberOfDay(this.kshmtHdpubPerWeek.getInLegalHd().doubleValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySettingGetMemento#getOutLegalHoliday()
	 */
	@Override
	public WeekNumberOfDay getOutLegalHoliday() {
		return new WeekNumberOfDay(this.kshmtHdpubPerWeek.getOutLegalHd().doubleValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySettingGetMemento#getStartDay()
	 */
	@Override
	public DayOfWeek getStartDay() {
		return DayOfWeek.valueOf(this.kshmtHdpubPerWeek.getStartDay());
	}

}
