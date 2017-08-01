/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.resttime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class BreakTimeZone.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class BreakTimeZone {

	/** The time table. */
	List<TimeTable> timeTable;

	public BreakTimeZone(BreakTimeZoneGetMemento memento) {
		this.timeTable = memento.getTimeTable();
	}

	public void saveToMemento(BreakTimeZoneSetMemento memento) {
		memento.setTimeTable(this.timeTable);
	}
}
