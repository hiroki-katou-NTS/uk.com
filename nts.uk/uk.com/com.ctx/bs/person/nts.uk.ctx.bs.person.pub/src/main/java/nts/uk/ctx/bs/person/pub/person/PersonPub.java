/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
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
}
