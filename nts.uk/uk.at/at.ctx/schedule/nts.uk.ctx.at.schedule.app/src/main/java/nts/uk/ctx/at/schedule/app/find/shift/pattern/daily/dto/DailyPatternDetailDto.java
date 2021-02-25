/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyPatternDetailDto{

	/** The pattern code. */
	private String patternCode;

	/** The pattern name. */
	private String patternName;

	/** The list daily pattern val. */
	private List<DailyPatternValDto> dailyPatternVals;

}