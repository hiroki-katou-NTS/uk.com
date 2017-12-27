package nts.uk.ctx.bs.employee.app.command.holidaysetting.configuration;

import lombok.Data;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekDay;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingGetMemento;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekPublicHoliday;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.LastWeekHolidayNumberOfFourWeek;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.LastWeekHolidayNumberOfOneWeek;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.OneWeekPublicHoliday;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekNumberOfDay;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FourWeekFourHolidayNumberSettingCommandDto.
 */
@Data
public class FourWeekFourHolidayNumberSettingCommandDto implements FourWeekFourHolidayNumberSettingGetMemento{
	
	/** The Constant TRUE_VALUE. */
	private final static int TRUE_VALUE = 1;
	
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
	private double inLegelHdFwph;
	
	/** The out legel hd fwph. */
	private double outLegelHdFwph;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingGetMemento#getIsOneWeekHoliday()
	 */
	@Override
	public boolean getIsOneWeekHoliday() {
		if(this.isOneWeekHoliday == TRUE_VALUE){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingGetMemento#getOneWeek()
	 */
	@Override
	public OneWeekPublicHoliday getOneWeek() {
		LastWeekHolidayNumberOfOneWeek obj = new LastWeekHolidayNumberOfOneWeek(new WeekNumberOfDay(this.inLegalHdLwhnoow), 
															new WeekNumberOfDay(this.outLegalHdLwhnoow));
		return new OneWeekPublicHoliday(obj, 
						new WeekNumberOfDay(this.inLegalHdOwph),
						new WeekNumberOfDay(this.outLegalHdOwph));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingGetMemento#getIsFourWeekHoliday()
	 */
	@Override
	public boolean getIsFourWeekHoliday() {
		if(this.isFourWeekHoliday == TRUE_VALUE){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingGetMemento#getFourWeek()
	 */
	@Override
	public FourWeekPublicHoliday getFourWeek() {
		LastWeekHolidayNumberOfFourWeek obj = new LastWeekHolidayNumberOfFourWeek(new FourWeekDay(this.inLegalHdLwhnofw),
															new FourWeekDay(this.outLegalHdLwhnofw));
		return new FourWeekPublicHoliday(obj,
						new FourWeekDay(this.inLegelHdFwph),
						new FourWeekDay(this.outLegelHdFwph));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.FourWeekFourHolidayNumberSettingGetMemento#getCID()
	 */
	@Override
	public String getCID() {
		return AppContexts.user().companyId();
	}

}
