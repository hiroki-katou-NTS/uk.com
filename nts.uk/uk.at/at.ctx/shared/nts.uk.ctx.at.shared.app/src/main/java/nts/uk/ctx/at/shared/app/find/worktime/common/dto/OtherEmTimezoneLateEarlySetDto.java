/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class OtherEmTimezoneLateEarlySetDto.
 */
@Getter
@Setter
public class OtherEmTimezoneLateEarlySetDto {
	
	/** The del time rounding set. */
	private TimeRoundingSettingDto delTimeRoundingSet; 
	
	/** The stamp exactly time is late early. */
	private boolean stampExactlyTimeIsLateEarly;
	
	/** The grace time set. */
	private GraceTimeSetDto graceTimeSet;
	
	/** The record time rounding set. */
	private TimeRoundingSettingDto recordTimeRoundingSet;
	
	/** The late early atr. */
	private Integer lateEarlyAtr;

}
