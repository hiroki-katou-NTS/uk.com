package nts.uk.ctx.at.shared.infra.repository.holidaysetting.configuration;

import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.FourWeekDay;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.FourWeekFourHolidayNumberSettingGetMemento;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.FourWeekPublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.LastWeekHolidayNumberOfFourWeek;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.LastWeekHolidayNumberOfOneWeek;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.OneWeekPublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.WeekNumberOfDay;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.configuration.Kshmt4w4dNumSet;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaFourWeekFourHolidayNumberSettingGetMemento.
 */
public class JpaFourWeekFourHolidayNumberSettingGetMemento implements FourWeekFourHolidayNumberSettingGetMemento{
	
	/** The Constant TRUE_VALUE. */
	private final static int TRUE_VALUE = 1;
	
	/** The kshmt fourweekfour hd numb set. */
	private Kshmt4w4dNumSet kshmt4w4dNumSet;
	
	/**
	 * Instantiates a new jpa four week four holiday number setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFourWeekFourHolidayNumberSettingGetMemento(Kshmt4w4dNumSet entity){
		this.kshmt4w4dNumSet = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingGetMemento#getIsOneWeekHoliday()
	 */
	@Override
	public boolean getIsOneWeekHoliday() {
		if(this.kshmt4w4dNumSet.getIsOneWeekHd() == TRUE_VALUE){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingGetMemento#getOneWeek()
	 */
	@Override
	public OneWeekPublicHoliday getOneWeek() {
		LastWeekHolidayNumberOfOneWeek obj = new LastWeekHolidayNumberOfOneWeek(new WeekNumberOfDay(this.kshmt4w4dNumSet.getInLegalHdLwhnoow().doubleValue()),
													new WeekNumberOfDay(this.kshmt4w4dNumSet.getOutLegalHdLwhnoow().doubleValue()));
		return new OneWeekPublicHoliday(obj, 
										new WeekNumberOfDay(this.kshmt4w4dNumSet.getInLegalHdOwph().doubleValue()),
										new WeekNumberOfDay(this.kshmt4w4dNumSet.getOutLegalHdOwph().doubleValue()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingGetMemento#getIsFourWeekHoliday()
	 */
	@Override
	public boolean getIsFourWeekHoliday() {
		if(this.kshmt4w4dNumSet.getIsFourWeekHd() == TRUE_VALUE){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingGetMemento#getFourWeek()
	 */
	@Override
	public FourWeekPublicHoliday getFourWeek() {
		LastWeekHolidayNumberOfFourWeek obj = new LastWeekHolidayNumberOfFourWeek(
														new FourWeekDay(this.kshmt4w4dNumSet.getInLegalHdLwhnofw().doubleValue()),
														new FourWeekDay(this.kshmt4w4dNumSet.getOutLegalHdLwhnofw().doubleValue()));
		return new FourWeekPublicHoliday(obj,
										 new FourWeekDay(this.kshmt4w4dNumSet.getInLegelHdFwph().doubleValue()),
										 new FourWeekDay(this.kshmt4w4dNumSet.getOutLegalHdFwph().doubleValue()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingGetMemento#getCID()
	 */
	@Override
	public String getCID() {
		return AppContexts.user().companyId();
	}

}
