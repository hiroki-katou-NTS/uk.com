/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.denominationtable.data;

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
public class DenoTableHeaderData {
	
	/** The date. */
	private String targetYearMonth;
	
	/** The department. */
	private String departmentInfo;
	
	/** The position. */
	private String positionInfo;
	
	/** The emp type. */
	private String categoryInfo;
	

}
