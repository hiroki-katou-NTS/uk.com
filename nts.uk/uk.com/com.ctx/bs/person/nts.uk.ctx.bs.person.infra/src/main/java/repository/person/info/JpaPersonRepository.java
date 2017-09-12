/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package repository.person.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import entity.person.info.BpsmtPerson;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.infra.entity.person.CcgmtPerson;
import nts.uk.ctx.basic.infra.entity.person.CcgmtPerson_;
import nts.uk.ctx.basic.infra.repository.person.JpaPersonGetMemento;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;

/**
 * The Class JpaPersonRepository.
 */
@Stateless
public class JpaPersonRepository extends JpaRepository implements PersonRepository {
	public final String SELECT_NO_WHERE = "SELECT c FROM BpsdtPerson c";

	public final String SELECT_BY_PERSON_IDS = SELECT_NO_WHERE
			+ " WHERE c.bpsdtPersonPk.pId IN :pids";

	private static Person toDomain(BpsmtPerson entity) {
		Person domain = Person.createFromJavaType(entity.bpsdtPersonPk.pId, entity.personName);
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
				.query(SELECT_BY_PERSON_IDS, BpsmtPerson.class)
				.setParameter("pids", personIds).getList(c -> toDomain(c));
		
		return lstPerson;
	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.person.PersonRepository#getByPersonId(java.lang.String)
	 */
	@Override
	public Optional<Person> getByPersonId(String personId) {
		return this.queryProxy().find(personId, BpsmtPerson.class).map(item -> toDomain(item));
	}
}
