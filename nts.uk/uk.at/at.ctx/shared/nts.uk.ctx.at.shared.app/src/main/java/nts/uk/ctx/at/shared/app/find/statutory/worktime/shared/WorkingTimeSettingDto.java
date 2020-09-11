/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.shared;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class WorkingTimeSettingDto.
 */

/**
 * Instantiates a new working time setting dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingTimeSettingDto {

	/** The daily. */
	private int daily;

	/** The monthly. */
	private List<MonthlyDto> monthly;

	/** The weekly. */
	private int weekly;
}
