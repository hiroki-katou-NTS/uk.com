/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.ac.person;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.access.person.PersonAdapter;
import nts.uk.ctx.bs.employee.dom.access.person.dto.AcPersonDto;
import nts.uk.ctx.bs.person.pub.person.PersonPub;

/**
 * The Class PersonAdapterImpl.
 */
@Stateless
public class PersonAdapterImpl implements PersonAdapter {

	/** The person pub. */
	@Inject
	private PersonPub personPub;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.access.person.PersonAdapter#findByPersonIds(
	 * java.util.List)
	 */
	@Override
	public List<AcPersonDto> findByPersonIds(List<String> personIds) {
		return personPub.findByPersonIds(personIds).stream()
				.map(item -> new AcPersonDto(item.getPersonId(), item.getPersonName()))
				.collect(Collectors.toList());
	}

}
