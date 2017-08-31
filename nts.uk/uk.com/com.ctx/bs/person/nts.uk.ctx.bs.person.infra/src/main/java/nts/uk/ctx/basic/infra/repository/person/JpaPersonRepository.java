/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.info.BpsdtPerson;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;

/**
 * The Class JpaPersonRepository.
 */
@Stateless
public class JpaPersonRepository extends JpaRepository implements PersonRepository {
	public final String SELECT_NO_WHERE = "SELECT c FROM BpsdtPerson c";

	public final String SELECT_BY_PERSON_IDS = SELECT_NO_WHERE
			+ " WHERE c.bpsdtPersonPk.personId IN :pids";

	private static Person toDomain(BpsdtPerson entity) {
		Person domain = Person.createFromJavaStyle(entity.bpsdtPersonPk.personId,
				entity.PersonNameGroup);
		return domain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.person.PersonRepository#getPersonByPersonId(java.
	 * util.List)
	 */
	@Override
	public List<Person> getPersonByPersonIds(List<String> personIds) {
		
		// check exist input
		if(CollectionUtil.isEmpty(personIds)){
			return new ArrayList<>();
		}
		
		List<Person> lstPerson = this.queryProxy()
				.query(SELECT_BY_PERSON_IDS, BpsdtPerson.class)
				.setParameter("pids", personIds).getList(c -> toDomain(c));
		
		return lstPerson;
	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.person.PersonRepository#getByPersonId(java.lang.String)
	 */
	@Override
	public Optional<Person> getByPersonId(String personId) {
		return this.queryProxy().find(personId, BpsdtPerson.class).map(item -> toDomain(item));
	}
}
