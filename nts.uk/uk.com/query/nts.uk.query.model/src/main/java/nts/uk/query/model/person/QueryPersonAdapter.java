/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.person;

import java.util.List;

/**
 * The Interface QueryPersonAdapter.
 */
public interface QueryPersonAdapter {

	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the list
	 */
	List<String> findPersonIdsByName(String name);
}
