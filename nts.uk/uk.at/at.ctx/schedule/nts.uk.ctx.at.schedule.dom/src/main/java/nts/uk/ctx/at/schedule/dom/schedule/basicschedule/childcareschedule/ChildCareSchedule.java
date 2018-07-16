/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class ChildCareSchedule.
 */
// 勤務予定育児介護時間帯
@AllArgsConstructor
@Getter
public class ChildCareSchedule extends DomainObject{
	
	/** The child care number. */
	// 予定育児介護回数
	private ChildCareScheduleRound childCareNumber;
	
	/** The child care schedule start. */
	// 予定育児介護開始時刻
	private TimeWithDayAttr childCareScheduleStart;
	
	/** The child care schedule end. */
	// 予定育児介護終了時刻
	private TimeWithDayAttr childCareScheduleEnd;

	/** The child care atr. */
	// 育児介護区分
	private ChildCareAtr childCareAtr;
	
	/**
	 * Instantiates a new child care schedule.
	 *
	 * @param memento the memento
	 */
	public ChildCareSchedule(ChildCareScheduleGetMemento memento) {
		this.childCareNumber = memento.getChildCareNumber();
		this.childCareScheduleStart = memento.getChildCareScheduleStart();
		this.childCareScheduleEnd = memento.getChildCareScheduleEnd();
		this.childCareAtr = memento.getChildCareAtr();
	}
	
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ChildCareScheduleSetMemento memento){
		memento.setChildCareNumber(this.childCareNumber);
		memento.setChildCareScheduleStart(this.childCareScheduleStart);
		memento.setChildCareScheduleEnd(this.childCareScheduleEnd);
		memento.setChildCareAtr(this.childCareAtr);
	}
}
