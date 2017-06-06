/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class Closure.
 */
@Getter
public class Closure extends AggregateRoot{
	
	/** The company id. */
	private CompanyId companyId;
	
	/** The closure id. */
	private String closureId;
	
	/** The use classification. */
	private UseClassification useClassification;
	
	
	/** The month. */
	private ClosureMonth month;

	
	/** The closure historries. */
	private List<ClosureHistorry> closureHistorries;
}
