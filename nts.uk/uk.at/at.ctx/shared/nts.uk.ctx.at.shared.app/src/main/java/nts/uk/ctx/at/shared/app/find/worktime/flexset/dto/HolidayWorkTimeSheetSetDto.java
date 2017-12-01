/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class HolidayWorkTimeSheetSetDto.
 */
@Getter
@Setter
public class HolidayWorkTimeSheetSetDto {

	/** The work time no. */
	private Integer workTimeNo;
	
	/** The timezone. */
	private TimeZoneRoundingDto timezone;

	/** The is legal holiday constraint time. */
	private boolean isLegalHolidayConstraintTime;

	/** The in legal break frame no. */
	private Integer inLegalBreakFrameNo;

	/** The is non statutory dayoff constraint time. */
	private boolean isNonStatutoryDayoffConstraintTime;

	/** The out legal break frame no. */
	private Integer outLegalBreakFrameNo;

	/** The is non statutory holiday constraint time. */
	private boolean isNonStatutoryHolidayConstraintTime;

	/** The out legal pub hol frame no. */
	private Integer outLegalPubHolFrameNo;
}
