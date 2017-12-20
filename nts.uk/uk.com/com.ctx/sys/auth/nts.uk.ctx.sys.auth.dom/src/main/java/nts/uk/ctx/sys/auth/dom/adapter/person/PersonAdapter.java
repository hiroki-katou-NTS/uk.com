/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.adapter.person;

import java.util.List;


/**
 * The Interface PersonAdapter.
 */
public interface PersonAdapter {

	/**
	 * Find by person ids.
	 *
	 * @param personIds the person ids
	 * @return the list
	 */
	List<PersonImport> findByPersonIds(List<String> personIds);
	
}
