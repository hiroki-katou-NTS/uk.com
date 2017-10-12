/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ClockValue;

/**
 * The Class ChildCareSchedule.
 */
// 勤務予定育児介護時間帯
@Getter
public class ChildCareSchedule extends DomainObject{
	
	/** The child care number. */
	// 予定育児介護回数
	private ChildCareScheduleRound childCareNumber;
	
	/** The child care schedule start. */
	// 予定育児介護開始時刻
	private ClockValue childCareScheduleStart;
	
	/** The child care schedule end. */
	// 予定育児介護終了時刻
	private ClockValue childCareScheduleEnd;

	/** The child care atr. */
	// 育児介護区分
	private ChildCareAtr childCareAtr;
}
