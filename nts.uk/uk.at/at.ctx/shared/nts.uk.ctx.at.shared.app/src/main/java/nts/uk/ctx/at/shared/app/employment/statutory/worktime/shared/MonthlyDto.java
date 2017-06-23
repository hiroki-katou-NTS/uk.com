/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.shared;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.gul.util.Time;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.Monthly;

/**
 * The Class MonthlyDto.
 */

/**
 * Instantiates a new monthly dto.
 */
@Data
public class MonthlyDto {

	/** The month. */
	private int month;

	/** The time. */
	private Long time;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the list
	 */
	public static List<MonthlyDto> fromDomain(List<Monthly> domain) {
		return domain.stream().map(item -> {
			MonthlyDto dto = new MonthlyDto();
			dto.setMonth(item.getMonth().getValue());
			dto.setTime(item.getTime().v() / Time.STEP);
			return dto;
		}).collect(Collectors.toList());
	}
}
