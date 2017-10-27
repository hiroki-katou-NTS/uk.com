/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.person.pubimp.person;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.pub.person.PersonPub;
import nts.uk.ctx.bs.person.pub.person.PubPersonDto;

/**
 * The Class PersonPubImp.
 */
@Stateless
public class PersonPubImp implements PersonPub {

	/** The person repository. */
	@Inject
	private PersonRepository personRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.person.pub.person.PersonPub#findByPersonIds(java.util.List)
	 */
	@Override
	public List<PubPersonDto> findByPersonIds(List<String> personIds) {
		return personRepository.getPersonByPersonIds(personIds).stream()
				.map(item -> new PubPersonDto(item.getPersonId(), item.getPersonNameGroup().getPersonName().v()))
				.collect(Collectors.toList());
	}

}
