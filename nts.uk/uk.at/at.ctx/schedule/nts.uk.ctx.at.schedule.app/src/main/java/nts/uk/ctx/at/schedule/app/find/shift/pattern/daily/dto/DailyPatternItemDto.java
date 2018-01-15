/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyPatternItemDto {

	/** The pattern code. */
	private String patternCode;

	/** The pattern name. */
	private String patternName;

	/**
	 * Instantiates a new daily pattern item dto.
	 *
	 * @param patternCode
	 *            the pattern code
	 * @param patternName
	 *            the pattern name
	 */
	public DailyPatternItemDto(String patternCode, String patternName) {
		super();
		this.patternCode = patternCode;
		this.patternName = patternName;
	}

}