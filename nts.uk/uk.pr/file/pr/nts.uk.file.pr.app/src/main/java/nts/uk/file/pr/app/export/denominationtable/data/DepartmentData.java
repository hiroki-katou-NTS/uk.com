/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.denominationtable.data;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class DepartmentData.
 */

@Builder
@Getter
@Setter
public class DepartmentData {
	
	/** The dep code. */
	private String depCode;
	
	/** The dep name. */
	private String depName; 
	
	/** The dep path. */
	private String depPath;
	
	/** The dep level. */
	private Integer depLevel;
	
	/** The denomination. */
	private Map<Denomination, Long> denomination;
	
	/** The accumulated payment. */
	private Double accumulatedPayment;
	
	/** The number of emp. */
	private int numberOfEmp;
	
}
