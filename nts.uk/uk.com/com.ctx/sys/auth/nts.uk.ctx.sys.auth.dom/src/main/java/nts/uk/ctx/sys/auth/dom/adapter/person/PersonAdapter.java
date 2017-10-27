/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.adapter.person;

/**
 * The Interface PersonAdapter.
 */
public interface PersonAdapter {

	/**
	 * Find by id.
	 *
	 * @param personId the person id
	 * @return the person import
	 */
	// RequestList #86
	PersonImport findById(String personId);
}
