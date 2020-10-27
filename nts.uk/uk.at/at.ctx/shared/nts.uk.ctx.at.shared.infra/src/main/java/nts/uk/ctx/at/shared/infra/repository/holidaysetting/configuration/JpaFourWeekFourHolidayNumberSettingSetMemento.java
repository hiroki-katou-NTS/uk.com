package nts.uk.ctx.at.shared.infra.repository.holidaysetting.configuration;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.FourWeekFourHolidayNumberSettingSetMemento;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.FourWeekPublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.OneWeekPublicHoliday;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.configuration.Kshmt4w4dNumSet;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaFourWeekFourHolidayNumberSettingSetMemento.
 */
public class JpaFourWeekFourHolidayNumberSettingSetMemento implements FourWeekFourHolidayNumberSettingSetMemento{
	/** The Constant TRUE_VALUE. */
	private final static int TRUE_VALUE = 1;
	
	/** The Constant FALSE_VALUE. */
	private final static int FALSE_VALUE = 0;
	
	/** The kshmt fourweekfour hd numb set. */
	private Kshmt4w4dNumSet kshmt4w4dNumSet;
	
	/**
	 * Instantiates a new jpa four week four holiday number setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFourWeekFourHolidayNumberSettingSetMemento(Kshmt4w4dNumSet entity){
		if(entity.getCid() == null){
			entity.setCid(AppContexts.user().companyId());
		}
		this.kshmt4w4dNumSet = entity;
	}
	
	/**
	 * Sets the checks if is one week holiday.
	 *
	 * @param isOneWeekHoliday the new checks if is one week holiday
	 */
	@Override
	public void setIsOneWeekHoliday(boolean isOneWeekHoliday) {
		if(isOneWeekHoliday){
			this.kshmt4w4dNumSet.setIsOneWeekHd(TRUE_VALUE);
		} else {
			this.kshmt4w4dNumSet.setIsOneWeekHd(FALSE_VALUE);
		}
	}

	/**
	 * Sets the one week.
	 *
	 * @param oneWeek the new one week
	 */
	@Override
	public void setOneWeek(OneWeekPublicHoliday oneWeek) {
		this.kshmt4w4dNumSet.setInLegalHdOwph(BigDecimal.valueOf(oneWeek.getInLegalHoliday().v()));
		this.kshmt4w4dNumSet.setOutLegalHdOwph(BigDecimal.valueOf(oneWeek.getOutLegalHoliday().v()));
		this.kshmt4w4dNumSet.setInLegalHdLwhnoow(BigDecimal.valueOf(oneWeek.getLastWeekAddedDays().getInLegalHoliday().v()));
		this.kshmt4w4dNumSet.setOutLegalHdLwhnoow(BigDecimal.valueOf(oneWeek.getLastWeekAddedDays().getOutLegalHoliday().v()));
	}

	/**
	 * Sets the checks if is four week holiday.
	 *
	 * @param isFourWeekHoliday the new checks if is four week holiday
	 */
	@Override
	public void setIsFourWeekHoliday(boolean isFourWeekHoliday) {
		if(isFourWeekHoliday){
			this.kshmt4w4dNumSet.setIsFourWeekHd(TRUE_VALUE);
		} else {
			this.kshmt4w4dNumSet.setIsFourWeekHd(FALSE_VALUE);
		}
	}

	/**
	 * Sets the four week.
	 *
	 * @param fourWeek the new four week
	 */
	@Override
	public void setFourWeek(FourWeekPublicHoliday fourWeek) {
		this.kshmt4w4dNumSet.setInLegelHdFwph(BigDecimal.valueOf(fourWeek.getInLegalHoliday().v()));
		this.kshmt4w4dNumSet.setOutLegalHdFwph(BigDecimal.valueOf(fourWeek.getOutLegalHoliday().v()));
		this.kshmt4w4dNumSet.setInLegalHdLwhnofw(BigDecimal.valueOf(fourWeek.getLastWeekAddedDays().getInLegalHoliday().v()));
		this.kshmt4w4dNumSet.setOutLegalHdLwhnofw(BigDecimal.valueOf(fourWeek.getLastWeekAddedDays().getOutLegalHoliday().v()));
	}

	/**
	 * Sets the cid.
	 *
	 * @param CID the new cid
	 */
	@Override
	public void setCID(String CID) {
		// do not code here
	}

}
