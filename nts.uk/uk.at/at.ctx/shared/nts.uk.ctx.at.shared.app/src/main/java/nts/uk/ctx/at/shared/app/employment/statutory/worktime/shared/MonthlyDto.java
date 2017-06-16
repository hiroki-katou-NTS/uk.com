/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.shared;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.Monthly;

/**
 * The Class MonthlyDto.
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
	 * @return the sets the
	 */
	public static List<MonthlyDto> fromDomain(List<Monthly> domain) {
		return domain.stream().map(item -> {
			MonthlyDto dto = new MonthlyDto();
			dto.setMonth(item.getMonth().getValue());
			dto.setTime(item.getTime().v());
			return dto;
		}).collect(Collectors.toList());
	}
}
