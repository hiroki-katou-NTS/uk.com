/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.accumulatedpayment.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class AccPaymentHeaderData.
 */

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder

/**
 * Gets the year month info.
 *
 * @return the year month info
 */
@Getter

/**
 * Sets the year month info.
 *
 * @param yearMonthInfo the new year month info
 */
@Setter
public class AccPaymentHeaderData {
	
	/** The department info. */
	private String departmentInfo;
	
	/** The emp type info. */
	private String empTypeInfo;
	
	/** The position info. */
	private String positionInfo;
	
	/** The year month info. */
	private String yearMonthInfo;
}
