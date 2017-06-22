/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.classification.history.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class ClassificationHistoryInDto.
 */
@Getter
@Setter
public class ClassificationHistoryInDto implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The base date. */
	private GeneralDate baseDate;
	
	/** The classification codes. */
	private List<String> classificationCodes;
}
