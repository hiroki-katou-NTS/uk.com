/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.person;

import java.util.List;

/**
 * The Interface PersonRepository.
 */
public interface PersonRepository {
	
	/**
	 * Gets the person by person id.
	 *
	 * @param personIds the person ids
	 * @return the person by person id
	 */
	List<Person> getPersonByPersonId(List<String> personIds);
}
