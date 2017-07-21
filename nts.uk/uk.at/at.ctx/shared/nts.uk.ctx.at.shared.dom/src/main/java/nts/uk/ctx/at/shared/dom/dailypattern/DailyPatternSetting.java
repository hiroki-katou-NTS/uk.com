/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.dailypattern;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

// TODO: Auto-generated Javadoc
/**
 * The Class CalendarSetting.
 */

/**
 * Gets the number day calendar.
 *
 * @return the number day calendar
 */
@Getter
public class DailyPatternSetting extends DomainObject {
	
	/** The number day calendar. */
	private NumberDayDaily numberDayCalendar;
	

    /**
     * Instantiates a new calendar setting.
     *
     * @param memento the memento
     */
    public DailyPatternSetting(DailyPatternSettingGetMemento memento) {
        this.numberDayCalendar = memento.getNumberDayCalendar();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(DailyPatternSettingSetMemento memento) {
        memento.setNumberDayCalendar(this.numberDayCalendar);
    }
}
