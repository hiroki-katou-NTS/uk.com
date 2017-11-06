/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.shared;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.Monthly;

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
	private int time;

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
			dto.setTime(item.getTime().valueAsMinutes());
			return dto;
		}).collect(Collectors.toList());
	}
}
