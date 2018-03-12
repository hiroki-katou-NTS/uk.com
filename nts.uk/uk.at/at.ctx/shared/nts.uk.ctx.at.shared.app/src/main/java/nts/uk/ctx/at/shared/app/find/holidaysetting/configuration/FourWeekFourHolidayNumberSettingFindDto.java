package nts.uk.ctx.at.shared.app.find.holidaysetting.configuration;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.FourWeekFourHolidayNumberSettingSetMemento;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.FourWeekPublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.OneWeekPublicHoliday;

/**
 * The Class FourWeekFourHolidayNumberSettingFindDto.
 */
@Data
public class FourWeekFourHolidayNumberSettingFindDto implements FourWeekFourHolidayNumberSettingSetMemento {
	
	/** The Constant TRUE_VALUE. */
	private final static int TRUE_VALUE = 1;
	
	/** The Constant FALSE_VALUE. */
	private final static int FALSE_VALUE = 0;
	
	/** The is one week holiday. */
	private int isOneWeekHoliday;
	
	/** The is four week holiday. */
	private int isFourWeekHoliday;
	
	/** The in legal hd lwhnoow. */
	private double inLegalHdLwhnoow;
	
	/** The out legal hd lwhnoow. */
	private double outLegalHdLwhnoow;
	
	/** The in legal hd owph. */
	private double inLegalHdOwph;
	
	/** The out legal hd owph. */
	private double outLegalHdOwph;
	
	/** The in legal hd lwhnofw. */
	private double inLegalHdLwhnofw;
	
	/** The out legal hd lwhnofw. */
	private double outLegalHdLwhnofw;
	
	/** The in legel hd fwph. */
	private double inLegalHdFwph;
	
	/** The out legel hd fwph. */
	private double outLegalHdFwph;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingSetMemento#setIsOneWeekHoliday(boolean)
	 */
	@Override
	public void setIsOneWeekHoliday(boolean isOneWeekHoliday) {
		if(isOneWeekHoliday){
			this.isOneWeekHoliday = TRUE_VALUE;
		} else {
			this.isOneWeekHoliday = FALSE_VALUE;
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingSetMemento#setOneWeek(nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.OneWeekPublicHoliday)
	 */
	@Override
	public void setOneWeek(OneWeekPublicHoliday oneWeek) {
		this.inLegalHdOwph = oneWeek.getInLegalHoliday().v();
		this.outLegalHdOwph = oneWeek.getOutLegalHoliday().v();
		this.inLegalHdLwhnoow = oneWeek.getLastWeekAddedDays().getInLegalHoliday().v();
		this.outLegalHdLwhnoow = oneWeek.getLastWeekAddedDays().getOutLegalHoliday().v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingSetMemento#setIsFourWeekHoliday(boolean)
	 */
	@Override
	public void setIsFourWeekHoliday(boolean isFourWeekHoliday) {
		if(isFourWeekHoliday){
			this.isFourWeekHoliday = TRUE_VALUE;
		} else {
			this.isFourWeekHoliday = FALSE_VALUE;
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingSetMemento#setFourWeek(nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekPublicHoliday)
	 */
	@Override
	public void setFourWeek(FourWeekPublicHoliday fourWeek) {
		this.inLegalHdFwph = fourWeek.getInLegalHoliday().v();
		this.outLegalHdFwph = fourWeek.getOutLegalHoliday().v();
		this.inLegalHdLwhnofw = fourWeek.getLastWeekAddedDays().getInLegalHoliday().v();
		this.outLegalHdLwhnofw = fourWeek.getLastWeekAddedDays().getOutLegalHoliday().v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingSetMemento#setCID(java.lang.String)
	 */
	@Override
	public void setCID(String CID) {
		// do not code
	}
}
