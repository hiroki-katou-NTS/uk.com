/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.person;

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

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.person.Person;
import nts.uk.ctx.basic.dom.person.PersonRepository;
import nts.uk.ctx.basic.infra.entity.person.CcgmtPerson;
import nts.uk.ctx.basic.infra.entity.person.CcgmtPerson_;

/**
 * The Class JpaPersonRepository.
 */
@Stateless
public class JpaPersonRepository extends JpaRepository implements PersonRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.person.PersonRepository#getPersonByPersonId(java.
	 * util.List)
	 */
	@Override
	public List<Person> getPersonByPersonId(List<String> personIds) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call CCGMT_PERSON (CcgmtPerson SQL)
		CriteriaQuery<CcgmtPerson> cq = criteriaBuilder.createQuery(CcgmtPerson.class);

		// root data
		Root<CcgmtPerson> root = cq.from(CcgmtPerson.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// in person id
		lstpredicateWhere.add(criteriaBuilder.and(root.get(CcgmtPerson_.pid).in(personIds)));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<CcgmtPerson> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the person
	 */
	private Person toDomain(CcgmtPerson entity) {
		return new Person(new JpaPersonGetMemento(entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.person.PersonRepository#getByPersonId(java.lang.String)
	 */
	@Override
	public Optional<Person> getByPersonId(String personId) {
		return this.queryProxy().find(personId, CcgmtPerson.class).map(item -> this.toDomain(item));
	}
}
