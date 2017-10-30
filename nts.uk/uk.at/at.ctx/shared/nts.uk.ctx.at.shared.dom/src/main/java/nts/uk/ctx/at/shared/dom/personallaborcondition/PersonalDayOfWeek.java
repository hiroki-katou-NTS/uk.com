/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.personallaborcondition;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class PersonalDayOfWeek.
 */
// 個人曜日別勤務
@Getter
public class PersonalDayOfWeek extends DomainObject{

	/** The saturday. */
	// 土曜日
	private Optional<SingleDaySchedule> saturday;
	
	/** The sunday. */
	// 日曜日
	private Optional<SingleDaySchedule> sunday;
	
	/** The monday. */
	// 月曜日
	private Optional<SingleDaySchedule> monday;
	
	/** The thursday. */
	// 木曜日
	private Optional<SingleDaySchedule> thursday;
	
	/** The wednesday. */
	// 水曜日
	private Optional<SingleDaySchedule> wednesday;
	
	/** The tuesday. */
	// 火曜日
	private Optional<SingleDaySchedule> tuesday;
	
	/** The friday. */
	// 金曜日
	private Optional<SingleDaySchedule> friday;
	
}
