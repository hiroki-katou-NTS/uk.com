/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.person.pub.person;

import java.util.List;

/**
 * The Interface PersonPub.
 */
public interface PersonPub {

	/**
	 * Find by person ids.
	 *
	 * @param personIds the person ids
	 * @return the list
	 */
	List<PubPersonDto> findByPersonIds(List<String> personIds);
	
	/**
	 * Find by id.
	 *
	 * @param personId the person id
	 * @return the person info export
	 */
	// RequestList #86
	PersonInfoExport findById(String personId);
}
