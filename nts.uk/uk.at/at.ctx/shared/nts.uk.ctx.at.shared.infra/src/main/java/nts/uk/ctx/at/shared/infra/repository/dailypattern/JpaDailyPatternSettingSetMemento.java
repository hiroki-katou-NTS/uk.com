package nts.uk.ctx.at.shared.infra.repository.dailypattern;

import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSettingSetMemento;
import nts.uk.ctx.at.shared.dom.dailypattern.NumberDayDaily;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternVal;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaDailyPatternSettingSetMemento.
 */
public class JpaDailyPatternSettingSetMemento implements DailyPatternSettingSetMemento{
    
    /** The entity. */
    private KdpstDailyPatternVal entity;
	
	
	/**
	 * Instantiates a new jpa daily pattern setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaDailyPatternSettingSetMemento(KdpstDailyPatternVal entity) {
		this.entity = entity;
	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.CalendarSettingSetMemento#setNumberDayCalendar(nts.uk.ctx.at.shared.dom.patterncalendar.NumberDayCalendar)
	 */
	@Override
	public void setNumberDayCalendar(NumberDayDaily numberDayCalendar) {
		 if (numberDayCalendar != null) {
	            this.entity.setDays(numberDayCalendar.v());
	        }
	}

}
