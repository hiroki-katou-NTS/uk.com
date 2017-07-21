package nts.uk.ctx.at.shared.infra.repository.dailypattern;

import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSettingGetMemento;
import nts.uk.ctx.at.shared.dom.dailypattern.NumberDayDaily;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternVal;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaDailyPatternSettingGetMemento.
 */
public class JpaDailyPatternSettingGetMemento implements DailyPatternSettingGetMemento{

	 /** The entity. */
    private KdpstDailyPatternVal entity;
	
    /**
     * Instantiates a new jpa daily pattern setting get memento.
     *
     * @param entity the entity
     */
	public JpaDailyPatternSettingGetMemento(KdpstDailyPatternVal entity) {
		this.entity = entity;
	}
    
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.CalendarSettingGetMemento#getNumberDayCalendar()
	 */
	@Override
	public NumberDayDaily getNumberDayCalendar() {
		return new NumberDayDaily(this.entity.getDays());
	}

}
