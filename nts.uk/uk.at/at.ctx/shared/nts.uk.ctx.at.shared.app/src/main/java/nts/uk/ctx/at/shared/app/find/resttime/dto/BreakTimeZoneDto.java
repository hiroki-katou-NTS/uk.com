/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.resttime.dto;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.resttime.BreakTimeZoneSetMemento;
import nts.uk.ctx.at.shared.dom.resttime.TimeTable;

/**
 * The Class BreakTimeZoneDto.
 */
public class BreakTimeZoneDto implements BreakTimeZoneSetMemento {

	/** The lst time table. */
	public List<TimeTableDto> lstTimeTable;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.resttime.BreakTimeZoneSetMemento#setTimeTable(
	 * java.util.List)
	 */
	@Override
	public void setTimeTable(List<TimeTable> timeTable) {
		this.lstTimeTable = timeTable.stream().map(item -> {
			TimeTableDto time = new TimeTableDto();
			item.saveToMemento(time);
			return time;
		}).collect(Collectors.toList());
	}

}
