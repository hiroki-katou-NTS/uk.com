/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.shared;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.Monthly;

/**
 * The Class MonthlyDto.
 */
@Value
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
		return domain.stream().map(item -> new MonthlyDto(item.getMonth().v(), item.getTime()))
				.collect(Collectors.toList());
	}
}
