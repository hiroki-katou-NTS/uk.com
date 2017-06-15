/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.workplace;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.workplace.Workplace;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWorkplace;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWorkplacePK_;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWorkplace_;

/**
 * The Class JpaWorkplaceRepository.
 */
@Stateless
public class JpaWorkplaceRepository extends JpaRepository implements WorkplaceRepository {
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceRepository#
	 * add(nts.uk.ctx.basic.dom.company.organization.workplace.Workplace)
	 */
	@Override
	public void add(Workplace workplace) {
		this.commandProxy().insert(this.toEntity(workplace));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceRepository#
	 * update(nts.uk.ctx.basic.dom.company.organization.workplace.Workplace)
	 */
	@Override
	public void update(Workplace workplace) {
		this.commandProxy().update(this.toEntity(workplace));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceRepository#
	 * findAll(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Workplace> findAll(String companyId,GeneralDate generalDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KWPMT_WORK_PLACE (KwpmtWorkPlace SQL)
		CriteriaQuery<KwpmtWorkplace> cq = criteriaBuilder.createQuery(KwpmtWorkplace.class);

		// root data
		Root<KwpmtWorkplace> root = cq.from(KwpmtWorkplace.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
			root.get(KwpmtWorkplace_.kwpmtWorkplacePK).get(KwpmtWorkplacePK_.cid), companyId));
		
		// >= general date
		lstpredicateWhere
			.add(criteriaBuilder.lessThanOrEqualTo(root.get(KwpmtWorkplace_.strD), generalDate));
		
		// <= general date
		lstpredicateWhere
		.add(criteriaBuilder.greaterThanOrEqualTo(root.get(KwpmtWorkplace_.endD), generalDate));
		
		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
			root.get(KwpmtWorkplace_.kwpmtWorkplacePK).get(KwpmtWorkplacePK_.cid), companyId));
		
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KwpmtWorkplace> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(workplace -> toDomain(workplace))
			.collect(Collectors.toList());
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the workplace
	 */
	private Workplace toDomain(KwpmtWorkplace entity){
		return new Workplace(new JpaWorkplaceGetMemento(entity));
	}
	
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kwpmt work place
	 */
	private KwpmtWorkplace toEntity(Workplace domain){
		KwpmtWorkplace entity = new KwpmtWorkplace();
		domain.saveToMemento(new JpaWorkplaceSetMemento(entity));
		return entity;
	}

}
