/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.ac.person;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.pub.person.PersonPub;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonAdapter;
import nts.uk.ctx.sys.auth.dom.person.dto.PersonImport;

/**
 * The Class PersonAdapterImpl.
 */
@Stateless
public class AuthPersonAdapterImpl implements PersonAdapter {

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
	public List<PersonImport> findByPersonIds(List<String> personIds) {
		return personPub.findByPersonIds(personIds).stream()
				.map(item -> new PersonImport(item.getPersonId(), item.getPersonName()))
				.collect(Collectors.toList());
	}

}
