/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.workfixed;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoImportedDto;

/**
 * The Class PersonInfoWorkFixedFinder.
 */
@Stateless
public class PersonInfoWorkFixedFinder {

	/** The person info adapter. */
	@Inject
	private PersonInfoAdapter personInfoAdapter;

	/**
	 * Gets the person info.
	 *
	 * @param personId the person id
	 * @return the person info
	 */
	public PersonInfoWorkFixedDto getPersonInfo(String personId) {
		PersonInfoImportedDto personImportDto = personInfoAdapter.getPersonInfo(personId);
		PersonInfoWorkFixedDto personInfoWorkFixedDto = PersonInfoWorkFixedDto.builder()
				.employeeId(personImportDto.getEmployeeName()).employeeName(personImportDto.getEmployeeName()).build();

		return personInfoWorkFixedDto;

	}

}
