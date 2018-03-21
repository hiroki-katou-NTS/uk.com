/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class MonthlyUnitDto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyUnitDto extends ValueObject {

	/** The monthly. */
	private Integer month;

	/** The monthly time. */
	private Integer monthlyTime;

}
