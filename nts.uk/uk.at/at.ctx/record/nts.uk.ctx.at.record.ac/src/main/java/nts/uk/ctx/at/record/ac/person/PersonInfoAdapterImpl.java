/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ac.person;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoImportedDto;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;

/**
 * The Class PersonInfoAdapterImpl.
 */
@Stateless
public class PersonInfoAdapterImpl implements PersonInfoAdapter {

	/** The I person info pub. */
	@Inject
	private IPersonInfoPub IPersonInfoPub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter#getPersonInfo(java.lang.String)
	 */
	@Override
	public PersonInfoImportedDto getPersonInfo(String employeeId) {
		PersonInfoExport personInfoExport = IPersonInfoPub.getPersonInfo(employeeId);
		PersonInfoImportedDto personInfoImported = PersonInfoImportedDto.builder().employeeId(personInfoExport.getEmployeeId())
				.employeeName(personInfoExport.getEmployeeName()).build();
		
		return personInfoImported;
		
	}

}
