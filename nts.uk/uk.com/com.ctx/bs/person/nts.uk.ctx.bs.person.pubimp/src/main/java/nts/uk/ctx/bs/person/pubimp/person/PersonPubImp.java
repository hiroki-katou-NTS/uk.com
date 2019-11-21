/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.person.pubimp.person;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameGroup;
import nts.uk.ctx.bs.person.pub.person.FullNameSetExport;
import nts.uk.ctx.bs.person.pub.person.FullPersonInfoExport;
import nts.uk.ctx.bs.person.pub.person.PersonInfoExport;
import nts.uk.ctx.bs.person.pub.person.PersonNameGroupExport;
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

	@Override
	public List<FullPersonInfoExport> getPersonInfoFromListId(List<String> personIds) {
		return this.personRepository.getPersonByPersonIds(personIds).stream().map(p -> fromDomain(p))
				.collect(Collectors.toList());
				
	}
	
	private  FullPersonInfoExport fromDomain(Person p) {
		
		PersonNameGroup pName = p.getPersonNameGroup();
		
		FullNameSetExport  personName = new FullNameSetExport(pName.getPersonName().getFullName().v(), pName.getPersonName().getFullNameKana().v());
		FullNameSetExport  personalNameMultilingual = new FullNameSetExport(pName.getPersonalNameMultilingual().getFullName().v(), pName.getPersonalNameMultilingual().getFullNameKana().v());
		FullNameSetExport  personRomanji = new FullNameSetExport(pName.getPersonRomanji().getFullName().v(), pName.getPersonRomanji().getFullNameKana().v());
		FullNameSetExport  todokedeFullName = new FullNameSetExport(pName.getTodokedeFullName().getFullName().v(), pName.getTodokedeFullName().getFullNameKana().v());
		FullNameSetExport  oldName = new FullNameSetExport(pName.getOldName().getFullName().v(), pName.getOldName().getFullNameKana().v());
		
		PersonNameGroupExport personNameGroup = 
				PersonNameGroupExport.builder()
				.businessName(pName.getBusinessName().v())
				.businessNameKana(pName.getBusinessNameKana().v())
				.businessOtherName(pName.getBusinessOtherName().v())
				.businessEnglishName(pName.getBusinessEnglishName().v())
				.personName(personName)
				.PersonalNameMultilingual(personalNameMultilingual)
				.personRomanji(personRomanji)
				.todokedeFullName(todokedeFullName)
				.oldName(oldName)
				.build();
		
		return FullPersonInfoExport.builder()
				.birthDate(p.getBirthDate())
				.bloodType(p.getBloodType().value)
				.gender(p.getGender().value)
				.personId(p.getPersonId())
				.personNameGroup(personNameGroup)
				.build();
	}

}
