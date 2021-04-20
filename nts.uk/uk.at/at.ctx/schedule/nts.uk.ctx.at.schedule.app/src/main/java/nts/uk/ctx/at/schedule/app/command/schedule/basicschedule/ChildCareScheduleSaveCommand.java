/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleGetMemento;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleRound;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class ChildCareScheduleSaveCommand.
 */
@Getter
@Setter
public class ChildCareScheduleSaveCommand implements ChildCareScheduleGetMemento{
	
	/** The child care number. */
	private int childCareNumber;
	
	/** The child care schedule start. */
	private int childCareScheduleStart;
	
	/** The child care schedule end. */
	private int childCareScheduleEnd;

	/** The child care atr. */
	private int childCareAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleGetMemento#getChildCareNumber()
	 */
	@Override
	public ChildCareScheduleRound getChildCareNumber() {
		return ChildCareScheduleRound.valueOf(this.childCareNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleGetMemento#getChildCareScheduleStart()
	 */
	@Override
	public TimeWithDayAttr getChildCareScheduleStart() {
		return new TimeWithDayAttr(childCareScheduleStart);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleGetMemento#getChildCareScheduleEnd()
	 */
	@Override
	public TimeWithDayAttr getChildCareScheduleEnd() {
		return new TimeWithDayAttr(childCareScheduleEnd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.
	 * ChildCareScheduleGetMemento#getChildCareAtr()
	 */
	@Override
	public ChildCareAtr getChildCareAtr() {
		return ChildCareAtr.valueOf(childCareAtr);
	}

}
