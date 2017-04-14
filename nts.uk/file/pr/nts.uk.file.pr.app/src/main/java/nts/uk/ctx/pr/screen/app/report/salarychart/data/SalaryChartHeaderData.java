/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.app.report.salarychart.data;

import java.time.YearMonth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder

/**
 * Gets the emp type.
 *
 * @return the emp type
 */
@Getter

/**
 * Sets the emp type.
 *
 * @param empType the new emp type
 */
@Setter
public class SalaryChartHeaderData {
	
	/** The date. */
	private YearMonth date;
	
	/** The department. */
	private String department;
	
	/** The position. */
	private String position;
	
	/** The emp type. */
	private String empType;
	

}
