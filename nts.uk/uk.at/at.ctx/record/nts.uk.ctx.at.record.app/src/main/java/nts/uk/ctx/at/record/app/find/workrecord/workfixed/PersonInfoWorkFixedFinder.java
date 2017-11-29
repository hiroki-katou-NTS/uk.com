/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.workfixed;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoImportedImport;

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
	 * @param employeeId the employee id
	 * @return the person info
	 */
	public PersonInfoWorkFixedDto getPersonInfo(String employeeId) {
		PersonInfoImportedImport personImportDto = personInfoAdapter.getPersonInfo(employeeId);
		PersonInfoWorkFixedDto personInfoWorkFixedDto = PersonInfoWorkFixedDto.builder()
				.employeeId(personImportDto.getEmployeeId()).employeeName(personImportDto.getEmployeeName()).build();

		return personInfoWorkFixedDto;

	}

}
