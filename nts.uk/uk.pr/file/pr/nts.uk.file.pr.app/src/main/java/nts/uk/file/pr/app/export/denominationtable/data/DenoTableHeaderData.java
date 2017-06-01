/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.denominationtable.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/**
 * The Class DenoTableHeaderData.
 */

@Builder

@Getter

@Setter
public class DenoTableHeaderData {
	
	/** The target year month. */
	private String targetYearMonth;
	
	/** The department info. */
	private String departmentInfo;
	
	/** The position info. */
	private String positionInfo;
	
	/** The category info. */
	private String categoryInfo;
	

}
