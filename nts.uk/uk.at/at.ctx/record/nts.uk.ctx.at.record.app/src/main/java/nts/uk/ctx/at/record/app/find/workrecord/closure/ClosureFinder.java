/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.closure;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureRepository;

/**
 * The Class ClosureFinder.
 */
@Stateless
public class ClosureFinder {
	
	/** The repository. */
	@Inject
	private ClosureRepository repository;
	
	
}
