/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.reference;

import java.util.List;

/**
 * The Interface WageTableCodeRefRepository.
 */
public interface WtReferenceRepository {

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<WtCodeRefItem> getCodeRefItem(WtCodeRef codeRef);

	/**
	 * Gets the master ref item.
	 *
	 * @param masterRef the master ref
	 * @return the master ref item
	 */
	List<WtCodeRefItem> getMasterRefItem(WtMasterRef masterRef);

}
