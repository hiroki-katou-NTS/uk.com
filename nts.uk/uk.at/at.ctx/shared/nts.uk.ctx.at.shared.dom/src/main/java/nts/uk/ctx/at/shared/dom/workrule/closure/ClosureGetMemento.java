/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import java.util.List;

/**
 * The Interface ClosureGetMemento.
 */
public interface ClosureGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the closure id.
	 *
	 * @return the closure id
	 */
	ClosureId getClosureId();

	/**
	 * Gets the use classification.
	 *
	 * @return the use classification
	 */
	UseClassification getUseClassification();

	/**
	 * Gets the closure month.
	 *
	 * @return the closure month
	 */
	CurrentMonth getClosureMonth();

	/**
	 * Gets the closure histories.
	 *
	 * @return the closure histories
	 */
	List<ClosureHistory> getClosureHistories();
	
}
