/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.person.pubimp.person;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.pub.person.PersonInfoExport;
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
				.map(item -> new PubPersonDto(item.getPersonId(),
						item.getPersonNameGroup().getPersonName().getFullName().v(),
						item.getPersonNameGroup().getBusinessName().v()))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.person.pub.person.PersonPub#findById(java.lang.String)
	 * 
	 * @Override public PersonInfoExport findById(String personId) { Person person =
	 * this.personRepository.getByPersonId(personId).get(); PersonInfoExport
	 * personInfo = PersonInfoExport.builder() .personId(person.getPersonId())
	 * .personName(person.getPersonNameGroup().getPersonName().v())
	 * .birthDay(person.getBirthDate()) .pMailAddr(new
	 * MailAddress(person.getMailAddress().v())) .build();
	 * 
	 * return personInfo; }
	 */

	@Override
	public List<PersonInfoExport> findByListId(List<String> personIds) {
		return personRepository.getPersonByPersonIds(personIds).stream()
				.map(item -> new PersonInfoExport(
						item.getPersonId(),
						item.getPersonNameGroup().getBusinessName() == null ? "" : item.getPersonNameGroup().getBusinessName().v(),
						item.getBirthDate(), 
						null,
						item.getGender() == null ? 0 : item.getGender().value))
				.collect(Collectors.toList());
	}

}
